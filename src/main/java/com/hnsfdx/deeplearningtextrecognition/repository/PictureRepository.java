package com.hnsfdx.deeplearningtextrecognition.repository;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureRepository {
    List<Picture> findAllByLoginName(String loginName);

    boolean savePicture(Picture pic);

    boolean updatePicture(Picture picture);

    boolean deleteAllPictures(String loginName);

    boolean deleteSinglePicture(Integer id);

    Picture findSingleById(Integer id);

    boolean savePictureList(List<Picture> pictureList);
}
