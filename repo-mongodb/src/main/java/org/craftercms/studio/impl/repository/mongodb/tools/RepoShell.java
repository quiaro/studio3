/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import javolution.util.FastList;
import org.apache.commons.lang3.StringUtils;
import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.GridFSService;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Repo Shell.
 * Simple CLI to interact with the mongo repository.
 * Scans all implementations of AbstractAction and loads them in a registry. then
 * for each action enter by the users iterates over register actions and as if given command
 * belongs to it {@link AbstractAction#responseTo(String)} when the first match is found then
 * it calls {@link AbstractAction#run(RepoShellContext, String[])}.<br/>
 * All Exceptions are caught and its message is printed.All loggers are redirect to a file
 */
public class RepoShell {

    /**
     * RepoShell Context to be send to the actions.
     */
    private final RepoShellContext context;
    /**
     * User Input Reader.
     */
    private final BufferedReader in;
    /**
     * Output to the user.
     */
    private final PrintStream out;
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(RepoShell.class);
    /**
     * Registry of the actions.
     */
    private List<AbstractAction> actionList;

    /**
     * Creates a new instance of the Shell.Creates an instance of RepoShellContext<br/>
     * Additionally search and loads all the Actions in the classpath.
     * @param ctx Spring Application Context.
     * @throws IllegalAccessException If Actions constructor is not public.
     * @throws InstantiationException If Actions can't be instantiated.
     * @throws ClassNotFoundException If Actions can't be found.
     */
    public RepoShell(ApplicationContext ctx) throws IllegalAccessException, InstantiationException,
        ClassNotFoundException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = System.out;
        try {
            this.context = new RepoShellContext(ctx.getBean(NodeService.class).getRootNode(),
                ctx.getBean(NodeService.class), ctx.getBean(PathService.class), ctx.getBean(GridFSService.class),
                ctx.getBean(ContentService.class), System.out, in);
        } catch (MongoRepositoryException e) {
            throw new IllegalStateException("Unable to get Root Node");
        }
        loadActions();
    }

    /**
     * Starts the Shell, Loads Spring Contexts.
     * @param args Application Args
     * @throws Exception If Spring context can't be load.
     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Starting Mongo Repo Console ... ");
            configLogger();
            ApplicationContext ctx = new ClassPathXmlApplicationContext
                ("classpath:/craftercms/studio/craftercms-mongo-repository.xml");
            new RepoShell(ctx).run();
        } catch (Throwable ex) {
            System.out.printf("Unable to start due a internal error %s ," +
                "" + "please check the logfile for detail info", ex.getMessage());
        }
    }

    /**
     * Reconfigures the Logging makes sure that all logs are redirected to
     * repoConsole.log.<b>All file configurations are ignore</b>. <br/>
     * Logging level is set to be Info.(This could be change in the future)
     */
    private static void configLogger() {
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        loggerContext.reset();

        RollingFileAppender rfAppender = new RollingFileAppender();
        rfAppender.setContext(loggerContext);
        rfAppender.setFile("repoConsole.log");
        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        // rolling policies need to know their parent
        // it's one of the rare cases, where a sub-component knows about its parent
        rollingPolicy.setParent(rfAppender);
        rollingPolicy.setFileNamePattern("repoConsole.%i.log.zip");
        rollingPolicy.start();

        SizeBasedTriggeringPolicy triggeringPolicy = new ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy();
        triggeringPolicy.setMaxFileSize("5MB");
        triggeringPolicy.start();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%-4relative [%thread] %-5level %logger{35} - %msg%n");
        encoder.start();

        rfAppender.setEncoder(encoder);
        rfAppender.setRollingPolicy(rollingPolicy);
        rfAppender.setTriggeringPolicy(triggeringPolicy);
        rfAppender.start();
        for (Logger logger : loggerContext.getLoggerList()) {
            ((ch.qos.logback.classic.Logger)logger).setLevel(Level.INFO);
            ((ch.qos.logback.classic.Logger)logger).addAppender(rfAppender);
        }
    }

    /**
     * Using Spring's ClassPathScanningCandidateComponentProvider search for all Classes
     * that Extends {@link org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction}
     * in the package <i>org.craftercms.studio.impl.repository.mongodb</i> and loads and register them.
     * @throws ClassNotFoundException If Class can't be found.
     * @throws IllegalAccessException If Class can't be access.
     * @throws InstantiationException If Class can't be Instantiated.
     * @throws ClassCastException If Class can't be cast to AbstractAction.
     */
    private void loadActions() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
        ClassCastException {
        this.actionList = new FastList<>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractAction.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("org.craftercms.studio.impl.repository" + "" +
            ".mongodb");
        for (BeanDefinition component : components) {
            actionList.add((AbstractAction)Class.forName(component.getBeanClassName()).newInstance());
        }
        actionList.add(new HelpAction(Collections.unmodifiableList(actionList)));
    }

    /**
     * Creates a Infinitive loop that reads the user input.<br/>
     * When the user sends the EOL (enter/return) input is read and the split (for the whitespace char)
     * it uses the first part of the string as the actual command/action be be run.the rest of the string is
     * consider arguments for the command/action.<br/>
     * Search  is done by iterating the registry and asking for the action if response to the given
     * command/action.
     * (uses {@link org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction#responseTo(String)}  result.)
     * @throws IOException
     */
    public void run() throws IOException {
        while (true) {
            out.print("> ");
            String input = in.readLine();
            if (!StringUtils.isBlank(input)) {
                String cmd = input.split(" ")[0];
                String[] args = null;
                if (!(input.indexOf(" ") < 0)) {
                    args = input.substring(input.indexOf(" ") + 1).split(" ");
                }
                try {
                    if (!runAction(cmd, args)) {
                        out.printf("Command %s not found \n", cmd);
                    }
                } catch (RuntimeException ex) {
                    out.printf("Error running %s due %s\n", cmd, ex.getMessage());
                }
            }

        }
    }

    /**
     * Runs the action.
     * @param cmd Command.
     * @param args Command Arguments
     * @return True if was run successfully. False otherwise.
     */
    private boolean runAction(final String cmd, final String[] args) {
        for (AbstractAction action : actionList) {
            if (action.responseTo(cmd)) {
                try {
                    action.run(context, args);
                } catch (StudioException ex) {
                    log.error("Unable to run " + cmd, ex);
                    out.printf("Error running %s due %s\n", cmd, ex.getMessage());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Help action that is always available.
     * Calls the {@link AbstractAction#printHelp()} to print out help.
     */
    class HelpAction extends AbstractAction {

        /**
         * Copy of Action Registry.
         */
        private final List<AbstractAction> actions;

        /**
         * Default Constructor.
         * @param actions Action Registry.
         */
        public HelpAction(List<AbstractAction> actions) {
            super();
            this.actions = actions;
        }

        @Override
        public boolean responseTo(final String action) {
            return Arrays.asList("help", "?").contains(action);
        }

        @Override
        public void run(final RepoShellContext context, final String[] args) throws StudioException {
            if (args == null || args.length == 0) {
                for (AbstractAction action : actions) {
                    System.out.printf("\t %s \t %s \n", action.actionName(), action.printHelp());
                }
            } else {
                runHelp(args[0]);
            }
        }

        /**
         * Runs the help Command for a given Command.
         * @param cmd Command to be run.
         */
        private void runHelp(final String cmd) {
            for (AbstractAction action : actions) {
                if (action.responseTo(cmd)) {
                    System.out.printf("\t %s \n", action.printHelp());
                    return;
                }
            }
            System.out.printf("Help for %s not found \n", cmd);
        }

        @Override
        public String printHelp() {
            return "Prints action help , or a short summary of all the register actions";
        }

        @Override
        public String actionName() {
            return "help,?";
        }
    }
}
