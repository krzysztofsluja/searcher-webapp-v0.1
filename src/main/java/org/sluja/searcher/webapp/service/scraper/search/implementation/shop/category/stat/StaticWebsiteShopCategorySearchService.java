package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.stat;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.connect.StaticWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.connector.stat.StaticWebsiteConnector;
import org.sluja.searcher.webapp.service.enums.search.SearchPropertyGetComponent;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public class StaticWebsiteShopCategorySearchService extends ShopCategorySearchService<StaticWebsiteScrapRequest> {

    @Override
    public List<String> search(final SearchRequest request) throws ValueForSearchPropertyException, ProductNotFoundException, ConnectionTimeoutException, IOException, ScraperIncorrectFieldException {
        final String homePageAddress = (String) getProperty(request.properties().get(SearchProperty.HOME_PAGE_ADDRESS));
        final StaticWebsiteScrapRequest scrapRequest = new StaticWebsiteScrapRequest(request.dynamicWebsite(),
                StringUtils.EMPTY,
                StaticWebsiteConnector.INSTANCE.connectAndGetPage(new StaticWebsiteConnectRequest(homePageAddress)));
        final List<Element> elements = (List<Element>) super.search(request,scrapRequest);
        if(CollectionUtils.isEmpty(elements)) {
            throw new ProductNotFoundException();
        }
        final String pageAddressExtractAttribute = (String) getProperty(request.properties().get(SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE));
        return elements.stream()
                .map(element -> element.attr(pageAddressExtractAttribute))
                .map(element -> element.replaceAll("\\s", StringUtils.EMPTY))
                .distinct()
                .toList();
    }
}
