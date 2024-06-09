package org.sluja.searcher.webapp.utils.website;

import org.sluja.searcher.webapp.exception.action.WebsiteUnsuccessfulActionException;

public interface WebsiteHandler<T> {

    void doAction(T element) throws WebsiteUnsuccessfulActionException;
}
