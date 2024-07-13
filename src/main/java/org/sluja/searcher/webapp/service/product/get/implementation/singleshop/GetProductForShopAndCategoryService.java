package org.sluja.searcher.webapp.service.product.get.implementation.singleshop;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.builder.request.product.instance.ProductInstanceSearchRequestBuilder;
import org.sluja.searcher.webapp.builder.request.product.object.BuildProductObjectRequestBuilder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.object.ProductObjectBuildFailedException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.object.IBuildProductObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("getProductForShopAndCategoryService")
@RequiredArgsConstructor
public class GetProductForShopAndCategoryService implements IGetProductService<List<ProductDTO>, GetProductForShopNameAndCategoryRequest> {

    private final ProductInstanceSearchService<DynamicWebsiteScrapRequest, ProductInstanceSearchRequest> dynamicWebsiteProductInstanceSearchService;
    private final IBuildProductObject buildProductObjectService;

    @Override
    public List<ProductDTO> get(final GetProductForShopNameAndCategoryRequest request) throws ProductNotFoundException{
        final ProductInstanceSearchRequest productInstanceSearchRequest = ProductInstanceSearchRequestBuilder.build(request);
        final List<Element> elements = (List<Element>) dynamicWebsiteProductInstanceSearchService.searchList(productInstanceSearchRequest);
        final BuildProductObjectRequest buildProductObjectRequest = BuildProductObjectRequestBuilder.build(request, elements);
        final List<ProductDTO> products = buildProductObjectService.build(buildProductObjectRequest);
        if(CollectionUtils.isEmpty(products)) {
            throw new ProductObjectBuildFailedException();
        }
        return products;
    }
}
