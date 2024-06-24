package org.sluja.searcher.webapp.dto.product.request.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SearchServiceRequest {

    private boolean dynamicWebsite;
    private String homePageAddress;
}
