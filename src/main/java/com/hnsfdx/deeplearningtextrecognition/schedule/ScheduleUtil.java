package com.hnsfdx.deeplearningtextrecognition.schedule;

import com.hnsfdx.deeplearningtextrecognition.service.PictureService;
import com.hnsfdx.deeplearningtextrecognition.service.UserService;
import com.hnsfdx.deeplearningtextrecognition.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleUtil.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    // 凌晨2点清理冗余图片
    @Scheduled(cron = "0 0 2 ? * *")
    public void scheduledClearImg() {
        List<String> userAvatarUrls = userService.getAllAvatarUrl();
        List<String> pictureImgUrls = pictureService.getAllImgUrl();
        String relativePath;
        String localFileName;
        Map<String, File> mappedFile;
        // 对每一个用户的头像进行处理
        delRedundantFiles(userAvatarUrls);
        // 接着对每一个用户上传的图片进行处理
        delRedundantFiles(pictureImgUrls);
    }

    private void delRedundantFiles(List<String> urls) {
        String relativePath;
        String localFileName;
        Map<String, File> mappedFile;
        for (String url : urls) {
            relativePath = FileUtil.getRelativePath(url);
            localFileName = FileUtil.getLocalFileName(url);
            mappedFile = mappingFile(relativePath);
            mappedFile.remove(localFileName);
            for(Map.Entry<String, File> entry : mappedFile.entrySet()) {
                FileUtil.deleteSingleInServer(relativePath, entry.getKey());
            }
        }
    }

    private Map<String, File> mappingFile(String relativePath) {
        Map<String, File> fileMap = null;
        File file = new File(FileUtil.BASE_DIR + relativePath);
        if (!file.isDirectory()) {
            logger.error("相对路径不是文件夹，请重新尝试");
            return fileMap;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length != 0) {
            fileMap = new HashMap<>();
            for (File tmpFile : listFiles) {
                fileMap.put(tmpFile.getName(), tmpFile);
            }
        }
        return fileMap;
    }
}
