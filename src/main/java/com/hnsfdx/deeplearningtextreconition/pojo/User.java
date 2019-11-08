package com.hnsfdx.deeplearningtextreconition.pojo;

import org.springframework.web.multipart.MultipartFile;

public class User {
    //主键ID
    private Integer id;
    //登录账户名
    private String loginName;
    //密码
    private String loginPwd;
    //用户昵称
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
