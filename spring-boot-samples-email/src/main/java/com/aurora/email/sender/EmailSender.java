package com.aurora.email.sender;

import com.aurora.email.manager.EmailManager;
import com.aurora.email.manager.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 邮件发送业务
 * @author xzbcode
 */
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private FileManager fileManager;


    @Value("${spring.mail.username}")
    private String formEmailAccount;

    /**
     * 发送简单邮件
     */
    public void sendSimpleEmail() throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 邮件主题
        simpleMailMessage.setSubject("这是测试邮件的主题");
        // 发送人
        simpleMailMessage.setFrom(formEmailAccount);
        // 接收人
        simpleMailMessage.setTo("xxxxxxxxxx@qq.com");
        // 抄送人/隐秘抄送人
        //simpleMailMessage.setCc("xxxxxxxxxx@qq.com");
        //simpleMailMessage.setBcc("xxxxxxxxxx@qq.com");
        // 邮件内容
        simpleMailMessage.setText("这是测试邮件的内容");
        // 发送时间
        simpleMailMessage.setSentDate(new Date());
        // 发送邮件
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送带附件的邮件
     */
    public void sendAttachmentEmail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 设置携带附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是测试携带附件邮件的主题");
        helper.setFrom(formEmailAccount);
        helper.setTo("xxxxxxxxxx@qq.com");
        helper.setText("这是测试携带附件邮件的内容");
        helper.setSentDate(new Date());
        // 附件参数
        helper.addAttachment("email.txt", fileManager.getAttachmentFile());
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送带图片邮件
     */
    public void sendImageEmail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 设置携带附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是测试带图片邮件的主题");
        helper.setFrom(formEmailAccount);
        helper.setTo("xxxxxxxxxx@qq.com");
        // 使用Html 和占位符
        helper.setText(
                "<p>这是测试带图片邮件的内容，图片:</p>" +
                "<img src='cid:p1'/>",
                true);
        helper.setSentDate(new Date());
        // 图片参数
        helper.addInline("p1", fileManager.getImageFile());
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送模板邮件
     * 这是使用的是Thymeleaf模板
     */
    public void sendTemplateEmail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 设置携带附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是测试带图片邮件的主题");
        helper.setFrom(formEmailAccount);
        helper.setTo("xxxxxxxxxx@qq.com");
        // 使用thymeleaf模板字符串
        String emailText = emailManager.getEmailText();
        helper.setText(emailText,true);
        helper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
    }

}
