package com.hnsfdx.deeplearningtextrecognition.service;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;

import java.util.Map;

public interface UserService {
    //验证用户登录名是否有效
    boolean isValidLoginName(String loginName);

    //头像下载
    //发送验证码邮件
    void sendRegCodeMail(String emailAddress);

    //发送成功注册邮件
    void sendRegSucMail(String emailAddress, Map<String, String> userInfoMap);

    //验证短信验证码是否正确
    boolean verifyNum(String emailAddress, String verifyCode);

    //注册保存账户到MySQL
    boolean saveUser(User user);

    //更新用户状态
    boolean updateUser(User user);

    //删除用户
    boolean deleteUser(String loginName);

    //验证登陆用户是否存在于数据库
    User verifyUser(Map<String, String> loginMap);
}
