package com.mcjtf.AuthPlus.Listeners;

import java.util.Date;

import com.mcjtf.AuthPlus.AuthPlus;
import com.mcjtf.AuthPlus.DBManager;
import com.mcjtf.AuthPlus.LoginData;
import com.mcjtf.AuthPlus.Settings;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener
implements Listener
{
	private DBManager db;
	private AuthPlus main;

	public LoginListener(AuthPlus main, DBManager db)
	{
		this.db = db;
		this.main = main;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		Player p = event.getPlayer();
		String name = p.getName();
		String lowname = p.getName().toLowerCase();
		if(!name.matches(Settings.allowUserName)){
			main.getLogger().info("用户名不符合规则.");
		}
		if (this.db.hasPlayer(name))
		{
			LoginData data = new LoginData(lowname, this.db.getPassword(name), this.db.getIP(name), this.db.getLastLogin(name));
			this.main.datas.put(lowname, data);
			this.main.getLogger().info(data.getName() + "|" + data.getPassworld() + "|" + data.getIP() + "|" + data.getLastlogin());
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		if(!main.datas.containsKey(event.getPlayer().getName().toLowerCase()))return;
		Player p =event.getPlayer();
		long timeout = Settings.timeout * 60000;
		LoginData pd = main.datas.get(p.getName().toLowerCase());
		long ct = new Date().getTime();
		if(((ct - pd.getLastlogin() < timeout) || (timeout == 0L)) && (!pd.getIP().equals("198.18.0.1"))){
			if(pd.getName().equalsIgnoreCase(p.getName()) && pd.getIP().equals(pd.getIP())){
				main.getLogger().info(p.getName()+":自动登录成功.");
				return;
			}
		}
		main.getLogger().info(p.getName()+":自动登录失败.");
	}
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		String name = event.getPlayer().getName().toLowerCase();
		if (this.main.datas.containsKey(name)) {
			this.main.datas.remove(name);
		}
	}
}
