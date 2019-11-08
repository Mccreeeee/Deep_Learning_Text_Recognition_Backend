package com.hnsfdx.deeplearningtextreconition.service;

import com.github.qcloudsms.httpclient.HTTPException;
import com.hnsfdx.deeplearningtextreconition.pojo.User;

import java.io.IOException;

public interface UserRegisterService {
    //发送短信验证码
    boolean sendMsg(String phoneNum);
    //验证短信验证码是否正确
    boolean verifyNum(String phoneNum, String verifyCode);
    //注册保存账户到MySQL
    boolean saveUser(User user);

}
