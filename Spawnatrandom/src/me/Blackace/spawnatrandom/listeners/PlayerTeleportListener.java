package me.Blackace.spawnatrandom.listeners;

import me.Blackace.spawnatrandom.spawnatrandom;

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
		
	}
	
	@EventHandler
	public void onJoinWorld(PlayerTeleportEvent e)
	{
		if(e.getCause() != TeleportCause.PLUGIN) return;
		String worldName = e.getTo().getWorld().getName();
		Player player = e.getPlayer();
		
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".X", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Y", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Z", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before", false , "");
		plugin.spawnsConfig.saveConfig();
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
