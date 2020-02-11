package com.hnsfdx.deeplearningtextrecognition.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "Picture对象", description = "Picture对象的数据")
public class Picture {
    //主键ID
    @ApiModelProperty(name = "id", value = "图片在数据库的主键id（更新时不需要填充此字段）", example = "1")
    private Integer id;
    //登录账户名
    @ApiModelProperty(name = "loginName", value = "用户的登录名（更新时不需要填充此字段）", example = "123456@qq.com")
    private String loginName;
    //图片名
    @ApiModelProperty(name = "pictureName", value = "图片的名字", example = "helloworld")
    private String pictureName;
    //图片url路径
    @ApiModelProperty(name = "pictureUrl", value = "访问图片时的url", example = "www.baidu.com")
    private String pictureUrl;
    //图片内容
    @ApiModelProperty(name = "pictureData", value = "图片识别后的内容", example = "高等数学第一版")
    private String pictureData;
    //图片的类型
    @ApiModelProperty(name = "pictureType", value = "上传的图片的类型，用于不同类型的识别，例如0是自然场景图片，1是pdf图片等", example = "0")
    private Integer pictureType;
    //上传时间
    @ApiModelProperty(name = "uploadTime", value = "上传图片的server端时间（更新时不需要填充此字段）", example = "2012-12-12 12:12:12")
    private Date uploadTime;

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

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureData() {
        return pictureData;
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }

    public Integer getPictureType() {
        return pictureType;
    }

    public void setPictureType(Integer pictureType) {
        this.pictureType = pictureType;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", pictureName='" + pictureName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", pictureData='" + pictureData + '\'' +
                ", pictureType=" + pictureType +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
