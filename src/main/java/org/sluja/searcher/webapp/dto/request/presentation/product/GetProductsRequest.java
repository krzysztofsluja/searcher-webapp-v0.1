package org.sluja.searcher.webapp.dto.request.presentation.product;

import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;

import java.util.List;
import java.util.Map;

public record GetProductsRequest(List<String> shopsNames,
                                 Map<String, List<String>> categories,
                                 Map<String, Boolean> dynamicWebsitesOfShops,
                                 String context) implements SearchServiceRequestMarker {
}
