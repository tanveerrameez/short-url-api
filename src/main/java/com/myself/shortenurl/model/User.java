package com.myself.shortenurl.model;

import java.util.List;


public class User {

	private String username;
	private String password;
	private List<ShortUrlMapping> urlList;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<ShortUrlMapping> getUrlList() {
		return urlList;
	}
	
	public synchronized void addUrl(ShortUrlMapping shortUrlMapping) {
		urlList.add(shortUrlMapping);
	}
	
	public synchronized void removeUrl(ShortUrlMapping shortUrlMapping) {
		urlList.remove(shortUrlMapping);
	}
	
	
	
}
