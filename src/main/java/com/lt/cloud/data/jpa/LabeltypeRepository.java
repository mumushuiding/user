package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.Label;
import com.lt.cloud.pojos.Labeltype;

public interface LabeltypeRepository extends JpaRepository<Labeltype,Long>{
	Page<Labeltype> findAll(Specification<Labeltype> specification,Pageable pageable);

	List<Label> findAll(Specification<Labeltype> specification);

	boolean existsByName(String name);
	
	Labeltype findByName(String name);
}
