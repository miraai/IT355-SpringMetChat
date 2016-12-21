package com.mrvelibor.metchat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrvelibor.metchat.podaci.MetUser;

@RestController
public class MetUserController {
    
	@Autowired
	private MetUserRepository repository;
    
    @RequestMapping("/usr")
    public List<MetUser> users(@RequestParam(value="username", defaultValue="") String username) {
    	if(!username.isEmpty()) {
    		List<MetUser> list = new ArrayList<MetUser>();
    		list.add(repository.findByUsername(username));
    		return list;
    	}
		return repository.findAll();
    }
}