package org.sluja.searcher.webapp.dto.scraper.dynamic;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.enums.scraper.dynamic.ScraperByAttribute;

@Getter
@Setter
public class DynamicWebsiteScrapRequest extends ScrapRequest {

    private ScraperByAttribute attribute;
    private WebDriver driver;

    public DynamicWebsiteScrapRequest(final String property, final ScraperByAttribute attribute, final WebDriver driver) {
        super(true, property);
        this.attribute = attribute;
        this.driver = driver;
    }

    public DynamicWebsiteScrapRequest(final String property, final WebDriver driver) {
        super(true, property);
        this.attribute = ScraperByAttribute.DEFAULT();
        this.driver = driver;
    }
}
