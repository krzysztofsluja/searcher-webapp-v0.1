package org.sluja.searcher.webapp.service.scraper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.enums.scraper.ScrapElement;
import org.sluja.searcher.webapp.exception.action.WebsiteUnsuccessfulActionException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.service.scraper.connector.SeleniumScraperConnector;
import org.sluja.searcher.webapp.utils.website.WebsiteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Qualifier("seleniumScraperService")
public class SeleniumScraperService implements Scraper<WebElement, ScrapElement>, WebsiteHandler<WebElement> {

    @Override
    public List<WebElement> scrap(final String pageUrl, final String property) throws ProductNotFoundException {
        if(StringUtils.isEmpty(pageUrl) || StringUtils.isEmpty(property)) {
            throw new ProductNotFoundException();
        }
        final WebDriver driver = SeleniumScraperConnector.INSTANCE.connect();
        driver.get(pageUrl);
        final List<WebElement> elements =driver.findElements(By.cssSelector(property));
        if(CollectionUtils.isNotEmpty(elements)) {
            throw new ProductNotFoundException();
        }
        return elements;
    }

    @Override
    public WebElement scrap(final WebElement element, final String property, final ScrapElement elementClass) throws ProductNotFoundException{
        if(Objects.isNull(element) || StringUtils.isEmpty(property) || Objects.isNull(elementClass)) {
            throw new ProductNotFoundException();
        }
        final By by = switch (elementClass) {
            case ID -> By.id(property);
            case CLASS_NAME -> By.className(property);
            case NAME -> By.name(property);
            case TAG -> By.tagName(property);
            case XPATH -> By.xpath(property);
            case CSS_SELECTOR -> By.cssSelector(property);
        };
        return element.findElement(by);
    }

    @Override
    public void doAction(final WebElement element) throws WebsiteUnsuccessfulActionException {
        if(Objects.isNull(element)) {
            throw new WebsiteUnsuccessfulActionException();
        }
        element.click();
    }
}
