package com.hnsfdx.deeplearningtextrecognition.service;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    List<Picture> getAllByLoginName(String loginName);
    Picture getSingleById(Integer id);
    boolean savePicture(Picture pic);
    boolean updatePictureData(Picture picture);
    boolean deleteAllByLoginName(String loginName);
    boolean deleteSingleById(Integer id);
    boolean savePictureList(List<Picture> pictureList);
}
