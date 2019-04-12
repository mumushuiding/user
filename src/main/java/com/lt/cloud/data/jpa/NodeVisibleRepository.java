package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.NodeVisible;

public interface NodeVisibleRepository extends JpaRepository<NodeVisible, Long> {
	Page<NodeVisible> findAll(Specification<NodeVisible> specification, Pageable pageable);
	boolean existsByUseridAndNode(Long userid,String node);
	List<NodeVisible> findByUserid(Long userid);
}
