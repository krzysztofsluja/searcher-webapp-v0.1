package org.sluja.searcher.webapp.builder.request.product.category;

import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductManyCategoriesPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.utils.property.GetSearchPropertyValueUtils;

import java.util.List;
import java.util.Map;

public class ProductOneCategoryPageSearchRequestBuilder {

    public static ProductOneCategoryPageSearchRequest build(final SearchRequest request, final String category) throws ValueForSearchPropertyException {
        //TODO add error handling when class cannot be cast
        return new ProductOneCategoryPageSearchRequest(request.isDynamicWebsite(),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (List<String>) getProperty(request, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.CATEGORY_PAGE_AMOUNTS),
                ((Map<String, List<String>>) getProperty(request, SearchProperty.CATEGORY_PROPERTIES)).get(category));
    }

    public static ProductOneCategoryPageSearchRequest build(final ProductManyCategoriesPageSearchRequest request, final String category) throws ValueForSearchPropertyException {
        return new ProductOneCategoryPageSearchRequest(request.isDynamicWebsite(),
                request.getHomePageAddress(),
                request.getPageAddressExtractAttribute(),
                request.getAllCategoriesPageAddresses(),
                request.getCategoryPageAmounts(),
                getPropertiesForGivenCategory(request.getCategoryProperties(), category));
    }

    private static Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        return GetSearchPropertyValueUtils.getProperty(request, property);
    }

    private static List<String> getPropertiesForGivenCategory(final Map<String, List<String>> properties, final String category) throws ValueForSearchPropertyException {
        if(properties.isEmpty() || CollectionUtils.isEmpty(properties.get(category))) {
            throw new ValueForSearchPropertyException();
        }
        return properties.get(category);
    }
}
