package com.mrvelibor.metchat.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mrvelibor.metchat.podaci.MetGroup;

@RepositoryRestResource(collectionResourceRel = "g", path = "g")
public interface MetGroupRepository extends MongoRepository<MetGroup, String> {

    List<MetGroup> findByNameStartingWithIgnoreCase(@Param("name") String name);

}
