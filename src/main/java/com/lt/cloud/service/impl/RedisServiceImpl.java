package com.lt.cloud.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


import com.lt.cloud.service.RedisService;

import io.lettuce.core.RedisException;

@Component
public class RedisServiceImpl implements RedisService{
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Resource(name="stringRedisTemplate")
	ValueOperations<String, String> strVOS;
	@Override
	public String getStr(String key) {
		try {
			return this.strVOS.get(key);
		} catch (RedisException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void setStr(String key, String val) {
		try {
			strVOS.set(key, val);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setStr(String key, String value, long timeout, TimeUnit unit) {
		try {
			strVOS.set(key, value, timeout, unit);
		} catch (RedisException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
