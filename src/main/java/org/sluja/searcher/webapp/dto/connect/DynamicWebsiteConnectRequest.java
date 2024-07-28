package org.sluja.searcher.webapp.dto.connect;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

@Data
@Builder
@Getter
public class DynamicWebsiteConnectRequest implements ConnectRequest {

    private WebDriver driver;
    @Pattern(regexp = "^https://.*", message = DtoValidationErrorMessage.URL_WRONG_START)
    private String url;
}
