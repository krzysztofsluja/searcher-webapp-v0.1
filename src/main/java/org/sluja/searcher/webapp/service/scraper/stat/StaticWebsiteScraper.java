package org.sluja.searcher.webapp.service.scraper.stat;

import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.scraper.stat.StaticWebsiteScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("staticWebsiteScraper")
public class StaticWebsiteScraper implements WebsiteScraper<List<Element>, StaticWebsiteScrapRequest> {
    @Override
    public List<Element> scrap(final StaticWebsiteScrapRequest request) throws StaticWebsiteScraperIncorrectFieldException {
        if(Objects.nonNull(request.getDocument())) {
            return request.getDocument().select(request.getProperty());
        }
        throw new StaticWebsiteScraperIncorrectFieldException();
    }
}
