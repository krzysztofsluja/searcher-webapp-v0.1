package org.sluja.searcher.webapp.service.connector.stat;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sluja.searcher.webapp.dto.connect.StaticWebsiteConnectRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

public class StaticWebsiteConnector implements IConnector<Document, StaticWebsiteConnectRequest> {

    private final int CONNECTION_RETRY_TRIES = 5;
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36";

    public static final StaticWebsiteConnector INSTANCE = new StaticWebsiteConnector();

    @Override
    public Document connectAndGetPage(final StaticWebsiteConnectRequest request) throws ConnectionTimeoutException, IOException {
        if(validateRequest(request)) {
            int tryCounter = 0;
            while (tryCounter < CONNECTION_RETRY_TRIES) {
                try {
                    return Jsoup.connect(request.getUrl())
                            .userAgent(USER_AGENT)
                            .get();
                } catch (IOException ex) {
                    tryCounter++;
                }
            }
        }
        throw new ConnectionTimeoutException();
    }

    private boolean validateRequest(final StaticWebsiteConnectRequest request) {
        return Objects.nonNull(request) && StringUtils.isNotEmpty(request.getUrl());
    }
}
