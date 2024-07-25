package org.sluja.searcher.webapp.dto.product.request.search;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

@AllArgsConstructor
@Builder
@Getter
public class SearchServiceRequest implements SearchServiceRequestMarker {

    private boolean dynamicWebsite;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String homePageAddress;
}
