package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RestController
@RequestMapping("mail")
public class SendMailTLS {
    //testing
    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity<StudentDataModel> sendMail() {
        final String username = "karl24fernando@gmail.com";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("karl24fernando@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("sendItToSomeone@gmail.com")); //send it to who???
            message.setSubject("Testing Subject");
            message.setText("Dear someone,"
                    + "\n\n Your new password is: efewufewuigwbdw");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
