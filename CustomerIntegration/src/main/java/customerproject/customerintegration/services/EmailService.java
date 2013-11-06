/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerintegration.services;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author timovaananen
 */
@Stateless
@LocalBean
public class EmailService implements Serializable {

    private final static String smtpHost = "smtp.gmail.com";
    private final static String smtpPort = "587";
    private final static String from = "tvaana73@gmail.com";
    private final static String pass = "_tav073.";
    
    private static String contentLink = "http://localhost:8080/CustomerWeb/rest/email?verification=";
    
   
    public boolean send(String to, String hash) {
       
      boolean success = true;

      // Get system properties
      Properties properties = new Properties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", smtpHost);
      properties.setProperty("mail.smtp.port", smtpPort);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");

      // Get the default Session object.
      Session session = Session.getInstance(properties, new Authenticator() {
          
          protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, pass); // username and the password
                }
          
      });

      try{
          
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Registration confirmation");

         // Send the actual HTML message, as big as you like
         message.setContent("Thank you for registering, for the final step you need to verify your registration"
                 + "by clicking the link below<br><a>"+contentLink.concat(hash)+"</a>",
                            "text/html");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
        success = false;
        
    }
      return success;
    }
    
}
