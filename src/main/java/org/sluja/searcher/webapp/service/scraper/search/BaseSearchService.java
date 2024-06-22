package org.sluja.searcher.webapp.service.scraper.search;

import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.enums.search.SearchPropertyGetComponent;
import java.util.List;
import java.util.Objects;

public abstract class BaseSearchService<T extends SearchRequest> {

    protected Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        final Object requestProperty = request.getProperties().get(property);
        if(Objects.nonNull(requestProperty)) {
           return SearchPropertyGetComponent.getProperty(requestProperty);
        }
        throw new ValueForSearchPropertyException();
    }

    public abstract List<?> search(final T request) throws ProductNotFoundException;
}
