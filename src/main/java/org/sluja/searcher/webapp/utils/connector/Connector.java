package org.sluja.searcher.webapp.utils.connector;

import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;

import java.io.IOException;

public interface Connector<T> {

    default T connectAndGetPage(final String url) throws ConnectionTimeoutException, IOException {
        throw new ConnectionTimeoutException();
    }

    default T connect() throws ConnectionTimeoutException{
        throw new ConnectionTimeoutException();
    }
}
