package com.mrvelibor.metchat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mrvelibor.metchat.podaci.MetUser;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface MetUserRepository extends MongoRepository<MetUser, String> {

    public MetUser findByUsername(@Param("username") String username);

}
