package kr.co.iei.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;
	
	public void sendMail(String emailTitle, String receiver, String emailContent) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			helper.setSentDate(new Date());
			helper.setFrom(new InternetAddress("Admin@insidemirror.com", "인사이드미러 도우미"));
			helper.setTo(receiver);
			helper.setSubject(emailTitle);
			helper.setText(emailContent, true);
			sender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
