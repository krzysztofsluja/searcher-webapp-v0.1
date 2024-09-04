package org.sluja.searcher.webapp.service.frontend.communication.main.contact;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.contact.MainViewContactFormSessionAttribute;
import org.sluja.searcher.webapp.service.communication.email.IEmailSender;
import org.sluja.searcher.webapp.service.frontend.view.mainview.MainViewService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainViewContactFormLayoutService {

    private final IEmailSender contactEmailSender;
    private final MainViewContactFormSessionAttribute mainViewContactFormSessionAttribute;
    private final MessageReader communicationMessageReader;
    private final MainViewService mainViewService;

    public enum MessageElement {
        SHOP_ADDRESS,
        SHOP_MAIN_TEXT,
        USER_FEEDBACK_EMAIL
    }

    public Boolean isOptionToSendFeedbackToUser() {
        return mainViewContactFormSessionAttribute.isShouldClientBeNotified();
    }

    public void send(final String value) {
        contactEmailSender.send(value);
    }

    public String getShopAddressUrl() {
        return StringUtils.isNotEmpty(mainViewContactFormSessionAttribute.getShopAddressUrl()) ? mainViewContactFormSessionAttribute.getShopAddressUrl() : StringUtils.EMPTY;
    }

    public void setOptionToSendFeedbackToUser(final boolean shouldClientBeNotified) {
        mainViewContactFormSessionAttribute.setShouldClientBeNotified(shouldClientBeNotified);
    }

    public String getShopMainText() {
        return StringUtils.isNotEmpty(mainViewContactFormSessionAttribute.getShopNameTextWithAdditionalInformation()) ? mainViewContactFormSessionAttribute.getShopNameTextWithAdditionalInformation() : StringUtils.EMPTY;
    }

    public String getReplyUserEmail() {
        return mainViewContactFormSessionAttribute.getContactUserEmail();
    }

    private String buildMailText() {
        final String mainText = new StringBuilder()
                .append(communicationMessageReader.getPropertyValueOrEmptyOnError("communication.shop.name.label"))
                .append(StringUtils.SPACE)
                .append(mainViewContactFormSessionAttribute.getShopNameTextWithAdditionalInformation())
                .append(StringUtils.LF)
                .append(communicationMessageReader.getPropertyValueOrEmptyOnError("communication.shop.page.url.label"))
                .append(StringUtils.SPACE)
                .append(mainViewContactFormSessionAttribute.getShopAddressUrl())
                .toString();
        if(!mainViewContactFormSessionAttribute.isShouldClientBeNotified()) {
            return mainText;
        }
        return new StringBuilder()
                .append(mainText)
                .append(StringUtils.LF)
                .append(communicationMessageReader.getPropertyValueOrEmptyOnError("communication.user.contact.mail.label"))
                .append(StringUtils.SPACE)
                .append(mainViewContactFormSessionAttribute.getContactUserEmail())
                .toString();
    }

    public void sendShopRequest() {
        if(StringUtils.isNotEmpty(mainViewContactFormSessionAttribute.getShopAddressUrl())
            && StringUtils.isNotEmpty(mainViewContactFormSessionAttribute.getShopNameTextWithAdditionalInformation())) {
            this.send(buildMailText());
        }
    }

    public void setMailMessageText(final MessageElement messageElement,final String value) {
        switch (messageElement) {
            case SHOP_ADDRESS -> mainViewContactFormSessionAttribute.setShopAddressUrl(value);
            case SHOP_MAIN_TEXT -> mainViewContactFormSessionAttribute.setShopNameTextWithAdditionalInformation(value);
            case USER_FEEDBACK_EMAIL -> mainViewContactFormSessionAttribute.setContactUserEmail(value);
        }
    }

    public void refresh() {
        mainViewService.refreshContactForm();
    }
}
