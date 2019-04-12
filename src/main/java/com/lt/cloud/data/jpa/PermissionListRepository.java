package com.lt.cloud.data.jpa;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.PermissionList;

public interface PermissionListRepository extends JpaRepository<PermissionList, Long>{
	Page<PermissionList> findAll(Specification<PermissionList> specification, Pageable pageable);
	boolean existsByCompanyAndUrl(String company,String url);
	PermissionList findByCompanyAndUrl(String company,String url);
	PermissionList findByCompanyAndUrlStartingWith(String company,String url);
	List<PermissionList> findByCompanyAndUrlIn(String company,List<String> url);
	List<PermissionList> findByCompanyAndRoleIn(String company,List<String> role);
}
