package com.lt.cloud.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lt.cloud.pojos.User;

public interface UserRepository extends JpaRepository<User,Long>{
	Page<User> findAll(Specification<User> specification,Pageable pageable);

	List<User> findAll(Specification<User> specification);
	boolean existsByPhone(String phone);
}
