package org.sluja.searcher.webapp.exception.cache;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class CacheElementForGivenKeyNotFound extends ExceptionWithErrorCodeAndMessage {
    public CacheElementForGivenKeyNotFound() {
        super("error.cache.element.not.found", 7003L);
    }
}
