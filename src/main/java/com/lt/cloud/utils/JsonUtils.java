package com.lt.cloud.utils;
import org.springframework.data.domain.Page;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lt.cloud.utils.adapter.EmptyString2Null4Deserializer;
public class JsonUtils {
	private static Gson gson;
	static {
		gson=new GsonBuilder()
				.registerTypeHierarchyAdapter(Number.class, new EmptyString2Null4Deserializer<>())
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}
	public static Gson getGson(){
		
		return gson;
	}
	public static String formatDataForPagination(String jsonString,long total,int pageIndex,int pageSize){
		StringBuffer stringBuffer=new StringBuffer("{}");
		stringBuffer.insert(1,"\"total\":"+total+",\"page\":"+pageIndex+",\"pageSize\":"+pageSize+",\"rows\":"+jsonString);
		return stringBuffer.toString();
	}
	public static String formatDataForEasyUIForPagination(String jsonString,long total,int pageIndex,int pageSize){
		StringBuffer stringBuffer=new StringBuffer("{}");
		stringBuffer.insert(1,"\"total\":"+total+",\"page\":"+pageIndex+",\"pageSize\":"+pageSize+",\"rows\":"+jsonString);
		return stringBuffer.toString();
	}
	public static String formatPageForPagination(Page page) {
		
		return formatDataForPagination(getGson().toJson(page.getContent()), page.getTotalElements(), page.getPageable().getPageNumber()+1,page.getPageable().getPageSize());
	}

}



