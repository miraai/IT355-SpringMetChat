package com.mrvelibor.metchat.podaci;

import java.util.List;

import org.springframework.data.annotation.Id;

public class MetGroup implements MetReceiver {
	
	@Id
	public String name;
	
	public MetUser createdBy;

	public List<MetUser> users;
	
	public MetGroup() {}
	
	public MetGroup(String name) {
		this.name = name;
	}
	
}
