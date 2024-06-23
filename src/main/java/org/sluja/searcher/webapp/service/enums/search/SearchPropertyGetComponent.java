package org.sluja.searcher.webapp.service.enums.search;

import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class SearchPropertyGetComponent {

    public static Object getProperty(final Object property) throws ValueForSearchPropertyException {
        if(Objects.nonNull(property)) {
            if(checkOneElement(property) || checkManyElements(property) || checkMap(property)) {
                return property;
            }
        }
        throw new ValueForSearchPropertyException();
    }

    private static boolean checkOneElement(final Object property) {
        return property instanceof String;
    }

    private static boolean checkManyElements(final Object property) {
        return property instanceof List<?>;
    }
    private static boolean checkMap(final Object property) {
        return property instanceof Map<?,?>;
    }
}
