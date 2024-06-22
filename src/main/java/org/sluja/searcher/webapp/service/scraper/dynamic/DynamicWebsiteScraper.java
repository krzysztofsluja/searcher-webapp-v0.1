package org.sluja.searcher.webapp.service.scraper.dynamic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.enums.scraper.dynamic.ScraperByAttribute;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.exception.scraper.dynamic.DynamicWebsiteScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;

import java.util.List;
import java.util.Objects;

public class DynamicWebsiteScraper implements WebsiteScraper<WebElement, DynamicWebsiteScrapRequest> {
    //TODO logging on exception
    @Override
    public List<WebElement> scrap(final DynamicWebsiteScrapRequest request) throws ScraperIncorrectFieldException {
        try {
            if (Objects.nonNull(request)) {
                return request.getDriver().findElements(getBy(request.getAttribute(), request.getProperty()));
            }
        } catch (Exception ex) {
            //TODO logging
        }
        throw new DynamicWebsiteScraperIncorrectFieldException();
    }

    private By getBy(final ScraperByAttribute attribute, final String property) {
        return switch (attribute) {
            case ID -> By.id(property);
            case CLASS_NAME -> By.className(property);
            case NAME -> By.name(property);
            case CSS_SELECTOR -> By.cssSelector(property);
            case XPATH -> By.xpath(property);
            case LINK_TEXT -> By.linkText(property);
            case PARTIAL_LINK_TEXT -> By.partialLinkText(property);
            case TAG_NAME -> By.tagName(property);
        };
    }
}
