package org.sluja.searcher.webapp.dto.connect;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

@Data
@AllArgsConstructor
public class StaticWebsiteConnectRequest implements ConnectRequest {

    @NotEmpty
    @Pattern(regexp = "^https://.*", message = DtoValidationErrorMessage.URL_WRONG_START)
    private String url;
}
