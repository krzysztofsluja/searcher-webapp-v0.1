package org.sluja.searcher.webapp.service.connector.dynamic;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.utils.connector.IConnector;

import java.io.IOException;
import java.util.Objects;

public class DynamicWebsiteConnector implements IConnector<WebDriver, DynamicWebsiteConnectRequest> {

    public static DynamicWebsiteConnector INSTANCE = new DynamicWebsiteConnector();
    @Override
    public WebDriver connectAndGetPage(final DynamicWebsiteConnectRequest request) throws ConnectionTimeoutException, IOException {
        if(StringUtils.isEmpty(request.getUrl())) {
            throw new ConnectionTimeoutException();
        }
        final WebDriver driver = Objects.nonNull(request.getDriver()) ? request.getDriver() : new ChromeDriver();
        driver.get(request.getUrl());
        return driver;
    }

    public static void quit(final WebDriver driver) {
        if(Objects.nonNull(driver)) {
            driver.quit();
        }
    }
}
