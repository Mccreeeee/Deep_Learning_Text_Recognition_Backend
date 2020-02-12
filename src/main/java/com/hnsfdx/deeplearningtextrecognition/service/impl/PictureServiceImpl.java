package com.hnsfdx.deeplearningtextrecognition.service.impl;

import com.hnsfdx.deeplearningtextrecognition.pojo.Picture;
import com.hnsfdx.deeplearningtextrecognition.repository.PictureRepository;
import com.hnsfdx.deeplearningtextrecognition.service.PictureService;
import com.hnsfdx.deeplearningtextrecognition.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public List<Picture> getAllByLoginName(String loginName) {
        return pictureRepository.findAllByLoginName(loginName);
    }

    @Override
    public Picture getSingleById(Integer id) {
        return pictureRepository.findSingleById(id);
    }

    @Override
    public boolean savePicture(Picture pic) {
        return pictureRepository.savePicture(pic);
    }

    @Override
    public boolean updatePictureData(Picture picture) {
        return pictureRepository.updatePicture(picture);
    }

    @Override
    public boolean deleteAllByLoginName(String loginName) {
        return pictureRepository.deleteAllPictures(loginName);
    }

    @Override
    public boolean deleteSingleById(Integer id) {
        return pictureRepository.deleteSinglePicture(id);
    }

    @Override
    public boolean savePictureList(List<Picture> pictureList) {
        return pictureRepository.savePictureList(pictureList);
    }

    @Override
    public List<String> getAllImgUrl() {
        return pictureRepository.findAllImgUrl();
    }
}
