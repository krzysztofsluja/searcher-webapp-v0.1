package org.sluja.searcher.webapp.exception.cache;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class CacheKeyNotFoundException extends ExceptionWithErrorCodeAndMessage {
    public CacheKeyNotFoundException() {
        super("error.cache.key.not.found", 7002L);
    }
}
