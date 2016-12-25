package com.mrvelibor.metchat.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.mrvelibor.metchat.podaci.MetCookie;

public interface MetCookieRepository extends MongoRepository<MetCookie, String> {

	List<MetCookie> findByUsername(@Param("username") String username);
	
}
