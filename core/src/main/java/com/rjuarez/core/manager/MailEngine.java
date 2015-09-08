package com.rjuarez.core.manager;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 * 
 * @author Matt Raible
 */
@Service("mailEngine")
public class MailEngine {
    private final Log log = LogFactory.getLog(MailEngine.class);
    @Autowired
    private MailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;
    private String defaultFrom;

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setFrom(final String from) {
        this.defaultFrom = from;
    }

    /**
     * Send a simple message based on a Velocity template.
     * 
     * @param msg
     *            the message to populate
     * @param templateName
     *            the Velocity template to use (relative to classpath)
     * @param model
     *            a map containing key/value pairs
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void sendMessage(final SimpleMailMessage msg, final String templateName, final Map model) {
        String result = null;

        try {
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", model);
        } catch (final VelocityException e) {
            log.error(e.getMessage(), e);
        }

        msg.setText(result);
        send(msg);
    }

    /**
     * Convenience method for sending messages with attachments.
     * 
     * @param recipients
     *            array of e-mail addresses
     * @param sender
     *            e-mail address of sender
     * @param resource
     *            attachment from classpath
     * @param bodyText
     *            text in e-mail
     * @param subject
     *            subject of e-mail
     * @param attachmentName
     *            name for attachment
     * @throws MessagingException
     *             thrown when can't communicate with SMTP server
     */
    public void sendMessage(final String[] recipients, final String sender, final ClassPathResource resource, final String bodyText,
            final String subject, final String attachmentName) throws MessagingException {
        final MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        final MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipients);

        // use the default sending if no sender specified
        if (sender == null) {
            helper.setFrom(defaultFrom);
        } else {
            helper.setFrom(sender);
        }

        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) mailSender).send(message);
    }

    /**
     * Send a simple message with pre-populated values.
     * 
     * @param msg
     *            the message to send
     * @throws org.springframework.mail.MailException
     *             when SMTP server is down
     */
    public void send(final SimpleMailMessage msg) throws MailException {
        try {
            mailSender.send(msg);
        } catch (final MailException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void sendMail(final String to, final String subject, final String body) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
