package org.sluja.searcher.webapp.dto.scraper.search;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;

import java.util.Map;

@Getter
public class DynamicWebsiteSearchRequest extends SearchRequest {

    @NotEmpty(message = "error.validation.scrap.search.request.url.empty")
    private String url;
    public DynamicWebsiteSearchRequest(final boolean dynamicWebsite, final Map<SearchProperty, Object> properties, final String url) {
        super(dynamicWebsite, properties);
        this.url = url;
    }
}
