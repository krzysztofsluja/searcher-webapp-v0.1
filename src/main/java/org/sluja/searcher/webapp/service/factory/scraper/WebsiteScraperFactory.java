package org.sluja.searcher.webapp.service.factory.scraper;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodEndLog;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodStartLog;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("websiteScraperFactory")
@RequiredArgsConstructor
public class WebsiteScraperFactory {

    private final WebsiteScraper<List<Element>, StaticWebsiteScrapRequest> staticWebsiteScraper;
    private final WebsiteScraper<List<WebElement>, DynamicWebsiteScrapRequest> dynamicWebsiteScraper;
    private final WebsiteScraper<Element, StaticWebsiteElementScrapRequest> staticWebsiteElementScraper;
    @MethodStartLog
    @MethodEndLog
    public WebsiteScraper getScraper(final boolean isDynamicWebsite) {
        return isDynamicWebsite ? dynamicWebsiteScraper : staticWebsiteScraper;
    }

    @MethodStartLog
    @MethodEndLog
    public WebsiteScraper getElementScraper(final boolean isDynamicWebsite) {
        return isDynamicWebsite ? null : staticWebsiteElementScraper;
    }
}
