package org.sluja.searcher.webapp.dto.connect;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;

@Data
@Builder
@Getter
public class DynamicWebsiteConnectRequest implements ConnectRequest {

    private WebDriver driver;
    private String url;
}
