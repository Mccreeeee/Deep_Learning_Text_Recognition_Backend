package com.hnsfdx.deeplearningtextreconition.controller;

import com.hnsfdx.deeplearningtextreconition.pojo.User;
import com.hnsfdx.deeplearningtextreconition.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping(value = "/genVerifyNum")
    public boolean genVerifyNum(@RequestParam String loginName) {
        return userRegisterService.sendMsg(loginName);
    }

    @PostMapping(value = "/reg")
    public String register(@RequestBody User user, @RequestParam String verifyNum) {
        boolean flag = userRegisterService.verifyNum(user.getLoginName(), verifyNum);
        return "";
    }
}
