package com.lt.cloud.service;

import java.util.List;

import com.lt.cloud.pojos.PermissionList;

public interface PermissionListService{
	public String findAll(String receiver);
	public Boolean save(String entity);
	public boolean exists(String receiver);
	public boolean deleteById(Long id);
	public Boolean update(String entity);
	public String hasPermission(String token, String url);
	List<PermissionList> findByCompanyAndRoleIn(String company,List<String> role);
}
