package org.sluja.searcher.webapp.builder.request.connect.dynamic;

import org.openqa.selenium.WebDriver;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;

public class DynamicWebsiteConnectRequestBuilder {

    public static DynamicWebsiteConnectRequest build(final String url, final WebDriver driver) {
        return DynamicWebsiteConnectRequest.builder()
                .url(url)
                .driver(driver)
                .build();
    }
}
