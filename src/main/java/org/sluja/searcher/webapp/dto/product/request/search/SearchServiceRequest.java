package org.sluja.searcher.webapp.dto.product.request.search;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;

@AllArgsConstructor
@Builder
@Getter
public class SearchServiceRequest implements SearchServiceRequestMarker {

    private boolean dynamicWebsite;
    @NotEmpty(message = "error.validation.search.request.attribute.empty")
    private String homePageAddress;
}
