package org.craftercms.studio3.web.support.message;

public interface MessageFormatterManager {
    <T extends AbstractExceptionMessageFormatter>  void registerFormatter(Class<? extends Exception> clazz, T formatter);

    <T extends AbstractExceptionMessageFormatter> T getFormatter(Class<? extends Exception> clazz);
}
