package org.sluja.searcher.webapp.dto.connect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.sluja.searcher.webapp.dto.marker.connect.ConnectRequest;
@Data
@AllArgsConstructor
public class StaticWebsiteConnectRequest implements ConnectRequest {

    private String url;
}
