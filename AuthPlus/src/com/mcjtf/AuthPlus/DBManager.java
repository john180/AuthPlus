package com.mcjtf.AuthPlus;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mcjtf.AuthPlus.database.MySQL;

public class DBManager {
	private MySQL sql;
	private AuthPlus main;
	private ResultSet result;

	public DBManager(AuthPlus main){
		this.sql=null;
		this.main=main;
	}
	public void connect(){
		this.sql = new MySQL(main,Settings.host,String.valueOf(Settings.port),Settings.database,Settings.user,Settings.password);
		try {
			sql.openConnection();
		} catch (ClassNotFoundException e1) {
			main.getLogger().info(e1.getMessage());
		} catch (SQLException e1) {
			main.getLogger().info(e1.getMessage());
		}
		try {
			if(this.sql.checkConnection()){
				try {
					this.result= this.sql.querySQL("SHOW TABLES LIKE '"+Settings.table+"'");
					if(!result.next()){
						main.getLogger().info("指定数据表不存在.");
						main.getServer().getPluginManager().disablePlugin(main);
					}
				} catch (SQLException e) {
					main.getLogger().info(e.getMessage());
				}
				main.getLogger().info("已连接至数据库.");
			}else{
				main.getLogger().info("无法连接到数据库.");
				main.getServer().getPluginManager().disablePlugin(main);
			}
		} catch (ClassNotFoundException e) {
			main.getLogger().info(e.getMessage());
		} catch (SQLException e) {
			main.getLogger().info(e.getMessage());
		}
	}
	public String getPassword(String name){
		try {
			ResultSet result = sql.querySQL("select password from "+Settings.table+"  where username ='"+name+"' limit 1;");
			if(result.next()){	
				return result.getString("password");
			}else{
				return null;
			}
		} catch (ClassNotFoundException e) {
			main.getLogger().info(e.getMessage());
		} catch (SQLException e) {
			main.getLogger().info(e.getMessage());
		}
		return null;
	}
	public String getIP(String name){
		try {
			result = sql.querySQL("select ip from "+Settings.table+"  where username ='"+name+"' limit 1;");
			if(result.next()){
				return result.getString("ip");
			}else{
				return "198.18.0.1";
			}
		} catch (ClassNotFoundException e) {
			main.getLogger().info(e.getMessage());
		} catch (SQLException e) {
			main.getLogger().info(e.getMessage());
		}
		return null;
	}
	public long getLastLogin(String name){
		try {
			 result = sql.querySQL("select lastlogin from "+Settings.table+"  where username ='"+name+"' limit 1;");
			if(!result.next()){
				return 0;
			}
			Long last=result.getLong("lastlogin");
			return last;
		} catch (ClassNotFoundException e) {
			main.getLogger().info(e.getMessage());
		} catch (SQLException e) {
			main.getLogger().info(e.getMessage());
		}
		return 0;
	}
	public boolean hasPlayer(String name){
		try {
			result = sql.querySQL("select 1 from "+Settings.table+"  where username ='"+name+"' limit 1;");
			if(result.next()){
				return true;
			}else{
				return false;
			}
		} catch (ClassNotFoundException e) {
			main.getLogger().info(e.getMessage());
		} catch (SQLException e) {
			main.getLogger().info(e.getMessage());
		}
		return false;
	}
	public ResultSet getResult(){
		return result;
	}
	public MySQL getSQL(){
		return this.sql;
	}
}
