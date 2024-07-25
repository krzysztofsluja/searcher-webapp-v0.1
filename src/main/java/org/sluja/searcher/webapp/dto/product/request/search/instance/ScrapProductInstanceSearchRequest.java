package org.sluja.searcher.webapp.dto.product.request.search.instance;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

@Getter
public class ScrapProductInstanceSearchRequest extends SearchServiceRequest {

    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String currentPageSource;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
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
