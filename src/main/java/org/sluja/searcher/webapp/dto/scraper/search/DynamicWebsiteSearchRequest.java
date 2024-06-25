package org.sluja.searcher.webapp.dto.scraper.search;

import lombok.Builder;
import lombok.Getter;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;

import java.util.Map;

@Getter
public class DynamicWebsiteSearchRequest extends SearchRequest {

    private String url;
    public DynamicWebsiteSearchRequest(final boolean dynamicWebsite, final Map<SearchProperty, Object> properties, final String url) {
        super(dynamicWebsite, properties);
        this.url = url;
    }
}
