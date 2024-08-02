package org.sluja.searcher.webapp.exception.cache;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

import java.util.List;

public class CacheKeyCreationFailedException extends ParametrizedExceptionWithErrorCodeAndMessage {

    public CacheKeyCreationFailedException(final String shopName, final String categoryName) {
        super("error.cache.key.creation.failed", List.of(shopName, categoryName), 7001L);
    }
}
