package com.ryan.utils;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 * @author YoriChen
 * @date 2018/6/21
 */
public class MapUtil {
    /**
     * 转换错误信息为Map
     *
     * @param errors 错误信息List
     * @return
     */
    public static Map<String,String> objectErrorsToMap(List<ObjectError> errors){
        Map<String,String> map = new HashMap<String, String>();
        for(ObjectError error : errors){
            map.put(((FieldError)error).getField(),error.getDefaultMessage());
        }
        return map;
    }
}
