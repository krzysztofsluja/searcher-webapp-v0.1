package org.sluja.searcher.webapp.builder.request.product.category;

import org.sluja.searcher.webapp.dto.product.request.search.category.ProductManyCategoriesPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.utils.property.GetSearchPropertyValueUtils;

import java.util.List;
import java.util.Map;

public class ProductManyCategoryPageSearchRequestBuilder {

    public static ProductManyCategoriesPageSearchRequest build(final SearchRequest request) throws ValueForSearchPropertyException {
        //TODO add error handling when class cannot be cast
        return new ProductManyCategoriesPageSearchRequest(request.isDynamicWebsite(),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (List<String>) getProperty(request, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.CATEGORY_PAGE_AMOUNTS),
                ((Map<String, List<String>>) getProperty(request, SearchProperty.CATEGORY_PROPERTIES)),
                (List<String>) getProperty(request, SearchProperty.CATEGORIES));
    }

    private static Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        return GetSearchPropertyValueUtils.getProperty(request, property);
    }
}
