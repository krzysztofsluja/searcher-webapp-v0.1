package org.sluja.searcher.webapp.service.communication.email;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.config.email.EmailConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactEmailSender implements IEmailSender{

    private final JavaMailSender javaMailSender;
    @Override
    public void send(final String text) {
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply-searcher@searcher.com");
        mail.setTo(EmailConfiguration.RECEIVER_EMAIL);
        mail.setText(text);
        javaMailSender.send(mail);
    }
}
