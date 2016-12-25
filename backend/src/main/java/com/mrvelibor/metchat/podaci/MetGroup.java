package com.mrvelibor.metchat.podaci;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class MetGroup {
	
	@Id
	public String name;

	public Set<String> users;
	
	public MetGroup() {}
	
	public MetGroup(String name) {
		this.name = name;
		this.users = new HashSet<String>();
	}

    @Override
    public String toString() {
        return String.format(
                "MetGroup[name='%s', users={%s}]",
                name,
                Arrays.toString(users.toArray()));
    }
}
