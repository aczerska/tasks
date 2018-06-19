package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail, EmailType emailType) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail,emailType));
            if (emailType == EmailType.TRELLO_CARD) {
                LOGGER.info("Email for newly created Trello card has been sent");
            } else if (emailType == EmailType.SCHEDULED) {
                LOGGER.info("Scheduled email with current tasks quantity has been sent");
            }
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCC()).ifPresent(mailMessage::setCc);
        return mailMessage;
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail, EmailType emailType) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if (emailType == EmailType.TRELLO_CARD) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()),true);
            } else if (emailType == EmailType.SCHEDULED) {
                messageHelper.setText(mailCreatorService.buildTasksQuantiyEmail(mail.getMessage()),true);
            }

        };
    }
}
