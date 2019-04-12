package com.lt.cloud.service;
public interface BaseService {
	public String findAll(String receiver);
	public Boolean save(String entity);
	public boolean deleteById(Long id);
	public boolean exists(String receiver);
}
