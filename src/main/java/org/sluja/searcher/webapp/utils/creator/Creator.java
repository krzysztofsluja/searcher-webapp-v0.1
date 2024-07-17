package org.sluja.searcher.webapp.utils.creator;

import org.sluja.searcher.webapp.exception.cache.CacheKeyCreationFailedException;

public interface Creator<T,S> {

    T create(S request) throws CacheKeyCreationFailedException;
}
