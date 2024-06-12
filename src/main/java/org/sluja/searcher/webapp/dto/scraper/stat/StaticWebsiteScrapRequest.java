package org.sluja.searcher.webapp.dto.scraper.stat;

import lombok.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;

@Getter
@Setter
public class StaticWebsiteScrapRequest extends ScrapRequest {

    private Document document;
    private Element element;

    public StaticWebsiteScrapRequest(final boolean isDynamicWebsite, final String property, final Document document, final Element element) {
        super(isDynamicWebsite, property);
        this.document = document;
        this.element = element;
    }
}
