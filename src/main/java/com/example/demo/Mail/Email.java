package com.example.demo.Mail;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email
{



    public void sendEmail(String text,String to) throws IOException, MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtps.user","pasaharpsuk@gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("pasaharpsuk@gmail.com"));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(("pasaharpsuk@gmail.com")));
        message.setSubject("Код подтвержения: ");
        message.setText(text);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com","pasaharpsuk@gmail.com","Boda1006");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

}
