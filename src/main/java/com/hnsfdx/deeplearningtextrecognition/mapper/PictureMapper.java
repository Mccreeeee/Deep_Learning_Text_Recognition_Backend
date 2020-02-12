package com.hnsfdx.deeplearningtextrecognition.mapper;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PictureMapper {
    List<Picture> findAllByLoginName(@Param(value = "loginName") String loginName);

    boolean savePicture(Picture pic);

    boolean updatePicture(@Param(value = "picture") Picture picture);

    boolean deleteAllPictures(@Param(value = "loginName") String loginName);

    boolean deleteSinglePicture(@Param(value = "id") Integer id);

    Picture findSingleById(@Param(value = "id") Integer id);

    boolean savePictureList(@Param(value = "pictureList") List<Picture> pictureList);

    List<String> findAllImgUrl();
}
