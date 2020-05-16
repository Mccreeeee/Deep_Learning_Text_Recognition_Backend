package com.hnsfdx.deeplearningtextrecognition.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static final String BASE_DIR = "temp/images/";
    public static final String BASE_URL = "http://localhost:8080/resources/";
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    // 相对路径（暂定为User_loginName） + 文件名用于存储，可能会出现一些错误，错误时用logger记录
    // 返回在服务器端的相对路径
    public static String uploadToServer(String relativePath, MultipartFile multipartFile) {
        if ("".equals(relativePath) || relativePath == null) {
            logger.error("上传的相对路径为空或为null！");
            return null;
        }
        if (multipartFile.isEmpty() || multipartFile == null) {
            logger.error("上传的文件为空或为null，请检查！");
            return null;
        }
        String fileName = multipartFile.getOriginalFilename();
        File dir = new File(BASE_DIR + relativePath);
//        String absolutePath = dir.getAbsolutePath().endsWith("/") ? dir.getAbsolutePath() : dir.getAbsolutePath() + "/";
        File file = new File(dir.getAbsoluteFile(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            return (BASE_URL + relativePath + "/" + fileName);
        } catch (IOException e) {
            logger.error("创建文件错误！", e.getMessage());
        }
        return null;
    }

    // 删除文件夹相对路径（暂定为User_loginName）下的所有图片文件，可能会出现一些错误，错误时用logger记录
    public static void deleteAllInServer(String relativePath) {
        if ("".equals(relativePath) || relativePath == null) {
            logger.error("删除的相对路径为空或为null！");
        }
        File file = new File(BASE_DIR + relativePath);
        if (!file.isDirectory()) {
            logger.error("删除的相对路径不是文件夹");
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length != 0) {
            for (File tmpFile : listFiles) {
                tmpFile.delete();
            }
        }
    }

    // 删除相对路径（暂定为User_loginName）下的单个图片文件
    public static void deleteSingleInServer(String relativePath, String fileName) {
        if ("".equals(relativePath) || relativePath == null) {
            logger.error("删除的相对路径为空或为null！");
        }
        File dir = new File(BASE_DIR + relativePath);
        File file = new File(dir.getAbsoluteFile(), fileName);
        file.delete();
    }

    // 分割获得Url里的相对路径
    public static String getRelativePath(String url) {
        String res = url.substring(0, url.lastIndexOf("/"));
        res = res.substring(res.lastIndexOf("/") + 1);
        return res;
    }

    // 分割获得Url里的本地文件名
    public static String getLocalFileName(String url) {
        String res = url.substring(url.lastIndexOf("/") + 1);
        return res;
    }

    public static String getLocalLocation(String relativeLocation) {
        File file = new File(relativeLocation);
        return file.getAbsolutePath();
    }
}
