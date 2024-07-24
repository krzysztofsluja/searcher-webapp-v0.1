package org.sluja.searcher.webapp.dto.connect;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty
    @Pattern(regexp = "^https://.*", message = "URL must start with https://")
    private String url;
}
