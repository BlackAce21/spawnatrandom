package me.Blackace.spawnatrandom.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
		Player player = event.getPlayer();
		if(event.isBedSpawn() == true) return;
		if(event.isBedSpawn() == false)
		{	
			
			String playerName = event.getPlayer().getName();
			World savedWorld = player.getServer().getWorld((String) plugin.spawnsConfig.get("spawns." + playerName + ".World"));
			double savedX = plugin.spawnsConfig.getDouble("spawns." + playerName + ".X");
			double savedY = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Y");
			double savedZ = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Z");
			
			Location savedSpawn = new Location(savedWorld, savedX, savedY, savedZ);
			event.setRespawnLocation(savedSpawn);
		return;
		}
		
	}


}

