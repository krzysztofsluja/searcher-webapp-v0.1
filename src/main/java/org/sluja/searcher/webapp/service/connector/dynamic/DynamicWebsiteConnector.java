package org.sluja.searcher.webapp.service.connector.dynamic;

import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Scope("prototype")
@Qualifier("dynamicWebsiteConnector")
public class DynamicWebsiteConnector implements IConnector<WebDriver, DynamicWebsiteConnectRequest> {

    //public static DynamicWebsiteConnector INSTANCE = new DynamicWebsiteConnector();
    @Override
    public WebDriver connectAndGetPage(@Valid final DynamicWebsiteConnectRequest request) throws ConnectionTimeoutException, IOException {
        if(StringUtils.isEmpty(request.getUrl())) {
            throw new ConnectionTimeoutException();
        }
        final WebDriver driver = Objects.nonNull(request.getDriver()) ? request.getDriver() : getWebDriver();
        driver.get(request.getUrl());
        return driver;
    }

    public  void quit(final WebDriver driver) {
        if(Objects.nonNull(driver)) {
            driver.quit();
        }
    }

    private WebDriver getWebDriver() {
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
