package org.sluja.searcher.webapp.service.factory.scraper;

import org.jsoup.select.Elements;
import org.sluja.searcher.webapp.dto.marker.scraper.IScraper;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.stat.StaticWebsiteScraper;

public class WebsiteScraperFactory {

    //TODO dynamic scraper
    public static WebsiteScraper getScraper(final boolean isDynamicWebsite) {
        return new StaticWebsiteScraper();
    }
}
