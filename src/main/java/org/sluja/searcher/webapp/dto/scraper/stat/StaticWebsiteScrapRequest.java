package org.sluja.searcher.webapp.dto.scraper.stat;

import lombok.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;

@Getter
@Setter
public class StaticWebsiteScrapRequest extends ScrapRequest {

    private Document document;

    public StaticWebsiteScrapRequest(final boolean isDynamicWebsite, final String property, final Document document) {
        super(isDynamicWebsite, property);
        this.document = document;
    }
}
