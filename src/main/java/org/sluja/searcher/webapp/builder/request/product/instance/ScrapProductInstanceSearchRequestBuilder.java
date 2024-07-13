package org.sluja.searcher.webapp.builder.request.product.instance;

import org.sluja.searcher.webapp.dto.product.request.search.instance.ProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ScrapProductInstanceSearchRequest;

public class ScrapProductInstanceSearchRequestBuilder {

    public static ScrapProductInstanceSearchRequest build(final ProductInstanceSearchRequest request, final String pageSource) {
        return new ScrapProductInstanceSearchRequest(request.isDynamicWebsite(),
                request.getHomePageAddress(),
                pageSource,
                request.getProductInstance());
    }
}
