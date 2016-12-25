package com.mrvelibor.metchat.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.mrvelibor.metchat.podaci.MetUser;

public interface MetUserRepository extends MongoRepository<MetUser, String> {

    List<MetUser> findByUsernameStartingWithIgnoreCase(@Param("username") String username);
    
    List<MetUser> findByLastActionGreaterThan(@Param("lastAction") Date lastAction);

}
