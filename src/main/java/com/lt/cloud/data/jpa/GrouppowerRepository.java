package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.Grouppower;


public interface GrouppowerRepository extends JpaRepository<Grouppower, Long>{
	Page<Grouppower> findAll(Specification<Grouppower> specification,Pageable pageable);

	List<Grouppower> findAll(Specification<Grouppower> specification);
}
