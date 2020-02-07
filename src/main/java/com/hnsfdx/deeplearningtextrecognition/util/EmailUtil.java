package com.hnsfdx.deeplearningtextrecognition.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component

public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    @Autowired
    private JavaMailSender javaMailSender;

    // 异步发送邮件，交由线程池处理
    @Async("taskExecutor")
    public void sendEmail(String senderAddress, String emailAddress, String subject, String body) {
        //发送一封验证码邮件
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(senderAddress);
            mimeMessageHelper.setTo(emailAddress);
            mimeMessageHelper.setSubject(subject);
            StringBuilder sb = new StringBuilder();
            sb.append(body);
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
        }
    }
}
