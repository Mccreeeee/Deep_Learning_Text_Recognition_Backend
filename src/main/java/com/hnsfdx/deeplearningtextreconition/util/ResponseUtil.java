package com.hnsfdx.deeplearningtextreconition.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static Map<String, Object> sucMsg() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("status", "success");
        return returnMap;
    }

    public static Map<String, Object> failMsg() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("status", "fail");
        return returnMap;
    }
}
