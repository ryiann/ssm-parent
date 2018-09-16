package com.ryan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json 工具类
 * @author YoriChen
 * @date 2018/5/21
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toString(Object obj){
		return toJson(obj);
	}
	
	public static String toJson(Object obj){
		try{
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, obj);
			return writer.toString();
		}catch(Exception e){
			throw new RuntimeException("序列化对象【"+obj+"】时出错", e);
		}
	}
	
	public static <T> T toBean(Class<T> entityClass, String jsonString){
		try {
			return mapper.readValue(jsonString, entityClass);
		} catch (Exception e) {
			throw new RuntimeException("JSON【"+jsonString+"】转对象时出错", e);
		}
	}
	
	/**
	 * 用于对象通过其他工具已转为JSON的字符形式，这里不需要再加上引号
	 *
	 * @param obj
	 * @param isObject
	 */
	public static String getJsonSuccess(String obj, boolean isObject){
		String jsonString = null;
		if(obj == null){
			jsonString = "{\"success\":true}";
		}else{
			jsonString = "{\"success\":true,\"data\":"+obj+"}";
		}
		return jsonString;
	}
	
	public static String getJsonSuccess(Object obj){
		return getJsonSuccess(obj, null);
	}
	
	public static String getJsonSuccess(Object obj, String message) {
		if(obj == null){
			return "{\"success\":true,\"message\":\""+message+"\"}";
		}else{
			try{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", true);
				return "{\"success\":true,"+toString(obj)+",\"message\":\""+message+"\"}";
			}catch(Exception e){
				throw new RuntimeException("序列化对象【"+obj+"】时出错", e);
			}
		}
	}
	
	public static String getJsonError(Object obj){
		return getJsonError(obj, null);
	}
	
	public static String getJsonError(Object obj, String message) {
		if(obj == null){
			return "{\"success\":false,\"message\":\""+message+"\"}";
		}else{
			try{
				obj = parseIfException(obj);
				return "{\"success\":false,\"data\":"+toString(obj)+",\"message\":\""+message+"\"}";
			}catch(Exception e){
				throw new RuntimeException("序列化对象【"+obj+"】时出错", e);
			}
		}
	}
	
	public static Object parseIfException(Object obj){
		if(obj instanceof Exception){
			return getErrorMessage((Exception) obj, null);
		}
		return obj;
	}
	
	public static String getErrorMessage(Exception e, String defaultMessage){
		return defaultMessage != null ? defaultMessage : null;
	}
	
	public static ObjectMapper getMapper() {
		return mapper;
	}

	/***
	 * JSON 转换为 List
	 *
	 * @param jsonStr
	 *         [{"age":12,"createTime":null,"id":"","name":"wxw","registerTime":null,"sex":1},{...}]
	 * @param objectClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> json2List(String jsonStr, Class<T> objectClass){
		if (StringUtils.isNotBlank(jsonStr)) {
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			List<T> list = (List<T>) JSONArray.toCollection(jsonArray, objectClass);
			return list;
		}
		return null;
	}
}