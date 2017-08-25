package org.telegram.telegrambots.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin{
	@Override
	public void onEnable(){
		Bukkit.getLogger().info("Enabled Telegram bots API");
	}
	@Override
	public void onDisable(){
		Bukkit.getLogger().info("Disabled Telegram bots API");
	}
}
