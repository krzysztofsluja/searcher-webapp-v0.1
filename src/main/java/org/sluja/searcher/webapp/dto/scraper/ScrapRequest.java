package org.sluja.searcher.webapp.dto.scraper;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.sluja.searcher.webapp.dto.marker.scraper.IScrapRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

@Data
public class ScrapRequest implements IScrapRequest {

    protected boolean isDynamicWebsite;
    @NotEmpty(message = DtoValidationErrorMessage.SCRAP_REQUEST_PROPERTY_EMPTY)
    protected String property;

    public ScrapRequest(boolean isDynamicWebsite, String property) {
        this.isDynamicWebsite = isDynamicWebsite;
        this.property = property;
    }
}
