package com.mcjtf.AuthPlus;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.mcjtf.AuthPlus.Listeners.LoginListener;

public class AuthPlus extends JavaPlugin{
	private Settings settings;
	private DBManager DB;
	public HashMap<String, LoginData> datas;

	public void onEnable(){
		datas=new HashMap<String,LoginData>();
		
		saveDefaultConfig();
		this.settings=new Settings(this);
		settings.LoadConfig();
		
		getLogger().info("正在连接至数据库...");
		DB=new DBManager(this);
		DB.connect();
		
		getServer().getPluginManager().registerEvents(new LoginListener(this,DB), this);
	}
}
