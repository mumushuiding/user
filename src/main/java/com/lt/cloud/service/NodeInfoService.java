package com.lt.cloud.service;

import com.lt.cloud.pojos.NodeInfo;

public interface NodeInfoService {

	String findAll(String receiver);

	Boolean save(String entity);

	boolean deleteById(Long id);

	boolean exists(String receiver);

	boolean existsByUserid(Long id);

	String findByUserid(Long id);

	NodeInfo findNodeinfoByUserid(Long id);

	NodeInfo save(NodeInfo nodeInfo);

	boolean update(String entity);

}
