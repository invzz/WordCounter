package models;

import java.util.HashMap;

import implementations.User;

public class Session {
	
	public User user;  		
	public boolean status; 		
	
	public HashMap<String,Integer> data; 
	
	public Session(User user) {
		this.user = user;
		status = false;
		data = new HashMap<String,Integer>();
	}
	
	public User getUser(){
		return user;
	}
	
	public HashMap<String,Integer> getData(){
		return data;
	}
}