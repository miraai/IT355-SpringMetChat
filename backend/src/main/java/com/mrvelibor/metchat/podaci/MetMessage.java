package com.mrvelibor.metchat.podaci;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class MetMessage {
	
	@Id
	public String id;
	
	public String sender;
	public String receiver;
	public boolean group;
	
	public String text;
	public Date date;
	
	public MetMessage() {}
	
	public MetMessage(MetUser sender, MetUser receiver, String text) {
		this.sender = sender.username;
		this.receiver = receiver.username;
		this.group = false;
		this.text = text;
		this.date = new Date();
	}
	
	public MetMessage(MetUser sender, MetGroup receiver, String text) {
		this.sender = sender.username;
		this.receiver = receiver.name;
		this.group = true;
		this.text = text;
		this.date = new Date();
	}

    @Override
    public String toString() {
        return String.format(
                "MetMessage[id='%s'; from='%s'; to='%s'; group='%s'; date='%s'; text='%s']",
                id,
                sender,
                receiver,
                group,
                date,
                text);
    }
}
