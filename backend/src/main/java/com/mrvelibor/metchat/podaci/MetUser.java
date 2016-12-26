package com.mrvelibor.metchat.podaci;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrvelibor.metchat.Hashish;

public class MetUser {

	@Id
	public String username;
	@JsonIgnore
	public String password;
	public int avatar;
	
	public Set<String> favorites;
	
	public Set<String> groups;
	
	public Date lastAction;
	
	public MetUser() {}
	
	public MetUser(String username, String password, int avatar) {
		this.username = username;
		this.password = Hashish.HashPassword(password);
		this.avatar = avatar;
		this.favorites = new HashSet<String>();
		this.groups = new HashSet<String>();
		this.lastAction = new Date();
	}

    @Override
    public String toString() {
        return String.format(
                "MetUser[username='%s'; password='%s'; avatar=%d; favorites={%s}; groups={%s}; lastAction='%s']",
                username,
                password,
                avatar,
                Arrays.toString(favorites.toArray()),
                Arrays.toString(groups.toArray()),
                lastAction);
    }
}
