package org.sluja.searcher.webapp.service.scraper.connector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.utils.connector.Connector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Qualifier("scraperConnector")
public class ScraperConnector implements Connector<Document> {

    private final int CONNECTION_RETRY_TRIES = 5;
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36";

    @Override
    public Document connectAndGetPage(final String url) throws ConnectionTimeoutException {
        int tryCounter = 0;
        while (tryCounter < CONNECTION_RETRY_TRIES) {
            try {
                return Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .get();
            } catch (IOException _) {
                tryCounter++;
            }
        }
        throw new ConnectionTimeoutException();
    }
}
