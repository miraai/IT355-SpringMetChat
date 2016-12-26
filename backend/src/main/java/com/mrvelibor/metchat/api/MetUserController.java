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
import com.mrvelibor.metchat.podaci.MetUser;
import com.mrvelibor.metchat.repos.MetCookieRepository;
import com.mrvelibor.metchat.repos.MetUserRepository;

@RestController
public class MetUserController {
	
	@Autowired
	private MetUserRepository userRepo;
	@Autowired
	private MetCookieRepository cookieRepo;
    
    @RequestMapping("/user")
    public MetUser getUser(@RequestParam("username") String username) {
    	return userRepo.findOne(username);
    }
    
    @RequestMapping("/users")
    public List<MetUser> findUserByUsername(@RequestParam(value="username", defaultValue="") String username) {
    	if(!username.isEmpty()) {
    		return userRepo.findByUsernameStartingWithIgnoreCase(username);
    	}
		return userRepo.findAll();
    }
    
    @RequestMapping(value = "/user/favorite", method = RequestMethod.POST)
    public MetUser addFavorite(@CookieValue("token") String token, @RequestParam(value="username") String username) {
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
    	
    	if(username.isEmpty()) {
    		throw new BadRequestException("Parameter 'username' must not be empty.");
    	}		
		MetUser user2 = userRepo.findOne(username);
		if(user2 == null) {
			throw new ForbiddenException("User with given 'username' does not exist.");
		}
		if(user.favorites.contains(user2.username)) {
			throw new UnauthorizedException("That user is already in your favorites.");
		}
		
		user.favorites.add(user2.username);
		user = userRepo.save(user);
		 
		return user;
    }
    
    @RequestMapping(value = "/user/unfavorite", method = RequestMethod.POST)
    public MetUser removeFavorite(@CookieValue("token") String token, @RequestParam(value="username") String username) {
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
    	
    	if(username.isEmpty()) {
    		throw new BadRequestException("Parameter 'username' must not be empty.");
    	}		
		MetUser user2 = userRepo.findOne(username);
		if(user2 == null) {
			throw new ForbiddenException("User with given 'username' does not exist.");
		}
		if(!user.favorites.contains(user2.username)) {
			throw new UnauthorizedException("That user is not in your favorites.");
		}
		
		user.favorites.remove(user2.username);
		user = userRepo.save(user);
		 
		return user;
    }
}