package org.sluja.searcher.webapp.service.cache;

import org.sluja.searcher.webapp.exception.cache.CacheElementForGivenKeyNotFound;

public interface CacheService<T> {

    void save(final T object);
    T get(final String key) throws CacheElementForGivenKeyNotFound;
    Boolean exists(final String key);
}
