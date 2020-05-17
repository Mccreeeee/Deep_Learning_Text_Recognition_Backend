package com.hnsfdx.deeplearningtextrecognition.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RecogUtil {
    private static final Logger logger = LoggerFactory.getLogger(RecogUtil.class);
    private static final String PYTHON_SCRIPT_ROOT_DIR_PATH = "F:\\ocr_chinese\\";
    public static String getResultFromPyScript(String localLocation) {
        Process proc;
        File f= new File(localLocation);
        String loc = f.getAbsolutePath().replaceAll("\\\\", "/");
        String result = null;
        String fileName = f.getName().split("\\.")[0];
        try {
            proc = Runtime.getRuntime().exec("cmd /c " + PYTHON_SCRIPT_ROOT_DIR_PATH +"run.bat "
                    + PYTHON_SCRIPT_ROOT_DIR_PATH + " " +loc);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            int res = proc.waitFor();
            if (res == 0) {
                File tmp = new File(PYTHON_SCRIPT_ROOT_DIR_PATH + fileName + ".txt");
                BufferedReader bf = new BufferedReader(new FileReader(tmp));
                String stext;
                while ((stext = bf.readLine()) != null) {
                    result += stext + "\r\n";
                }
                bf.close();
                tmp.delete();
            }
        } catch (IOException e) {
            logger.error("发生IO处理异常，异常信息为{}", e.getMessage());
        } catch (InterruptedException e) {
            logger.error("发生终端处理异常，异常信息为{}", e.getMessage());
        }
        return result;
    }
}
