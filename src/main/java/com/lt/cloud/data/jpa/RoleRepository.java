package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lt.cloud.pojos.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Page<Role> findAll(Specification<Role> specification,Pageable pageable);
	List<Role> findAll(Specification<Role> specification);
	boolean existsByUseridAndNodevalue(Long userid, Long nodevalue);
	List<Role> findAllByUserid(Long id);
}
