package org.sluja.searcher.webapp.service.product.search;

import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;

@FunctionalInterface
public interface IProductSearch<T,R> {
    R search(final T request) throws ProductNotFoundException;
}
