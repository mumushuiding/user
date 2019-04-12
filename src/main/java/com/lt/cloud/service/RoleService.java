package com.lt.cloud.service;

import java.util.List;
import java.util.Map;

import com.lt.cloud.pojos.Role;

public interface RoleService extends BaseService{

	boolean existsGroup(String json);

	String findRoleByUseridAsTree(Long  id);

	String findRolesAsTree(String json);

	boolean existsRole(String json);

	List<Role> findAllByUserid(Long id);

	Map<String, List<Role>> findAllWithUseridClassifyByType(Long id);
}
