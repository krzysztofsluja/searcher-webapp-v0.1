package org.sluja.searcher.webapp.dto.connect;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;
@Data
@AllArgsConstructor
public class StaticWebsiteConnectRequest implements ConnectRequest {

    @NotEmpty
    @Pattern(regexp = "^https://.*", message = "URL must start with https://")
    private String url;
}
