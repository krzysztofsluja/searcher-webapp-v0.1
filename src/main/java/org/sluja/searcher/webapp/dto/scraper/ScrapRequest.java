package org.sluja.searcher.webapp.dto.scraper;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.sluja.searcher.webapp.dto.marker.scraper.IScrapRequest;

@Data
public class ScrapRequest implements IScrapRequest {

    protected boolean isDynamicWebsite;
    @NotEmpty(message = "error.validation.scrap.request.property.empty")
    protected String property;

    public ScrapRequest(boolean isDynamicWebsite, String property) {
        this.isDynamicWebsite = isDynamicWebsite;
        this.property = property;
    }
}
