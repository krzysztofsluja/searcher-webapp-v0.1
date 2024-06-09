package org.sluja.searcher.webapp.service.scraper.connector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sluja.searcher.webapp.utils.connector.Connector;

public class SeleniumScraperConnector implements Connector<WebDriver> {

    public static SeleniumScraperConnector INSTANCE = new SeleniumScraperConnector();

    @Override
    public WebDriver connect() {
        final ChromeOptions options= new ChromeOptions();
        options.addArguments("--headless");
        final WebDriver driver = new ChromeDriver(options);
        return driver;
    }
}
