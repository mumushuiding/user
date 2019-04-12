package com.lt.cloud.utils;

public class ResponseCodeUtils {
	public static String response(String message) {
		return "{\"message\":\""+message+"\"}";
	}
	public static String response(boolean flag) {
		return "{\"message\":"+(flag ? "\"成功\"":"\"失败\"")+"}";
	}
}
