package com.mrvelibor.metchat.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.mrvelibor.metchat.podaci.MetMessage;
import com.mrvelibor.metchat.podaci.MetUser;
import com.mrvelibor.metchat.repos.MetCookieRepository;
import com.mrvelibor.metchat.repos.MetGroupRepository;
import com.mrvelibor.metchat.repos.MetMessageRepository;
import com.mrvelibor.metchat.repos.MetUserRepository;

@RestController
public class MetMessageController {
    
	@Autowired
	private MetMessageRepository messageRepo;	
	@Autowired
	private MetUserRepository userRepo;
	@Autowired
	private MetGroupRepository groupRepo;
	@Autowired
	private MetCookieRepository cookieRepo;
	
	@RequestMapping("/messages")
	public List<MetMessage> getMessages(@CookieValue("token") String token, @RequestParam(value="username", defaultValue="") 
					    String username, @RequestParam(value="groupname", defaultValue="") 
					    String groupName, @RequestParam(value="newerThan", defaultValue="0") long newerThan) {
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
    	
    	if(username.isEmpty() && groupName.isEmpty()) {
    		throw new BadRequestException("Parameters 'username' or 'groupname' must not be empty.");
    	}
    	
    	user.lastAction = new Date();
    	user = userRepo.save(user);
    	
    	if(!username.isEmpty()) {
    		MetUser user2 = userRepo.findOne(username);
    		if(user2 == null) {
    			throw new ForbiddenException("User with given 'username' does not exist.");
    		}
    		List<MetMessage> messages = messageRepo.findByUsers(user.username, user2.username);
    		if(newerThan != 0) {
    			messages = messages.stream().filter(message -> message.date.after(new Date(newerThan)))
				.collect(Collectors.toList());
    		}
    		return messages;
    	}
    	else {
    		MetGroup group = groupRepo.findOne(groupName);
    		if(group == null) {
    			throw new ForbiddenException("Group with given 'groupname' does not exist.");
    		}
    		List<MetMessage> messages = messageRepo.findByGroup(group.name);
    		if(newerThan != 0) {
    			messages = messages.stream().filter(message -> message.date.after(new Date(newerThan)))
				.collect(Collectors.toList());
    		}
    		return messages;
    	}
	}
    
    @RequestMapping(value = "/message/new", method = RequestMethod.POST)
    public MetMessage newMessage(@CookieValue("token") String token, @RequestParam(value="username", defaultValue="") 
				 String username, @RequestParam(value="groupname", defaultValue="") 
				 String groupName, @RequestParam(value="text", defaultValue="") String text) {
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
    	
    	if(username.isEmpty() && groupName.isEmpty()) {
    		throw new BadRequestException("Parameters 'username' or 'groupname' must not be empty.");
    	}
    	
    	user.lastAction = new Date();
    	user = userRepo.save(user);
    	
    	if(!username.isEmpty()) {
    		MetUser user2 = userRepo.findOne(username);
    		if(user2 == null) {
    			throw new ForbiddenException("User with given 'username' does not exist.");
    		}
    		return messageRepo.save(new MetMessage(user, user2, text));
    	}
    	else {
    		MetGroup group = groupRepo.findOne(groupName);
    		if(group == null) {
    			throw new ForbiddenException("Group with given 'groupname' does not exist.");
    		}
    		return messageRepo.save(new MetMessage(user, group, text));
    	}
    }
}
