package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.dynamic;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.sluja.searcher.webapp.builder.request.product.category.ProductOneCategoryPageSearchRequestBuilder;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductManyCategoriesPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductManyCategoriesPageSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("dynamicWebsiteProductManyCategoriesPageSearchService")
public class DynamicWebsiteProductManyCategoriesPageSearchService extends ProductManyCategoriesPageSearchService<DynamicWebsiteScrapRequest, ProductManyCategoriesPageSearchRequest> {

    private final ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> dynamicWebsiteProductCategoryPageSearchService;

    @Override
    public Map<String, List<String>> searchMap(final ProductManyCategoriesPageSearchRequest request) throws ProductNotFoundException {
        if (Objects.nonNull(request) && (CollectionUtils.isNotEmpty(request.getCategories()) || CollectionUtils.isNotEmpty(request.getCategoryProperties().keySet()))) {
            final ExecutorService executor = Executors.newFixedThreadPool(request.getCategories().size());
            final List<Future<Pair<String, List<String>>>> futures = request.getCategories()
                    .stream()
                    .map(category -> executor.submit(getProductCategoryPageSearchServiceFunction(request).apply(category)))
                    .toList();
            final List<Pair<String, List<String>>> resultList = futures.stream()
                    .map(future -> {
                        try{
                            return future.get();
                        }catch(Exception ex) {
                            //TODO logging
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .toList();
            return resultList.stream()
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        }
        throw new CategoryPageAddressNotFoundException();
    }

    private Function<String, Callable<Pair<String, List<String>>>> getProductCategoryPageSearchServiceFunction(final ProductManyCategoriesPageSearchRequest request) {
        return category -> (Callable<Pair<String, List<String>>>) () -> {
            final ProductOneCategoryPageSearchRequest productCategoryPageSearchRequest = ProductOneCategoryPageSearchRequestBuilder.build(request, category);
            List<String> result;
            try {
                result = (List<String>) dynamicWebsiteProductCategoryPageSearchService.searchList(productCategoryPageSearchRequest);
                if(CollectionUtils.isEmpty(result)) {
                    throw new CategoryPageAddressNotFoundException();
                }
            } catch (ProductNotFoundException | UnsupportedOperationException e) {
                result = Collections.emptyList();
            }
            return Pair.of(category, result);
        };
    }

}
