package com.cgi.demoproject.service;

import com.cgi.demoproject.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@PropertySource("classpath:application.properties")
@Service
public class EmailSender {
    @Value("${from}")
    private String from;
    @Value("${cc}")
    private String cc;
    @Value("${subject}")
    private String subject;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailTemplate(String to,String message) throws MessagingException {

        Email email=new Email();
        email.setFrom(from);
        email.setCc(cc);
        email.setSubject(subject);
        System.out.println(email.getFrom());
        System.out.println(email.getSubject());
        System.out.println(email.getCc());
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom(email.getFrom());
        helper.setTo(to);
        helper.setSubject(email.getSubject());
        helper.setCc(email.getCc());
        helper.setText(message,true);
        javaMailSender.send(msg);
    }
}
