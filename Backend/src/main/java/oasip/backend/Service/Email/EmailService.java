package oasip.backend.Service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(String toEmail , String msgBody , String subject);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
