package org.testo.admin.model;

public class Message {

	private String to;
	private String body;
	
	public Message() {	
	}
	
	public Message(String to, String body) {
		this.setTo(to);
		this.setBody(body);
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
