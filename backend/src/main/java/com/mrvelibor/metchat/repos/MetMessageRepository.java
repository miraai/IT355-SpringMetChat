package com.mrvelibor.metchat.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mrvelibor.metchat.podaci.MetMessage;

public interface MetMessageRepository extends MongoRepository<MetMessage, String> {

	@Query("{ $and: [ { $or: [ { $and: [ { 'sender': ?0 }, { 'receiver': ?1 } ] }, 
	       { $and: [ { 'sender': ?1 }, { 'receiver': ?0 } ] } ] }, { 'group': false } ] }")
    List<MetMessage> findByUsers(@Param("user1") String user1, @Param("user2") String user2);

    @Query("{ 'receiver': ?0, 'group': true }")
    List<MetMessage> findByGroup(@Param("group") String group);

}
