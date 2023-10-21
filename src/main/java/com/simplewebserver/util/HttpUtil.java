package com.simplewebserver.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static Map<String,String> requestHeadBuild(String httpContent){
        String[] strings = httpContent.split("\r\n");
        String path=strings[0];
        List<String> heads = Arrays.asList(strings).subList(1,strings.length);
        Map<String,String>map=new HashMap<>(heads.size());
        for (String head : heads) {
            int m = head.indexOf(":");
            String headKey = head.substring(0, m);
            String headContent = head.substring(m + 2);
            map.put(headKey,headContent);
        }
        map.put("path",path);
        return map;
    }
}
