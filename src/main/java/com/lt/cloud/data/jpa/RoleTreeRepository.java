package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.RoleTree;

public interface RoleTreeRepository extends JpaRepository<RoleTree, Long>{
	List<RoleTree> findAll(Specification<RoleTree> specification);

	boolean existsByTitleAndCompany(String title, String company);

	RoleTree findByTitleAndCompany(String title, String company);

	boolean existsByParentid(Long id);

}
