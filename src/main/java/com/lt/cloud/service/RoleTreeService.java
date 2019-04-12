package com.lt.cloud.service;

public interface RoleTreeService {

	String findAll(String receiver);

	String save(String entity);

	boolean deleteById(Long id);

	boolean exists(String receiver);

	String findAllAstree(String receiver);

	boolean hasChildren(Long id);

}
