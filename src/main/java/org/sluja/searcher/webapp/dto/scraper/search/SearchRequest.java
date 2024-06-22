package org.sluja.searcher.webapp.dto.scraper.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;

import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class SearchRequest {

    private boolean dynamicWebsite;
    private Map<SearchProperty, Object> properties;
}
