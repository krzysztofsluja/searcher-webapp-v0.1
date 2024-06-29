package org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.stat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ScrapProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("staticWebsiteProductInstanceSearchService")
public class StaticWebsiteProductInstanceSearchService extends ProductInstanceSearchService<StaticWebsiteScrapRequest, ScrapProductInstanceSearchRequest> {
    @Override
    public List<Element> searchList(final ScrapProductInstanceSearchRequest searchRequest) {
        final Document document = Jsoup.parse(searchRequest.getCurrentPageSource());
        final StaticWebsiteScrapRequest scrapRequest = new StaticWebsiteScrapRequest(searchRequest.getProductInstance(), document);
        try {
            return (List<Element>) search(false, scrapRequest);
        } catch (ValueForSearchPropertyException e) {
            throw new RuntimeException(e);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ScraperIncorrectFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
