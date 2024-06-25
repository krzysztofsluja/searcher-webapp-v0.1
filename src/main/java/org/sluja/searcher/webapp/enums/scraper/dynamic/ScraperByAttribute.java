package org.sluja.searcher.webapp.enums.scraper.dynamic;

public enum ScraperByAttribute {

    ID,
    CLASS_NAME,
    NAME,
    CSS_SELECTOR,
    XPATH,
    LINK_TEXT,
    PARTIAL_LINK_TEXT,
    TAG_NAME;

    public static ScraperByAttribute DEFAULT() {
        return CSS_SELECTOR;
    }
}
