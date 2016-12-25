package com.mrvelibor.metchat.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrvelibor.metchat.api.errors.BadRequestException;
import com.mrvelibor.metchat.api.errors.ForbiddenException;
import com.mrvelibor.metchat.api.errors.UnauthorizedException;
import com.mrvelibor.metchat.podaci.MetCookie;
import com.mrvelibor.metchat.podaci.MetGroup;
import com.mrvelibor.metchat.podaci.MetUser;
import com.mrvelibor.metchat.repos.MetCookieRepository;
import com.mrvelibor.metchat.repos.MetGroupRepository;
import com.mrvelibor.metchat.repos.MetUserRepository;

@RestController
public class MetGroupController {
    
	@Autowired
	private MetGroupRepository groupRepo;    
	@Autowired
	private MetUserRepository userRepo;
	@Autowired
	private MetCookieRepository cookieRepo;
    
    @RequestMapping("/group")
    public MetGroup getGroup(@RequestParam(value="name") String name) {
		return groupRepo.findOne(name);
    }
    
    @RequestMapping("/groups")
    public List<MetGroup> findGroupByName(@RequestParam(value="name", defaultValue="") String name) {
    	if(!name.isEmpty()) {
    		return groupRepo.findByNameStartingWithIgnoreCase(name);
    	}
		return groupRepo.findAll();
    }
    
    @RequestMapping(value = "/group/create", method = RequestMethod.POST)
    public MetGroup createGroup(@RequestParam(value="name") String name) {
    	if(name.isEmpty()) {
    		throw new BadRequestException("Parameter 'name' must not be empty.");
    	}
		if(groupRepo.exists(name)) {
			throw new ForbiddenException("Group with given 'name' already exists.");
		}
		MetGroup group = groupRepo.save(new MetGroup(name));
		return group;
    }
    
    @RequestMapping(value = "/group/join", method = RequestMethod.POST)
    public MetGroup joinGroup(@CookieValue("token") String token, @RequestParam(value="name") String name) {
    	if(token.isEmpty()) {
    		throw new UnauthorizedException("No token.");
    	}    	
    	MetCookie cookie = cookieRepo.findOne(token);
    	if(cookie == null) {
    		throw new UnauthorizedException("Invalid token.");
    	}    	
    	MetUser user = userRepo.findOne(cookie.username);
    	if(user == null) {
    		throw new UnauthorizedException("Invalid token.");
    	}
    	
    	if(name.isEmpty()) {
    		throw new BadRequestException("Parameter 'name' must not be empty.");
    	}		
		MetGroup group = groupRepo.findOne(name);
		if(group == null) {
			throw new ForbiddenException("Group with given 'name' does not exist.");
		}
		if(group.users.contains(user.username)) {
			throw new UnauthorizedException("You are already a member of that group.");
		}
		
		user.groups.add(group.name);
		group.users.add(user.username);
		user = userRepo.save(user);
		group = groupRepo.save(group);
		 
		return group;
    }
    
    @RequestMapping(value = "/group/leave", method = RequestMethod.POST)
    public MetGroup leaveGroup(@CookieValue("token") String token, @RequestParam(value="name") String name) {
    	if(token.isEmpty()) {
    		throw new UnauthorizedException("No token.");
    	}    	
    	MetCookie cookie = cookieRepo.findOne(token);
    	if(cookie == null) {
    		throw new UnauthorizedException("Invalid token.");
    	}    	
    	MetUser user = userRepo.findOne(cookie.username);
    	if(user == null) {
    		throw new UnauthorizedException("Invalid token.");
    	}
    	
    	if(name.isEmpty()) {
    		throw new BadRequestException("Parameter 'name' must not be empty.");
    	}		
		MetGroup group = groupRepo.findOne(name);
		if(group == null) {
			throw new ForbiddenException("Group with given 'name' does not exist.");
		}
		if(!group.users.contains(user.username)) {
			throw new UnauthorizedException("You are not a member of that group.");
		}
		
		user.groups.remove(group.name);
		group.users.remove(user.username);
		user = userRepo.save(user);
		group = groupRepo.save(group);
		 
		return group;
    }
    
}