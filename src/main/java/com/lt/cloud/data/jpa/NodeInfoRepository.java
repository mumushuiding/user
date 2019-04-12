package com.lt.cloud.data.jpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lt.cloud.pojos.NodeInfo;

public interface NodeInfoRepository extends JpaRepository<NodeInfo, Long>{

	Page<NodeInfo> findAll(Specification<NodeInfo> specification, Pageable pageable);
	
	boolean existsByUserid(Long userid);
	
	NodeInfo findByUserid(Long id);
}
