package com.galen.amazon.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @Author: Galen
 * @Date: 2019/5/23-15:42
 * @Description: 发邮件
 **/

@Component
public class SendMailComponent {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")  //发送人的邮箱  比如155156641XX@163.com
    private String from;

    /**
     * @Author: Galen
     * @Description: 发送简单文本形式的邮件，异步发送
     * @Date: 2019/4/11-16:32
     * @Param: [to, subject, text]
     * @return: void
     **/
    @Async
    public void sendTextMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发送人的邮箱
        message.setSubject(subject); //标题
        message.setTo(to); //发给谁  对方邮箱
        message.setText(text); //内容
        javaMailSender.send(message); //发送
    }

    /**
     * @Author: Galen
     * @Description: 发送html格式的邮件，异步发送
     * @Date: 2019/4/18-10:49
     * @Param: [to, subject, content]
     * @return: void
     **/
    @Async
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(content, true);
            javaMailSender.send(message);
            System.out.println("html格式邮件发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("html格式邮件发送失败");
        }
    }

}
