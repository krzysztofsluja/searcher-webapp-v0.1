package org.sluja.searcher.webapp.utils.connector;

import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;

import java.io.IOException;

@FunctionalInterface
public interface Connector<T> {

    T connectAndGetPage(final String url) throws ConnectionTimeoutException, IOException;
}
