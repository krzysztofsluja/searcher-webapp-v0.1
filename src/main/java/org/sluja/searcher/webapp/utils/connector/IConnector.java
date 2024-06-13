package org.sluja.searcher.webapp.utils.connector;

import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;

import java.io.IOException;

public interface IConnector<T,S extends ConnectRequest> {

    T connectAndGetPage(final S request) throws ConnectionTimeoutException, IOException;

}
