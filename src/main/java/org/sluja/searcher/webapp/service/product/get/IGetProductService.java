package org.sluja.searcher.webapp.service.product.get;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public interface IGetProductService<T, S extends SearchServiceRequest> {

    T get(S request) throws ProductNotFoundException;
}
