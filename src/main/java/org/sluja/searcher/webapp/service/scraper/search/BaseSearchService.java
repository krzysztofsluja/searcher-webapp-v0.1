package org.sluja.searcher.webapp.service.scraper.search;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.utils.property.GetSearchPropertyValueUtils;

import java.util.List;
import java.util.Map;

public abstract class BaseSearchService<T extends SearchServiceRequest> {

    protected Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        return GetSearchPropertyValueUtils.getProperty(request, property);
    }

    public List<?> searchList(final T request) throws ProductNotFoundException, UnsupportedOperationException {
        throw new UnsupportedOperationException("The search method returning List is not supported in that class!");
    }
    public Map<?,?> searchMap(final T request) throws UnsupportedOperationException, ProductNotFoundException {
        throw new UnsupportedOperationException("The search method returning Map is not supported in that class!");
    }
}
