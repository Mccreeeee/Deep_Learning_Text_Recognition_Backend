package com.hnsfdx.deeplearningtextrecognition.controller;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import com.hnsfdx.deeplearningtextrecognition.pojo.User;
import com.hnsfdx.deeplearningtextrecognition.schedule.ScheduleUtil;
import com.hnsfdx.deeplearningtextrecognition.service.PictureService;
import com.hnsfdx.deeplearningtextrecognition.service.UserService;
import com.hnsfdx.deeplearningtextrecognition.swagger.ApiJsonObject;
import com.hnsfdx.deeplearningtextrecognition.swagger.ApiJsonProperty;
import com.hnsfdx.deeplearningtextrecognition.swagger.WebDataResponse;
import com.hnsfdx.deeplearningtextrecognition.swagger.WebResponse;
import com.hnsfdx.deeplearningtextrecognition.util.FileUtil;
import com.hnsfdx.deeplearningtextrecognition.util.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "用户服务控制器", tags = "User")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
@Autowired
private PictureService pictureService;
@Autowired
private ScheduleUtil scheduleUtil;
    @PostMapping(value = "/genVerifyNum")
    @ApiOperation(value = "生成验证码", notes = "用于生成验证码并发送相应验证码邮件的api", tags = "User", httpMethod = "POST")
    @ApiImplicitParam(name = "loginName", value = "注册时使用的登录名", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    public Map<String, Object> genVerifyNum(@RequestParam String loginName) {
        userService.sendRegCodeMail(loginName);
        return ResponseUtil.sucMsg();
    }

    //传入的infoMap中有loginName，loginPwd，userName和code四个key-value对
    //因为注册时间和登陆时间需要以server端为准，所以传入map
    @PostMapping(value = "/reg")
    @ApiOperation(value = "用户注册", notes = "用于用户注册的api，此处会验证输入的验证码", tags = "User", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    public Map<String, Object> register(@ApiJsonObject(name = "infoMap", value = {
            @ApiJsonProperty(key = "loginName", example = "123456@qq.com", description = "用户登录名"),
            @ApiJsonProperty(key = "loginPwd", example = "123456", description = "用户登录密码"),
            @ApiJsonProperty(key = "userName", example = "test", description = "用户昵称"),
            @ApiJsonProperty(key = "code", example = "866281", description = "收到的验证码")
    }) @RequestBody Map<String, String> infoMap) {
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
            user.setUserAvatar(FileUtil.BASE_URL + "defaultAvatar.jpg");
            userService.saveUser(user);
            returnMap = ResponseUtil.sucMsg();
            userService.sendRegSucMail(infoMap.get("loginName"), infoMap);
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "验证码校验有误，请重新输入！");
        }
        return returnMap;
    }

    @PostMapping(value = "/verifyLoginName")
    @ApiOperation(value = "用户名查重", notes = "用于验证用户名，进行查重的api", tags = "User", httpMethod = "POST")
    @ApiImplicitParam(name = "loginName", value = "注册时使用的登录名", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
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

    @PostMapping(value = "/deleteUser")
    @ApiOperation(value = "删除用户", notes = "用于删除指定用户名的用户的api", tags = "User", httpMethod = "POST")
    @ApiImplicitParam(name = "loginName", value = "注册时使用的登录名", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
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

    @PostMapping(value = "/updateUser")
    @ApiOperation(value = "更新用户", notes = "用于更新用户信息的api", tags = "User", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    public Map<String, Object> updateUser(@RequestBody User updatedUser) {
        Map<String, Object> returnMap;
        boolean flag = userService.updateUser(updatedUser);
        if (flag) {
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "更新用户数据失败，请稍后再试！");
        }
        return returnMap;
    }

    // loginMap中存放登陆所需的信息，loginName以及loginPwd
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "用于检查用户信息是否存在，用户登录用的api", tags = "User", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebDataResponse.class),
    })
    public Map<String, Object> login(@ApiJsonObject(name = "loginMap", value = {
            @ApiJsonProperty(key = "loginName", example = "123456@qq.com", description = "用户登录名"),
            @ApiJsonProperty(key = "loginPwd", example = "123456", description = "用户登录密码")
    }) @RequestBody Map<String, String> loginMap) {
        Map<String, Object> returnMap;
        String lowerString = loginMap.get("loginName").toLowerCase();
        loginMap.put("loginName", lowerString);
        User user = userService.verifyUser(loginMap);
        if (user != null) {
            Date date = new Date();
            returnMap = ResponseUtil.sucMsg();
            user.setLoginTime(date);
            returnMap.put("data", user);
            // 这个User用来更新数据库的loginTime值
            User newLoginTimeUser = new User();
            newLoginTimeUser.setLoginName(lowerString);
            newLoginTimeUser.setLoginTime(date);
            userService.updateUser(newLoginTimeUser);
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "账户或密码输入错误，请重新尝试！");
        }
        return returnMap;
    }

    @ApiOperation(value = "上传头像", notes = "", tags = "User", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的头像图片", required = true, dataType = "MutipartFile"),
            @ApiImplicitParam(name = "loginName", value = "注册时使用的登录名", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebDataResponse.class),
    })
    @PostMapping(value = "/uploadAvatar")
    public Map<String, Object> uploadAvatarAndSave(@RequestParam(value = "file") MultipartFile file,
                                                   @RequestParam(value = "loginName") String loginName) {
        Map<String, Object> returnMap;
        String relativePath = "User_" + loginName.replaceAll("@|\\.", "_") + "_Avatar";
        String result = FileUtil.uploadToServer(relativePath, file);
        if (result != null) {
            returnMap = ResponseUtil.sucMsg();
            returnMap.put("data", result);
            //更新用户头像地址
            User newAvatarUser = new User();
            newAvatarUser.setLoginName(loginName);
            newAvatarUser.setUserAvatar(result);
            userService.updateUser(newAvatarUser);
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "上传头像失败，请重新尝试！");
        }
        return returnMap;
    }


    @GetMapping(value = "/testdel")
    public void test(@RequestParam Integer id) {
//        String relativePath = "User_" + loginName.replaceAll("@|\\.", "_") + "_Avatar";
//        FileUtil.deleteSingleInServer(relativePath, "20kb小照.jpg");
        scheduleUtil.scheduledClearImg();

    }
}
