package com.mrvelibor.metchat.podaci;

import org.springframework.data.annotation.Id;

public class MetRoom {

	@Id
	public String name;
	
	public MetRoom() {}
	
	public MetRoom(String name) {
		this.name = name;
	}
}
