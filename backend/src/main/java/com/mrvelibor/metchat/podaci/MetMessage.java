package com.mrvelibor.metchat.podaci;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class MetMessage {
	
	@Id
	public String id;
	
	public MetUser sender;
	public MetReceiver receiver;
	
	public String text;
	public Date date;
	
	public MetMessage() {}
	
	public MetMessage(MetUser sender, MetReceiver receiver, String text, Date date) {
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.date = date;
	}

    @Override
    public String toString() {
        return String.format(
                "MetMessage[id='%s'; from='%s'; to='%s'; date='%s'; text='%s']",
                id,
                sender,
                receiver,
                date,
                text);
    }
}
