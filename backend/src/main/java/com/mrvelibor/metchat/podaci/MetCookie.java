package com.mrvelibor.metchat.podaci;

import org.springframework.data.annotation.Id;

public class MetCookie {

	@Id
	public String token;
	public String username;
	
	public MetCookie() {}
	
	public MetCookie(MetUser user) {
		this.username = user.username;
	}

    @Override
    public String toString() {
        return String.format(
                "MetCookie[token='%s', username='%s']",
                token,
                username);
    }
}
