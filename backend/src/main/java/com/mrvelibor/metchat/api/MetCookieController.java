package com.mrvelibor.metchat.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrvelibor.metchat.Hashish;
import com.mrvelibor.metchat.api.errors.BadRequestException;
import com.mrvelibor.metchat.api.errors.ForbiddenException;
import com.mrvelibor.metchat.podaci.MetCookie;
import com.mrvelibor.metchat.podaci.MetUser;
import com.mrvelibor.metchat.repos.MetCookieRepository;
import com.mrvelibor.metchat.repos.MetUserRepository;

@RestController
public class MetCookieController {
    
	@Autowired
	private MetCookieRepository cookieRepo;    
	@Autowired
	private MetUserRepository userRepo;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MetUser logIn(@RequestParam(value="username") String username, @RequestParam(value="password", defaultValue="") 
			 String password, HttpServletResponse response) {
    	if(username.isEmpty()) {
    		throw new BadRequestException("Parameter 'username' must not be empty.");
    	}		
    	MetUser user = userRepo.findOne(username);
		if(user == null) {
			throw new ForbiddenException("Parameters 'username' or 'password' don't match.");
		}
		if(!user.password.equals(Hashish.HashPassword(password))) {
			throw new ForbiddenException("Parameters 'username' or 'password' don't match.");
		}
		
		MetCookie cookie = cookieRepo.save(new MetCookie(user));
		response.addCookie(new Cookie("token", cookie.token));
		return user;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public MetUser register(@RequestParam(value="username") String username, @RequestParam(value="password", defaultValue="") 
			    String password, @RequestParam(value="avatar", defaultValue="1") 
			    int avatar, HttpServletResponse response) {
    	if(username.isEmpty()) {
    		throw new BadRequestException("Parameter 'username' must not be empty.");
    	}
    	if(avatar < 1 || avatar > 36) {
    		throw new BadRequestException("Parameter 'avatar' must be between 1 and 36.");
    	}
		if(userRepo.exists(username)) {
			throw new ForbiddenException("User with giver 'username' already exists.");
		}
    	MetUser user = userRepo.save(new MetUser(username, password, avatar));
    	
		MetCookie cookie = cookieRepo.save(new MetCookie(user));
		response.addCookie(new Cookie("token", cookie.token));
		return user;
    }

}
