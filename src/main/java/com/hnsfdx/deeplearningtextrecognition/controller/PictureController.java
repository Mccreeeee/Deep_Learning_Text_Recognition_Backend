package com.hnsfdx.deeplearningtextrecognition.controller;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import com.hnsfdx.deeplearningtextrecognition.service.PictureService;
import com.hnsfdx.deeplearningtextrecognition.swagger.WebDataResponse;
import com.hnsfdx.deeplearningtextrecognition.swagger.WebResponse;
import com.hnsfdx.deeplearningtextrecognition.util.FileUtil;
import com.hnsfdx.deeplearningtextrecognition.util.RecogUtil;
import com.hnsfdx.deeplearningtextrecognition.util.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "图片服务控制器", tags = "Picture")
@RestController
@RequestMapping(value = "/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "获得所有图片", notes = "用于获得对应loginName的用户的所有照片信息", httpMethod = "GET")
    @ApiImplicitParam(name = "loginName", value = "需要获得所有照片的用户名", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebDataResponse.class),
    })
    @GetMapping(value = "/getAll")
    public Map<String, Object> getAllPictures(@RequestParam(value = "loginName") String loginName) {
        List<Picture> pictures = pictureService.getAllByLoginName(loginName);
        Map<String, Object> returnMap = ResponseUtil.sucMsg();
        returnMap.put("data", pictures);
        return returnMap;
    }

    @ApiOperation(value = "上传保存图片并识别，通识版", notes = "用于上传图片并保存信息至数据库", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户的登录名", dataType = "String"),
            @ApiImplicitParam(name = "pictureType", value = "上传的图片的类型，用于不同类型的识别，例如0是自然场景图片，1是pdf图片等", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "上传图片的id（如果为null，则说明是第一次上传）", dataType = "Integer"),
            @ApiImplicitParam(name = "file", value = "上传的头像图片", required = true, dataType = "MutipartFile")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebDataResponse.class),
    })
    @PostMapping(value = "/uploadImg")
    public Map<String, Object> uploadAndSave(@RequestParam(value = "loginName", required = false) String loginName,
                                             @RequestParam(value = "pictureType") Integer pictureType,
                                             @RequestParam(value = "id", required = false) Integer id,
                                             @RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> returnMap;
        boolean flag = false;
        // 判断是否存储过，存储过的话只需要重新识别内容，并更新数据库即可
        // 此处考虑相对路径
        String midFix;
        if (!StringUtils.isEmpty(loginName)) {
            midFix = loginName.replaceAll("@|\\.", "_");
        } else {
            midFix = "UnLoginUser";
        }
        String relativePath = "User_" + midFix + "_Img";
        String fileName = file.getOriginalFilename();

        // 图片在本地的位置，供调用python用
        String localRelativeLoc = FileUtil.BASE_DIR + relativePath + "/" + fileName;
        String localLocation = FileUtil.getLocalLocation(localRelativeLoc);

        Picture picture = pictureService.getSingleById(id);
        String resultData = "";
        if (picture == null) {
            picture = new Picture();
            String pictureUrl = FileUtil.uploadToServer(relativePath, file);
            // 此处调用python进行识别并setPictureData
            resultData = RecogUtil.getResultFromPyScript(localLocation);
            // 需要判断是否有保存过，没有才保存至数据库，并返回
            picture.setPictureData(resultData);
            picture.setPictureName(fileName);
            picture.setLoginName(loginName);
            picture.setPictureType(pictureType);
            picture.setPictureUrl(pictureUrl);
            Date date = new Date();
            picture.setUploadTime(date);
            // 登陆了，才保存记录
            if (!StringUtils.isEmpty(loginName)) {
                flag = pictureService.savePicture(picture);
            }
        } else {
            // 此处调用python进行识别并setPictureData
            resultData = RecogUtil.getResultFromPyScript(localLocation);
            picture.setPictureData(resultData);
            // 登录了，才更新记录
            if (!StringUtils.isEmpty(loginName)) {
                flag = pictureService.updatePictureData(picture);
            }
        }
        if (StringUtils.isEmpty(loginName) || flag) {
            returnMap = ResponseUtil.sucMsg();
            returnMap.put("data", picture);
        } else {
            // 选择定时删除策略
//            FileUtil.deleteSingleInServer(relativePath, fileName);
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "图片上传或识别失败，请重新尝试！");
        }
        return returnMap;
    }

    @ApiOperation(value = "同步保存图片，OCR版", notes = "用于上传图片并保存信息至数据库", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户的登录名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pictureType", value = "同步的图片的类型，用于不同类型的识别，例如0是自然场景图片，1是pdf图片等", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pictureData", value = "同步图片的识别内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "file", value = "需要同步的图片", required = true, dataType = "MutipartFile")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success/fail，fail时的data表示同步保存失败的图片数据", response = WebDataResponse.class),
    })
    @PostMapping(value = "/synOneOCR")
    public Map<String, Object> synOneOCR(@RequestParam(value = "loginName") String loginName,
                                         @RequestParam(value = "pictureType") Integer pictureType,
                                         @RequestParam(value = "pictureData") String pictureData,
                                         @RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> returnMap;
        // 判断是否存储过，存储过的话只需要重新识别内容，并更新数据库即可
        // 此处考虑相对路径
        String relativePath = "User_" + loginName.replaceAll("@|\\.", "_") + "_Img";
        String fileName = file.getOriginalFilename();
        Picture picture = new Picture();
        String pictureUrl = FileUtil.uploadToServer(relativePath, file);
        picture.setPictureName(fileName);
        picture.setPictureData(pictureData);
        picture.setLoginName(loginName);
        picture.setPictureType(pictureType);
        picture.setPictureUrl(pictureUrl);
        Date date = new Date();
        picture.setUploadTime(date);
        boolean flag = pictureService.savePicture(picture);
        if (flag) {
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "图片同步失败，请稍后重新尝试！");
            returnMap.put("data", picture);
        }
        return returnMap;
    }

    @ApiOperation(value = "获得一张图片", notes = "用于获得对应图片id的一张图片信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "需要获得图片信息的图片id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebDataResponse.class),
    })
    @GetMapping(value = "/getOne")
    public Map<String, Object> getSinglePic(@RequestParam(value = "id") Integer id) {
        Picture picture = pictureService.getSingleById(id);
        Map<String, Object> returnMap = ResponseUtil.sucMsg();
        returnMap.put("data", picture);
        return returnMap;
    }

    @ApiOperation(value = "删掉一张图片", notes = "用于删除对应图片的图片信息", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "需要获得图片信息的图片id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    @PostMapping(value = "/delOne")
    public Map<String, Object> delSinglePic(@RequestParam(value = "id") Integer id) {
        Map<String, Object> returnMap;
        Picture picture = pictureService.getSingleById(id);
        if (picture == null) {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "此图片不存在，请重新尝试！");
            return returnMap;
        }
        boolean flag = pictureService.deleteSingleById(id);
        if (flag) {
            // 选择定时删除策略
//            FileUtil.deleteSingleInServer(relativePath, fileRealName);
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "删除图片失败，请重新尝试！");
        }
        return returnMap;
    }

    @ApiOperation(value = "上传同步所有图片", notes = "用于上传同步所有图片的图片信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pictureList", value = "所有本地客户端的图片信息数据", required = true, dataType = "List")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    @PostMapping(value = "/synAll")
    public Map<String, Object> synAllPic(@RequestBody List<Picture> pictureList) {
        Map<String, Object> returnMap;
        if (pictureList != null && pictureList.size() != 0) {
            String loginName = pictureList.get(0).getLoginName();
            boolean flag = pictureService.deleteAllByLoginName(loginName);
            if (!flag) {
                returnMap = ResponseUtil.failMsg();
                returnMap.put("reason", "云端删除所有图片数据失败，请重新尝试！");
                return returnMap;
            }
            flag = pictureService.savePictureList(pictureList);
            if (flag) {
                returnMap = ResponseUtil.sucMsg();
            } else {
                returnMap = ResponseUtil.failMsg();
                returnMap.put("reason", "云端保存所有图片数据失败，请重新尝试！");
            }
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "同步失败，请检查请求参数是否有误！");
        }
        return returnMap;
    }


    @ApiOperation(value = "更新一张图片", notes = "用于更新对应图片的图片信息", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = WebResponse.class),
    })
    @PostMapping(value = "/updatePic")
    public Map<String, Object> updateSinglePic(@RequestBody Picture picture) {
        Map<String, Object> returnMap;
        boolean flag = pictureService.updatePictureData(picture);
        if (flag) {
            returnMap = ResponseUtil.sucMsg();
        } else {
            returnMap = ResponseUtil.failMsg();
            returnMap.put("reason", "更新图片失败，请重新尝试！");
        }
        return returnMap;
    }

}
