package org.sluja.searcher.webapp.utils.property;

import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.service.enums.search.SearchPropertyGetComponent;

import java.util.Objects;

public class GetSearchPropertyValueUtils {

    public static Object getProperty(final SearchRequest request, final SearchProperty property) throws ValueForSearchPropertyException {
        final Object requestProperty = request.getProperties().get(property);
        if(Objects.nonNull(requestProperty)) {
            return SearchPropertyGetComponent.getProperty(requestProperty);
        }
        throw new ValueForSearchPropertyException();
    }
}
