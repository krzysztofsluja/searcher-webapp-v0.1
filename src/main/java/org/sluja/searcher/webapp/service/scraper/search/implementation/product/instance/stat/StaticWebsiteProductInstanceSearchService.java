package org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.stat;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ScrapProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.product.instance.ProductInstanceNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("staticWebsiteProductInstanceSearchService")
@RequiredArgsConstructor
public class StaticWebsiteProductInstanceSearchService extends ProductInstanceSearchService<StaticWebsiteScrapRequest, ScrapProductInstanceSearchRequest> {

    private final WebsiteScraperFactory websiteScraperFactory;
    @Override
    @InputValidation(inputs = {ScrapProductInstanceSearchRequest.class})
    public List<Element> searchList(final ScrapProductInstanceSearchRequest searchRequest) throws ProductInstanceNotFoundException {
        final Document document = Jsoup.parse(searchRequest.getCurrentPageSource());
        final StaticWebsiteScrapRequest scrapRequest = new StaticWebsiteScrapRequest(searchRequest.getProductInstance(), document);
        try {
            return (List<Element>) search(scrapRequest);
        } catch (ScraperIncorrectFieldException e) {
            //TODO logging
            throw new ProductInstanceNotFoundException();
        }
    }

    @Override
    public WebsiteScraper<List<Element>, StaticWebsiteScrapRequest> getScraperService() {
        return (WebsiteScraper<List<Element>, StaticWebsiteScrapRequest>) websiteScraperFactory.getScraper(false);
    }
}
