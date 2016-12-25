package com.mrvelibor.metchat.podaci;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class MetUser {

	@Id
	public String username;
	public String password;
	
	public Set<String> favorites;
	
	public Set<String> groups;
	
	public Date lastAction;
	
	public MetUser() {}
	
	public MetUser(String username, String password) {
		this.username = username;
		this.password = password;
		this.favorites = new HashSet<String>();
		this.groups = new HashSet<String>();
		this.lastAction = new Date();
	}

    @Override
    public String toString() {
        return String.format(
                "MetUser[username='%s'; password='%s'; favorites={%s}; groups={%s}; lastOnline='%s']",
                username,
                password,
                Arrays.toString(favorites.toArray()),
                Arrays.toString(groups.toArray()),
                lastAction);
    }
}
