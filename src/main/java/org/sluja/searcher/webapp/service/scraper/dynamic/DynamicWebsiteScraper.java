package org.sluja.searcher.webapp.service.scraper.dynamic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodEndLog;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodStartLog;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.enums.scraper.dynamic.ScraperByAttribute;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.exception.scraper.dynamic.DynamicWebsiteScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("dynamicWebsiteScraper")
@RequiredArgsConstructor
@Slf4j
public class DynamicWebsiteScraper implements WebsiteScraper<List<WebElement>, DynamicWebsiteScrapRequest> {

    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    @MethodStartLog
    @MethodEndLog
    public List<WebElement> scrap(final DynamicWebsiteScrapRequest request) throws ScraperIncorrectFieldException {
        if (Objects.nonNull(request)) {
            return request.getDriver().findElements(getBy(request.getAttribute(), request.getProperty()));
        }
        log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                LoggerUtils.getCurrentMethodName(),
                "dynamic website scrap request is null"));
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
