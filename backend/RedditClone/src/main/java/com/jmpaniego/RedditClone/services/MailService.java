package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.models.NotificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private MailContentBuilder mailContentBuilder;

  public void sendMail(NotificationEmail notificationEmail){
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom("springredditclone@email.com");
      messageHelper.setTo(notificationEmail.getRecipient());
      messageHelper.setSubject(notificationEmail.getSubject());
      messageHelper.setText(
          mailContentBuilder.build(notificationEmail.getBody())
      );
    };
    try{
      javaMailSender.send(messagePreparator);
    }catch (MailException me){
      throw new SpringRedditException("Exception ocurred when sending mail to "+notificationEmail.getRecipient());
    }
  }
}
