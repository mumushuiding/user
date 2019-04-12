package com.lt.cloud.service;

import com.lt.cloud.pojos.NodeVisible;

public interface NodeVisibleService {

	String findAll(String receiver);

	String save(String entity);

	boolean deleteById(Long id);

	boolean exists(String receiver);
	boolean exists(NodeVisible nodeVisible);

	String findByUserid(Long id);



}
