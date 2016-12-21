package com.mrvelibor.metchat.podaci;

import org.springframework.data.annotation.Id;

public class MetUser implements MetReceiver {

	@Id
	public String username;
	public String password;
	
	public MetUser() {}
	
	public MetUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

    @Override
    public String toString() {
        return String.format(
                "MetUser[username='%s']",
                username);
    }
}
