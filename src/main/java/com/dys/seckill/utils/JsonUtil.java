
package com.dys.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String object2JsonStr(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonStr2Object(String jsonStr, Class<T> clazz){
        try {
            return objectMapper.readValue(jsonStr.getBytes("UTF-8"), clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 字符串Json 转换为对象List
     * @param strJson [{"username":"sxb"}]
     * @return 根据json转换List
     */
    public static List<Map<String, Object>> jsonToList(String strJson){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSONObject.parseObject(strJson, List.class);
        } catch (JSONException e) {
            System.out.println("json转换List出错："+e.getMessage());
        }
        return list;
    }
    

}
