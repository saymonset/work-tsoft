package com.indeval.portaldali.middleware.services.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static String serializeAsJson(Object o){
		if(logger.isTraceEnabled()) logger.trace("serializeAsJson");
		
		ObjectMapper m = new ObjectMapper();
		try {
			String result = m.writeValueAsString(o);
			
			if(logger.isDebugEnabled())logger.debug(result);
			
			return result;
		}catch(JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return "{}";
		}
	}
	
	public static <T> T readObject(String jsonString, Class<T> clazz){
		T  t = null;
		if(jsonString !=null && !jsonString.trim().isEmpty()) {
			try {
				return new ObjectMapper().readValue(jsonString, clazz);
			}catch(IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return t;
	}
	

	public static <T> List<T> readList(String jsonString, Class<T> clazz){
		List<T> list = new ArrayList<>();
		if(jsonString !=null && !jsonString.trim().isEmpty()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
				return mapper.readValue(jsonString, javaType);
			}catch(IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return list;
	}
}
