package com.lt.cloud.data.jpa; 
import org.springframework.data.jpa.repository.JpaRepository;
import com.lt.cloud.pojos.Login;
public interface LoginRepository extends JpaRepository<Login, Long>{
	Login findByNameAndPassword(String name,String password);
}
