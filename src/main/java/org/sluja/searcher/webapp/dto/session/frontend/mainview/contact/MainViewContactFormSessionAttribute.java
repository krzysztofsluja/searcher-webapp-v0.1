package org.sluja.searcher.webapp.dto.session.frontend.mainview.contact;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Setter
@Getter
public class MainViewContactFormSessionAttribute {

    private String shopNameTextWithAdditionalInformation;
    private String shopAddressUrl;
    private String contactUserEmail;
    private boolean shouldClientBeNotified;

    public void clear() {
        this.setShopAddressUrl(StringUtils.EMPTY);
        this.setShopNameTextWithAdditionalInformation(StringUtils.EMPTY);
        this.setContactUserEmail(StringUtils.EMPTY);
        this.setShouldClientBeNotified(false);
    }
}
