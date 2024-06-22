package org.sluja.searcher.webapp.builder.request.search;

import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.scraper.search.IncorrectInputException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchRequestBuilder {

    public static SearchRequest buildFromAnotherRequest(final boolean isDynamicWebsite, final List<SearchProperty> searchProperties, final SearchRequest request) throws IncorrectInputException {
        if(Objects.isNull(request)) {
            throw new IncorrectInputException("Search Request");
        }
        return SearchRequest.builder()
                .dynamicWebsite(isDynamicWebsite)
                .properties(buildPropertiesMap(searchProperties, request))
                .build();
    }

    private static Map<SearchProperty, Object> buildPropertiesMap(final List<SearchProperty> searchProperties, final SearchRequest request) {
        return searchProperties.stream()
                .collect(Collectors.toMap(property -> property, request.getProperties()::get));
    }
}
