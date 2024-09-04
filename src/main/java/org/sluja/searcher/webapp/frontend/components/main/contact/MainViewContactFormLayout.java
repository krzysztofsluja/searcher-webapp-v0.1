package org.sluja.searcher.webapp.frontend.components.main.contact;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.service.frontend.communication.main.contact.MainViewContactFormLayoutService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class MainViewContactFormLayout extends IContactFormLayout{

    private final MessageReader viewElementMessageReader;
    private final MainViewContactFormLayoutService mainViewContactFormLayoutService;

    private VerticalLayout getLayout() {
        final VerticalLayout layout = new VerticalLayout();
        layout.add(getContactToUserLayout(), getContactTextFields(), getSendButton());
        return layout;
    }

    private VerticalLayout getContactTextFields() {
        final VerticalLayout layout = new VerticalLayout();
        final TextArea messageTextArea = new TextArea();

        messageTextArea.setPlaceholder(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.shop.name.area.placeholder"));
        messageTextArea.setRequired(true);
        messageTextArea.setRequiredIndicatorVisible(true);
        messageTextArea.setErrorMessage(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.shop.name.area.error.message"));
        messageTextArea.addValueChangeListener(_ -> {
            mainViewContactFormLayoutService.setMailMessageText(MainViewContactFormLayoutService.MessageElement.SHOP_MAIN_TEXT, messageTextArea.getValue());
        });
        messageTextArea.setValue(mainViewContactFormLayoutService.getShopMainText());

        final TextField shopAddressTextField = new TextField();
        shopAddressTextField.setPlaceholder(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.shop.address.field.placeholder"));
        shopAddressTextField.setRequired(true);
        shopAddressTextField.setRequiredIndicatorVisible(true);
        shopAddressTextField.addValueChangeListener(_ -> {
           mainViewContactFormLayoutService.setMailMessageText(MainViewContactFormLayoutService.MessageElement.SHOP_ADDRESS, shopAddressTextField.getValue());
        });
        shopAddressTextField.setValue(mainViewContactFormLayoutService.getShopAddressUrl());

        layout.add(messageTextArea, shopAddressTextField);
        return layout;
    }

    private VerticalLayout getContactToUserLayout() {
        final VerticalLayout layout = new VerticalLayout();
        final EmailField contactToUser = getReplyUserEmailField();
        final Checkbox contactUserOptionCheckbox = getReplyUserEmailOptionCheckbox();
        contactToUser.setEnabled(contactUserOptionCheckbox.getValue());
        layout.add(contactToUser, contactUserOptionCheckbox);
        return layout;
    }

    private EmailField getReplyUserEmailField() {
        final EmailField contactToUser = new EmailField();
        contactToUser.setErrorMessage(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.reply.user.email.error.message"));
        contactToUser.addValueChangeListener(event -> {
            mainViewContactFormLayoutService.setMailMessageText(MainViewContactFormLayoutService.MessageElement.USER_FEEDBACK_EMAIL, event.getValue());
        });
        return contactToUser;
    }

    private Checkbox getReplyUserEmailOptionCheckbox() {
        final Checkbox checkbox = new Checkbox(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.reply.user.email.checkbox"));
        checkbox.setValue(mainViewContactFormLayoutService.isOptionToSendFeedbackToUser());
        checkbox.addValueChangeListener(event -> {
            mainViewContactFormLayoutService.setOptionToSendFeedbackToUser(event.getValue());
            mainViewContactFormLayoutService.refresh();
        });
        return checkbox;
    }

    private Button getSendButton() {
        final Button sendButton = new Button(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.contact.form.send.button"));
        sendButton.addClickListener(_ -> {
            final boolean isSendingFeedbackToUserOptionChecked = getReplyUserEmailOptionCheckbox().getValue();
            if(!isSendingFeedbackToUserOptionChecked
                    || (isSendingFeedbackToUserOptionChecked
                    && Objects.nonNull(mainViewContactFormLayoutService.getReplyUserEmail())
                    && !getReplyUserEmailField().getDefaultValidator().apply(mainViewContactFormLayoutService.getReplyUserEmail(), null).isError())) {
                mainViewContactFormLayoutService.sendShopRequest();
            }
        });
        return sendButton;
    }

    @Override
    public VerticalLayout build() {
        return getLayout();
    }
}
