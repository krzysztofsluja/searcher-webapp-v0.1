package org.sluja.searcher.webapp.dto.scraper.search;

import lombok.Builder;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;

import java.util.Map;

@Builder
public record SearchRequest(boolean dynamicWebsite,
                            Map<SearchProperty, Object> properties) {
}
