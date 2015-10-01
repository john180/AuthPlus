package com.mcjtf.AuthPlus;

public class LoginData {
	private String username;
	private String password;
	private String ip;
	private long lastlogin;
	public LoginData(String username, String password, String ip, long lastlogin)
	{
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.lastlogin = lastlogin;
	}
	public String getName(){
		return this.username;
	}
	public String getPassworld(){
		return this.password;
	}
	public Long getLastlogin(){
		return this.lastlogin;
	}
	public String getIP(){
		return this.ip;
	}
}
