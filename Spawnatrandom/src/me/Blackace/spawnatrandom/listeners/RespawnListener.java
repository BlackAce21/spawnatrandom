package me.Blackace.spawnatrandom.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Blackace.spawnatrandom.spawnatrandom;

public class RespawnListener implements Listener
{
	spawnatrandom plugin;


	public RespawnListener(spawnatrandom instance)
	{
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		
		if(event.isBedSpawn() == true) return;
		if(event.isBedSpawn() == false)
		{	
			String worldName = event.getRespawnLocation().getWorld().getName();
			String playerName = event.getPlayer().getName();
			double savedX = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".X");
			double savedY = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".Y");
			double savedZ = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".Z");
			
			Location savedSpawn = new Location(event.getPlayer().getWorld(), savedX, savedY, savedZ);
			event.setRespawnLocation(savedSpawn);
		}
		
	}


}

