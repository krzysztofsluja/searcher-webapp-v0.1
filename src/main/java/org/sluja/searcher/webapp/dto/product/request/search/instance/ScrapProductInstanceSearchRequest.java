package org.sluja.searcher.webapp.dto.product.request.search.instance;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;

@Getter
public class ScrapProductInstanceSearchRequest extends SearchServiceRequest {

    private String currentPageSource;
    private String productInstance;
    public ScrapProductInstanceSearchRequest(final boolean dynamicWebsite,
                                             final String homePageAddress,
                                             final String currentPageSource,
                                             final String productInstance) {
        super(dynamicWebsite, homePageAddress);
        this.currentPageSource = currentPageSource;
        this.productInstance = productInstance;
    }
}
