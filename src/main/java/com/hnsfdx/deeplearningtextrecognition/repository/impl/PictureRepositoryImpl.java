package com.hnsfdx.deeplearningtextrecognition.repository.impl;

import com.hnsfdx.deeplearningtextrecognition.mapper.PictureMapper;
import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import com.hnsfdx.deeplearningtextrecognition.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureRepositoryImpl implements PictureRepository {

    @Autowired
    private PictureMapper pictureMapper;
    @Override
    public List<Picture> findAllByLoginName(String loginName) {
        return pictureMapper.findAllByLoginName(loginName);
    }

    @Override
    public boolean savePicture(Picture pic) {
        return pictureMapper.savePicture(pic);
    }

    @Override
    public boolean updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }

    @Override
    public boolean deleteAllPictures(String loginName) {
        return pictureMapper.deleteAllPictures(loginName);
    }

    @Override
    public boolean deleteSinglePicture(Integer id) {
        return pictureMapper.deleteSinglePicture(id);
    }

    @Override
    public Picture findSingleById(Integer id) {
        return pictureMapper.findSingleById(id);
    }

    @Override
    public boolean savePictureList(List<Picture> pictureList) {
        return pictureMapper.savePictureList(pictureList);
    }

    @Override
    public List<String> findAllImgUrl() {
        return pictureMapper.findAllImgUrl();
    }
}
