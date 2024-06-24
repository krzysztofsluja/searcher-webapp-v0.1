package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.dynamic;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.builder.request.product.category.ProductOneCategoryPageSearchRequestBuilder;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductManyCategoriesPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductManyCategoriesPageSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Qualifier("dynamicWebsiteProductManyCategoriesPageSearchService")
public class DynamicWebsiteProductManyCategoriesPageSearchService extends ProductManyCategoriesPageSearchService<DynamicWebsiteScrapRequest, ProductManyCategoriesPageSearchRequest> {

    private final ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> dynamicWebsiteProductCategoryPageSearchService;

    @Override
    public Map<String, List<String>> searchMap(final ProductManyCategoriesPageSearchRequest request) throws ProductNotFoundException {
        if (Objects.nonNull(request) && (CollectionUtils.isNotEmpty(request.getCategories()) || CollectionUtils.isNotEmpty(request.getCategoryProperties().keySet()))) {
            final Map<String, List<String>> map = new HashMap<>();
            for(final String category : request.getCategories()) {
                try {
                    final ProductOneCategoryPageSearchRequest productCategoryPageSearchRequest = ProductOneCategoryPageSearchRequestBuilder.build(request, category);
                    map.put(category, (List<String>) dynamicWebsiteProductCategoryPageSearchService.searchList(productCategoryPageSearchRequest));
                } catch (ValueForSearchPropertyException e) {
                    //TODO logging
                    throw new CategoryPageAddressNotFoundException();
                }
            }
            return map;
        }
        throw new CategoryPageAddressNotFoundException();
    }
}
