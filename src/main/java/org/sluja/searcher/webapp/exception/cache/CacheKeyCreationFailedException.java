package org.sluja.searcher.webapp.exception.cache;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class CacheKeyCreationFailedException extends ExceptionWithErrorCodeAndMessage {

    public CacheKeyCreationFailedException() {
        super("error.cache.key.creation.failed", 7001L);
    }
}
