package org.sluja.searcher.webapp.dto.scraper.search;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.Map;

@Getter
public class DynamicWebsiteSearchRequest extends SearchRequest {

    @NotEmpty(message = DtoValidationErrorMessage.SCRAP_SEARCH_REQUEST_URL_EMPTY)
    private String url;
    public DynamicWebsiteSearchRequest(final boolean dynamicWebsite, final Map<SearchProperty, Object> properties, final String url) {
        super(dynamicWebsite, properties);
        this.url = url;
    }
}
