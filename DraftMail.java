package com.spring.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class DraftMail {
	
	@GraphId
	private Long id;

	private String mails;
	private String subject;
	private String body;
	
	public String getMails() {
		return mails;
	}
	public void setMails(String mails) {
		this.mails = mails;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
