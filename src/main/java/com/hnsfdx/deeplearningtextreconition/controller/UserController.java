package com.hnsfdx.deeplearningtextreconition.controller;

import com.hnsfdx.deeplearningtextreconition.pojo.User;
import com.hnsfdx.deeplearningtextreconition.service.UserService;
import com.hnsfdx.deeplearningtextreconition.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/genVerifyNum")
    public Map<String, Object> genVerifyNum(@RequestParam String loginName) {
        userService.sendRegCodeMail(loginName);
        return ResponseUtil.sucMsg();
    }

    //  传入的infoMap中有loginName，loginPwd，userName和code四个key-value对
    @PostMapping(value = "/reg")
    public Map<String, Object> register(@RequestBody Map<String, String> infoMap) {
        Map<String, Object> returnMap;
        boolean flag = userService.verifyNum(infoMap.get("loginName").toLowerCase(), infoMap.get("code"));
        if (flag) {
            User user = new User();
            user.setLoginName(infoMap.get("loginName").toLowerCase());
            user.setLoginPwd(infoMap.get("loginPwd"));
            user.setUserName(infoMap.get("userName"));
            Date date = new Date();
            user.setLoginTime(date);
            user.setRegisterTime(date);
            userService.saveUser(user);
            returnMap = ResponseUtil.sucMsg();
            userService.sendRegSucMail(infoMap.get("loginName"), infoMap);
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "验证码校验有误，请重新输入！");
        }
        return returnMap;
    }

    @PostMapping(value = "verifyLoginName")
    public Map<String, Object> verifyLoginName(@RequestParam String loginName) {
        Map<String, Object> returnMap;
        boolean flag = userService.isValidLoginName(loginName);
        if (flag) {
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "已存在的用户登陆名，请重新输入！");
        }
        return returnMap;
    }

    @PostMapping(value = "deleteUser")
    public Map<String, Object> deleteUser(@RequestParam String loginName) {
        Map<String, Object> returnMap;
        boolean flag = userService.deleteUser(loginName);
        if (flag) {
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "此用户已注销或不存在此用户，请重新输入！");
        }
        return returnMap;
    }
}
