package org.sluja.searcher.webapp.dto.scraper.stat;

import lombok.Getter;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;

@Getter
public class StaticWebsiteElementScrapRequest extends ScrapRequest {

    private Element element;
    public StaticWebsiteElementScrapRequest(final String property, final Element element) {
        super(false, property);
        this.element = element;
    }
}
