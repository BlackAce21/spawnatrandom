package me.Blackace.spawnatrandom.listeners;

import me.Blackace.spawnatrandom.spawnatrandom;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerTeleportListener implements Listener {


	spawnatrandom plugin;


	public PlayerTeleportListener(spawnatrandom instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTeleportEvent(PlayerTeleportEvent e)
	{
		if(plugin.config.getBoolean("Options." + "Command based autospawn") == true) return;
		String worldName = e.getTo().getWorld().getName();
		Player player = e.getPlayer();
		if(plugin.spawnsConfig.getBoolean("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before") == true) return;
		if(plugin.config.getList("Options." + "Worlds to enable random spawn in").contains(e.getTo().getWorld().getName()) == false) return;
			
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".X", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Y", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Z", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before", false , "");
		plugin.spawnsConfig.saveConfig();
		
		if(plugin.config.getBoolean("Options." + "Command based autospawn") == false) 
		{
			Location spawnLocation = plugin.randomSpawn(player.getWorld(), player);
			player.teleport(spawnLocation.add(0, 2, 0) , TeleportCause.UNKNOWN);
			player.sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");
		}



	}




}









