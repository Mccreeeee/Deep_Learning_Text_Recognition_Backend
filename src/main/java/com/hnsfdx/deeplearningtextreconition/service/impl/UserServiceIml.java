package com.hnsfdx.deeplearningtextreconition.service.impl;

import com.hnsfdx.deeplearningtextreconition.pojo.User;
import com.hnsfdx.deeplearningtextreconition.repository.UserRepository;
import com.hnsfdx.deeplearningtextreconition.service.UserService;
import com.hnsfdx.deeplearningtextreconition.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String senderAddress;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public boolean isValidLoginName(String loginName) {
        User user = userRepository.findByLoginName(loginName.toLowerCase());
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void sendRegCodeMail(String emailAddress) {
        String code;

        if (StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(emailAddress.toLowerCase()))) {
            // redis中没有就生成一个验证码
            // 生成6位随机数作为验证码
            code = (int) (Math.random() * 900000) + 100000 + "";
        } else {
            // redis中有就用redis中的作为验证码
            code = stringRedisTemplate.opsForValue().get(emailAddress.toLowerCase());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(formatter);

        String body = "<head>" +
                "    <base target=\"_blank\" />" +
                "    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>" +
                "    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>" +
                "    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>" +
                "    <style type=\"text/css\">" +
                "        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}" +
                "        td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}" +
                "        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}" +
                "        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}" +
                "        img{ border:0}" +
                "        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}" +
                "        blockquote{margin-right:0px}" +
                "    </style>" +
                "</head>" +
                "<body tabindex=\"0\" role=\"listitem\">" +
                "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">" +
                "    <tbody>" +
                "    <tr>" +
                "        <td>" +
                "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">" +
                "                    <tbody><tr><td width=\"210\"></td></tr></tbody>" +
                "                </table>" +
                "            </div>" +
                "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">" +
                "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">" +
                "                        您正在进行<span style=\"color: red\">注册账号</span>操作，请在5分钟以内于验证码输入框中输入：<span style=\"color:#f60;font-size: 24px\">" + code + "</span>，以完成操作。" +
                "                    </strong>" +
                "                </div>" +
                "                <div style=\"margin-bottom:30px;\">" +
                "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">" +
                "                        <p style=\"color:#747474;\">" +
                "                            注意：此邮件用于注册账号。如非本人操作，请忽视此邮件！" +
                "                            <br>（同时请注意工作人员不会向你索取此验证码，请勿泄漏！)" +
                "                        </p>" +
                "                    </small>" +
                "                </div>" +
                "            </div>" +
                "           </p>如果您有什么疑问可以联系管理员，Email: " + senderAddress + "</p>" +
                "           <p align=\"right\">" + nowTime + "</p>" +
                "            <div style=\"width:700px;margin:0 auto;\">" +
                "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">" +
                "                    <p>此为系统邮件，请勿回复<br>" +
                "                        请保管好您的邮箱，避免账号被他人盗用" +
                "                    </p>" +
                "                    <p>华南师范大学图识TooSimple科技团队</p>" +
                "                </div>" +
                "            </div>" +
                "        </td>" +
                "    </tr>" +
                "    </tbody>" +
                "</table>" +
                "</body>";
        stringRedisTemplate.opsForValue().set(emailAddress.toLowerCase(), code, 5, TimeUnit.MINUTES);
        emailUtil.sendEmail(senderAddress, emailAddress.toLowerCase(), "图识TooSimple App 验证码通知", body);
    }

    @Override
    public void sendRegSucMail(String emailAddress, Map<String, String> userInfoMap) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(formatter);
        String body = "<div style=\"background-color:#ECECEC; padding: 35px;\">" +
                "    <table cellpadding=\"0\" align=\"center\"" +
                "           style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">" +
                "        <tbody>" +
                "        <tr>" +
                "            <th valign=\"middle\"" +
                "                style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">" +
                "                <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">注册成功!</font>" +
                "            </th>" +
                "        </tr>" +
                "        <tr>" +
                "            <td>" +
                "                <div style=\"padding:25px 35px 40px; background-color:#fff;\">" +
                "                    <h2 style=\"margin: 5px 0px; \">" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">" +
                "                                亲爱的 " + userInfoMap.get("userName") + "</font>" +
                "                        </font>" +
                "                    </h2>" +
                "                    <p>欢迎您成为图识TooSimple App的一员！下面是您的账号信息<br>" +
                "                        您的账号：<b>" + userInfoMap.get("loginName").toLowerCase() + "</b><br>" +
                "                        您的密码：<b>" + userInfoMap.get("loginPwd") + "</b><br>" +
                "                        您的昵称：<b>" + userInfoMap.get("userName") + "</b><br>" +
                "                        您注册时的日期：<b>" + nowTime + "</b><br>" +
                "                        如果您有什么疑问可以联系管理员，Email: " + senderAddress + "</p>" +
                "                    <p align=\"right\">" + nowTime + "</p>" +
                "                    <div style=\"width:700px;margin:0 auto;\">" +
                "                        <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">" +
                "                            <p>此为系统邮件，请勿回复<br>" +
                "                                请保管好您的邮箱，避免账号被他人盗用" +
                "                            </p>" +
                "                            <p>©图识TooSimple</p>" +
                "                        </div>" +
                "                    </div>" +
                "                </div>" +
                "            </td>" +
                "        </tr>" +
                "        </tbody>" +
                "    </table>" +
                "</div>";
        emailUtil.sendEmail(senderAddress, emailAddress.toLowerCase(), "图识TooSimple App 注册成功通知", body);
    }

    @Override
    public boolean verifyNum(String emailAddress, String verifyCode) {
        boolean flag = true;
        String code = stringRedisTemplate.opsForValue().get(emailAddress.toLowerCase());
        if (!verifyCode.equals(code))
            flag = false;
        return flag;
    }

    @Override
    public boolean saveUser(User user) {
        return userRepository.saveUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(String loginName) {
        return userRepository.deleteUser(loginName.toLowerCase());
    }
}
