package org.sluja.searcher.webapp.filter.product.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.sluja.searcher.webapp.dto.request.search.manyshops.ManyShopsSearchProductsRequest;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.wrapper.product.get.LimitCategoryAmountFilterWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LimitCategoryAmountFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final LimitCategoryAmountFilterWrapper wrappedRequest = new LimitCategoryAmountFilterWrapper(httpServletRequest);
            final ManyShopsSearchProductsRequest request = objectMapper.readValue(wrappedRequest.getBody(), ManyShopsSearchProductsRequest.class);
            modifyRequest(request);
            String modifiedBody = objectMapper.writeValueAsString(request);
            wrappedRequest.setBody(modifiedBody);

            filterChain.doFilter(wrappedRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void modifyRequest(final ManyShopsSearchProductsRequest request) {
        for(ProductScrapWithDefinedAttributes element : request.getShopsProperties()) {
            if(element.categories().size() > 4) {
                final List<String> modifiedCategoryList = element.categories().subList(0, Math.min(element.categories().size(), 4));
                final Map<String, List<String>> modifiedCategoryProperties = element.categoryProperties().entrySet()
                        .stream()
                        .filter(e -> modifiedCategoryList.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                final ProductScrapWithDefinedAttributes modifiedElement = ProductScrapWithDefinedAttributes.builder()
                        .shopName(element.shopName())
                        .homePageAddress(element.homePageAddress())
                        .productPrice(element.productPrice())
                        .productDescription(element.productDescription())
                        .productDescriptionAttribute(element.productDescriptionAttribute())
                        .productName(element.productName())
                        .productDiscountPrice(element.productDiscountPrice())
                        .productPageAddressAttribute(element.productPageAddressAttribute())
                        .productImageExtractAttribute(element.productImageExtractAttribute())
                        .div(element.div())
                        .plainPageAddressToFormat(element.plainPageAddressToFormat())
                        .productPageAddresses(element.productPageAddresses())
                        .productImageAddresses(element.productImageAddresses())
                        .productInstance(element.productInstance())
                        .context(element.context())
                        .categoryProperties(modifiedCategoryProperties)
                        .categories(modifiedCategoryList)
                        .categoryPageAmounts(element.categoryPageAmounts())
                        .allCategoriesPageAddresses(element.allCategoriesPageAddresses())
                        .pageAddressExtractAttribute(element.pageAddressExtractAttribute())
                        .build();
                final int elementIndex = request.getShopsProperties().indexOf(element);
                request.getShopsProperties().set(elementIndex, modifiedElement);
            }
        }
    }

}
