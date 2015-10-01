package com.mcjtf.AuthPlus;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
	private AuthPlus main;
	public static String host;
	public static String database;
	public static String table;
	public static String user;
	public static String password;
	public static int port;
	public static int timeout;
	public static String allowUserName;

	public Settings(AuthPlus main){
		this.main=main;
	}
	public void LoadConfig(){
		FileConfiguration c = main.getConfig();
		String path="mysql.";
		host=c.getString(path+"host");
		database=c.getString(path+"database");
		table=c.getString(path+"table");
		user=c.getString(path+"user");
		password=c.getString(path+"password");
		port=c.getInt(path+"port");
		timeout=c.getInt("session.time");
		allowUserName=c.getString("settings.allowUserName");
	}
}
