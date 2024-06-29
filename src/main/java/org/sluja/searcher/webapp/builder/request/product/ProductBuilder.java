package org.sluja.searcher.webapp.builder.request.product;

import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.utils.property.GetSearchPropertyValueUtils;

public abstract class ProductBuilder {

    protected static Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        return GetSearchPropertyValueUtils.getProperty(request, property);
    }
}
