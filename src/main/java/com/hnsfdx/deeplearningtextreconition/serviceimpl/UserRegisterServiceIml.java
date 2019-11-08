package com.hnsfdx.deeplearningtextreconition.serviceimpl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;
import com.hnsfdx.deeplearningtextreconition.pojo.User;
import com.hnsfdx.deeplearningtextreconition.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegisterServiceIml implements UserRegisterService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendMsg(String phoneNum) {
        boolean flag = true;
        //生成4位随机数作为验证码
        String code = (int) (Math.random() * 9000) + 1000 + "";
        //得到appID和appKey用于发短信
        int appId = Integer.parseInt(stringRedisTemplate.opsForValue().get("appId"));
        String appKey = stringRedisTemplate.opsForValue().get("appKey");
        //向对应手机号发送短信
        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        //短信内容中的{}参数
        String[] params = {code, "1"};
        //短信模板ID
        int templateId = 308166;
        //单发短信
        try {
            //发送模板ID短信，发送失败则进入catch块
            ssender.sendWithParam("86", phoneNum,
                    templateId, params, "", "", "");
            System.err.println("code是：" + code);
//            您的验证码为：{1}，请于{2}分钟内填写。如非本人操作，请忽略此短信。
            //将发送的手机号作为key，验证码作为value保存到redis中，有效时限1分钟
            stringRedisTemplate.opsForValue().set(phoneNum, code, 1, TimeUnit.MINUTES);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean verifyNum(String phoneNum, String verifyCode) {
        boolean flag = true;
        String code = stringRedisTemplate.opsForValue().get(phoneNum);
        if (!code.equals(verifyCode))
            flag = false;
        return flag;
    }

    @Override
    public boolean saveUser(User user) {
        boolean flag = true;
        return flag;
    }
}
