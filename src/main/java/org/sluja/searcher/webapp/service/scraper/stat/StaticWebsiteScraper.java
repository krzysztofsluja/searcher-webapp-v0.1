package org.sluja.searcher.webapp.service.scraper.stat;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sluja.searcher.webapp.dto.connect.StaticWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.scraper.stat.StaticWebsiteScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;

public class StaticWebsiteScraper implements WebsiteScraper<Element, StaticWebsiteScrapRequest> {
    @Override
    public List<Element> scrap(final StaticWebsiteScrapRequest request) throws StaticWebsiteScraperIncorrectFieldException {
        if(Objects.nonNull(request.getDocument())) {
            return request.getDocument().select(request.getProperty());
        }
        throw new StaticWebsiteScraperIncorrectFieldException();
    }
}
