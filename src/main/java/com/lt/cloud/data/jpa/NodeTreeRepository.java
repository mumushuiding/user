package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.lt.cloud.pojos.NodeTree;

public interface NodeTreeRepository extends JpaRepository<NodeTree, Long>{
	boolean existsByParentid(Long id);
	List<NodeTree> findAll(Specification<NodeTree> specification);
	
	@Query(value="select * from nodetree where id in (select nodevalue from role where userid=?1 and depth >= ?2)",nativeQuery=true)
	List<NodeTree> findNodeByUseridAndDepth(Long id, Integer depth);
	Page<NodeTree> findAll(Specification<NodeTree> specification, Pageable pageRequest);
	boolean existsByParentidAndTitleAndCompany(Long parentid, String title, String company);
	List<NodeTree> findByTitle(String title);
	
}
