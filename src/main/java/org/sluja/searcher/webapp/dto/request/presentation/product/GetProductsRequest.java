package org.sluja.searcher.webapp.dto.request.presentation.product;

import jakarta.validation.constraints.NotEmpty;
import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;

import java.util.List;
import java.util.Map;

public record GetProductsRequest(List<String> shopsNames,
                                 Map<String, List<String>> categories,
                                 Map<String, Boolean> dynamicWebsitesOfShops,
                                 @NotEmpty(message = "error.validation.context.empty") String context) implements SearchServiceRequestMarker {
}
