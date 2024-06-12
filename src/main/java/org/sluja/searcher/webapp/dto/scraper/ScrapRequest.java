package org.sluja.searcher.webapp.dto.scraper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sluja.searcher.webapp.annotation.NullableOrEmptyField;
import org.sluja.searcher.webapp.dto.marker.scraper.IScrapRequest;

import java.util.List;
import java.util.Map;

@Data
public class ScrapRequest implements IScrapRequest {

    protected boolean isDynamicWebsite;
    protected String property;

    public ScrapRequest(boolean isDynamicWebsite, String property) {
        this.isDynamicWebsite = isDynamicWebsite;
        this.property = property;
    }
}
