package org.sluja.searcher.webapp.exception.action;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class WebsiteUnsuccessfulActionException extends ExceptionWithErrorCodeAndMessage {
    public WebsiteUnsuccessfulActionException() {
        super("error.action.website.unsuccessful", 5001L);
    }
}
