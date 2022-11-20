package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.PostCode;

@Repository
public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
	
	@Query(value ="SELECT post_code_id, code, user_id, latitude, longitude,place_name FROM POST_CODE\n"
			+ "WHERE \n"
			+ "user_id =\n"
			+ "(SELECT user_id FROM CUSTOMER \n"
			+ "WHERE  registration_time = (SELECT  MAX(registration_time)  FROM CUSTOMER WHERE username = ?1))",
			nativeQuery = true)
	List<PostCode> findPostCodesByUsernamesLastRegistration(String username);
}
