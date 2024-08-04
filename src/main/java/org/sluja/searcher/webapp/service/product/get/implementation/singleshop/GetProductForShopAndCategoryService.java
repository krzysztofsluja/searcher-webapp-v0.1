package org.sluja.searcher.webapp.service.product.get.implementation.singleshop;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.builder.request.product.instance.ProductInstanceSearchRequestBuilder;
import org.sluja.searcher.webapp.builder.request.product.object.BuildProductObjectRequestBuilder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.object.ProductObjectBuildFailedException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.object.IBuildProductObject;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Qualifier("getProductForShopAndCategoryService")
@RequiredArgsConstructor
public class GetProductForShopAndCategoryService implements IGetProductService<GetProductForShopAndCategoryResponse, GetProductForShopNameAndCategoryRequest> {

    private final ProductInstanceSearchService<DynamicWebsiteScrapRequest, ProductInstanceSearchRequest> dynamicWebsiteProductInstanceSearchService;
    private final IBuildProductObject buildProductObjectService;
    private final LoggerMessageUtils loggerMessageUtils;

    @Override
    @InputValidation(inputs = {GetProductForShopNameAndCategoryRequest.class})
    @ObjectMethodStartLog
    @ObjectMethodEndLog
    public GetProductForShopAndCategoryResponse get(final GetProductForShopNameAndCategoryRequest request) throws ProductNotFoundException  {
        final List<ProductDTO> products = getProducts(request);
        return GetProductForShopAndCategoryResponse.builder()
                .shopName(request.getShopName())
                .categoryName(request.getCategoryName())
                .products(products)
                .context(request.getContext())
                .build();
    }

    @InputValidation(inputs = {GetProductForShopNameAndCategoryRequest.class})
    private List<ProductDTO> getProducts(final GetProductForShopNameAndCategoryRequest request) throws ProductNotFoundException{
        final ProductInstanceSearchRequest productInstanceSearchRequest = ProductInstanceSearchRequestBuilder.build(request);
        List<Element> elements = Collections.emptyList();
        try {
             elements = (List<Element>) dynamicWebsiteProductInstanceSearchService.searchList(productInstanceSearchRequest);
        } catch (final ProductNotFoundException e) {
            loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessageCode(),
                    e.getErrorCode());
            loggerMessageUtils.getInfoLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    InformationMessageBuilder.buildParametrizedMessage("info.product.category.continue.searching", List.of(request.getCategoryName(), request.getShopName())));
            return Collections.emptyList();
        }
        final BuildProductObjectRequest buildProductObjectRequest = BuildProductObjectRequestBuilder.build(request, elements);
        final List<ProductDTO> products = buildProductObjectService.build(buildProductObjectRequest);
        if(CollectionUtils.isEmpty(products)) {
            throw new ProductObjectBuildFailedException();
        }
        return products;
    }
}
