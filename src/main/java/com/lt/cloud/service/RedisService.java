package com.lt.cloud.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
	/**
	 * 根据指定key获取string
	 * @param key
	 * @return
	 */
	public String getStr(String key);
	/**
	 * 设置str缓存
	 */
	public void setStr(String key, String val);
	void setStr(String key, String value, long timeout, TimeUnit unit);
}
