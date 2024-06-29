package org.sluja.searcher.webapp.service.scraper.stat;

import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("staticWebsiteElementScraper")
public class StaticWebsiteElementScraper implements WebsiteScraper<Element, StaticWebsiteElementScrapRequest> {
    @Override
    public Element scrap(StaticWebsiteElementScrapRequest request) throws ScraperIncorrectFieldException {
        return request.getElement().selectFirst(request.getProperty());
    }
}
