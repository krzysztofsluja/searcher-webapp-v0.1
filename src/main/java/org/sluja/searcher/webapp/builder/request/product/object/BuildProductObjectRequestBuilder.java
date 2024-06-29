package org.sluja.searcher.webapp.builder.request.product.object;

import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.List;

public class BuildProductObjectRequestBuilder extends ProductBuilder {

    public static BuildProductObjectRequest build(final SearchRequest request, final List<Element> productInstances, final String categoryName) throws ValueForSearchPropertyException {
        return new BuildProductObjectRequest((String) getProperty(request, SearchProperty.PRODUCT_PRICE),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PRODUCT_NAME),
                (String) getProperty(request, SearchProperty.PRODUCT_DISCOUNT_PRICE),
                (String) getProperty(request, SearchProperty.PRODUCT_IMAGE_EXTRACT_ATTRIBUTE),
                (String) getProperty(request, SearchProperty.DIV),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (String) getProperty(request, SearchProperty.PLAIN_PAGE_ADDRESS_TO_FORMAT),
                productInstances,
                (List<String>) getProperty(request, SearchProperty.PRODUCT_PAGE_ADDRESSES),
                (List<String>) getProperty(request, SearchProperty.PRODUCT_IMAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.SHOP_NAME),
                categoryName);
    }

}
