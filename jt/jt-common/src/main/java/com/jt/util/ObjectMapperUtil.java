package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该工具类解决对象转化中的try-catch问题
 * 	1. 对象转Json,toJson
 * 	2. Json转对象,toObject
 */
public class ObjectMapperUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object obj) {
		String writeValueAsString = null;
		try {
			writeValueAsString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return writeValueAsString;
	}
	
	public static <T> T toObject(String json,Class<T> target) {
		T t = null;
		try {
			t = mapper.readValue(json,target);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
	

}
