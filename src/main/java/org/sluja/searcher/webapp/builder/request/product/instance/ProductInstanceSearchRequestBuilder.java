package org.sluja.searcher.webapp.builder.request.product.instance;

import org.sluja.searcher.webapp.dto.product.request.search.instance.ProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.utils.property.GetSearchPropertyValueUtils;

import java.util.List;
import java.util.Map;

public class ProductInstanceSearchRequestBuilder {

    public static ProductInstanceSearchRequest build(final SearchRequest request, final String category) throws ValueForSearchPropertyException {
        return new ProductInstanceSearchRequest(request.isDynamicWebsite(),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (List<String>) getProperty(request, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.CATEGORY_PAGE_AMOUNTS),
                ((Map<String, List<String>>) getProperty(request, SearchProperty.CATEGORY_PROPERTIES)).get(category),
                (String) getProperty(request, SearchProperty.PRODUCT_INSTANCE));
    }

    private static Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        return GetSearchPropertyValueUtils.getProperty(request, property);
    }
}
