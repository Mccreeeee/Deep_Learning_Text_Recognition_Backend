package com.hnsfdx.deeplearningtextrecognition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "User对象", description = "User对象的数据")
public class User {
    //主键ID
    @ApiModelProperty(name = "id", value = "用户在数据库的主键id（更新时不需要填充此字段）", example = "1")
    private Integer id;
    //登录账户名
    @ApiModelProperty(name = "loginName", value = "用户的登录名（更新时不需要填充此字段）", example = "123456@qq.com")
    private String loginName;
    //登陆密码
    @ApiModelProperty(name = "loginPwd", value = "用户的密码", example = "123456(用MD5)")
    private String loginPwd;
    //用户昵称
    @ApiModelProperty(name = "userName", value = "用户的昵称", example = "test")
    private String userName;
    //用户头像地址
    @ApiModelProperty(name = "userAvatar", value = "用户的头像地址", example = "www.baidu.com")
    private String userAvatar;
    //用户注册时间
    @ApiModelProperty(name = "registerTime", value = "用户注册的server端时间（更新时不需要填充此字段）", example = "2012-12-12 12:12:12")
    private Date registerTime;
    //用户登陆时间
    @ApiModelProperty(name = "loginTime", value = "用户登录的server端时间（更新时不需要填充此字段）", example = "2012-12-12 12:12:12")
    private Date loginTime;

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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
