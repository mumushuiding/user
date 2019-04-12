package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.Label;

public interface LabelRepository extends JpaRepository<Label, Long>{
	Page<Label> findAll(Specification<Label> specification,Pageable pageable);
	List<Label> findByTypename(String typename);
	List<Label> findAll(Specification<Label> specification);
}
