package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.CoreMetadata;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.domain.NodeType;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Runs on Spring context startup.
 * makes sure that bare minimum data is store and it's valid
 * before even start the repository.
 */
public class MongoStartupService implements ApplicationListener {

    /**
     * Mongo Template.
     * needed to 'force' some initial system collections/documents.
     */
    private MongoTemplate mongoTemplate;
    /**
     * Node services.
     */
    private NodeService nodeService;
    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(MongoStartupService.class);

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            startup();
        }
    }

    /**
     * Runs all needed stuff to setup a default useful repo.
     */
    private void startup() {
        checkRepoIntegrity();
    }

    /**
     * Checks repository integrity.
     * <ul>
     *     <li>Checks if a root node exists if not creates a new one</li>
     *     <li>Checks that there is only one root node, if multiple found a IllegalStateException is thrown</li>
     * </ul>
     *
     */
    private void checkRepoIntegrity() {
        log.debug("Checking Repository Integrity");
        Node root = nodeService.getRootNode();
        if ( root == null) {
            log.info("Unable to find a root node, creating one ");
            root=createRootNode();
            log.info("Root node created , root = ", root);
        }else{
            log.debug("Root node found, root= {}", root);
        }
        //Checks if root node was created. if multiple roots found
        // Throw exception and stop startup
        //TODO build Tools for Mongo repo (sort of fdisk)
        List<Node> roots = nodeService.findNodesByParent(null);
        if ( roots.size() >1){
            log.error("Found {} root nodes, stopping repository to prevent it's corruption or data loses",
                roots.size());
            throw  new IllegalStateException("Multiple Root Nodes found");
        }

    }

    /**
     * Create a Root node and sets its metadata as default.
     */
    private Node createRootNode() {
        log.info("Root node was not found Creating Root node");
        Node rootNode = new Node();
        rootNode.setType(NodeType.FOLDER);
        rootNode.setId(UUID.randomUUID().toString());
        rootNode.setParent(null); //Force it to be ROOT, Only way to do it , hard way
        CoreMetadata metadata = new CoreMetadata();
        metadata.setCreateDate(new Date());
        metadata.setLastModifiedDate(new Date());
        metadata.setNodeName("/");
        metadata.setCreator(MongoRepositoryDefaults.SYSTEM_USER_NAME);
        metadata.setModifier(MongoRepositoryDefaults.SYSTEM_USER_NAME);
        metadata.setSize(0);
        rootNode.setMetadata(metadata);
        try {
            log.info("Creating Root node {}", rootNode);
            mongoTemplate.save(rootNode);
            log.info("Root node created");
            createSiteStructure(rootNode);
            return rootNode;
        }catch (MongoRepositoryException ex){
            log.error("Unable to create Repository default folders");
            log.error("Error while creating Site default folders ", ex);
            throw new IllegalStateException("Unable to create basic Repository structure");
        } catch (DataAccessException ex) {
            log.error("Unable to save root node", ex);
            // Force Bean container to stop.
            // Don't continue if root node can't be created.
            throw new IllegalStateException("Unable to create the root node", ex);
        }
    }

    private void createSiteStructure(Node root) throws MongoRepositoryException {
       nodeService.createFolderNode(root, MongoRepositoryDefaults.REPO_DEFAULT_CONFIG_FOLDER,
           MongoRepositoryDefaults.SYSTEM_USER_NAME);
       nodeService.createFolderNode(root, MongoRepositoryDefaults.REPO_DEFAULT_CONTENT_FOLDER,
           MongoRepositoryDefaults.SYSTEM_USER_NAME);
    }

    public void setMongoTemplate(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void setNodeServiceImpl(final NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
