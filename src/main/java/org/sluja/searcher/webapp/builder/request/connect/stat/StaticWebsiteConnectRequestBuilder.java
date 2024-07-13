package org.sluja.searcher.webapp.builder.request.connect.stat;

import org.sluja.searcher.webapp.dto.connect.StaticWebsiteConnectRequest;

public class StaticWebsiteConnectRequestBuilder {

    public static StaticWebsiteConnectRequest build(final String connectUrl) {
        return new StaticWebsiteConnectRequest(connectUrl);
    }
}
