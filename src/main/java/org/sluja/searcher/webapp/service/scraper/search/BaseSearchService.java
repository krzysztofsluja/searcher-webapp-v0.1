package org.sluja.searcher.webapp.service.scraper.search;

import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.service.enums.search.SearchPropertyGetComponent;

public abstract class BaseSearchService {

    protected Object getProperty(final Object request) throws ValueForSearchPropertyException {
        return SearchPropertyGetComponent.getProperty(request);
    }
}
