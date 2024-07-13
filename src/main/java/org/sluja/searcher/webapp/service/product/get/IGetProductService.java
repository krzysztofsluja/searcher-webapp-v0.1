package org.sluja.searcher.webapp.service.product.get;

import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public interface IGetProductService<T, S extends SearchServiceRequestMarker> {

    T get(S request) throws ProductNotFoundException;
}
