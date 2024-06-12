package org.sluja.searcher.webapp.service.enums.search;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class SearchPropertyGetComponent {

    public static Object getProperty(final Object property) throws ValueForSearchPropertyException {
        if(Objects.nonNull(property)) {
            if(checkOneElement(property) || checkManyElements(property)) {
                return property;
            }
        }
        throw new ValueForSearchPropertyException();
    }

    private static boolean checkOneElement(final Object property) {
        return property instanceof String && StringUtils.isNotEmpty((String) property);
    }

    private static boolean checkManyElements(final Object property) {
        return property instanceof List<?> && CollectionUtils.isNotEmpty((List<String>) property);
    }
}
