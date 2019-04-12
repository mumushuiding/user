package com.lt.cloud.service;

import com.lt.cloud.pojos.Labeltype;

public interface LabeltypeService extends BaseService{

	boolean existsByName(String name);
	
	Labeltype findByName(String name);
}
