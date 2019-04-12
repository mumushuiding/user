package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.Groups;

public interface GroupsRepository extends JpaRepository<Groups, Long>{
	Page<Groups> findAll(Specification<Groups> specification,Pageable pageable);

	List<Groups> findAll(Specification<Groups> specification);
}
