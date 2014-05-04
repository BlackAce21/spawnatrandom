package me.Blackace.spawnatrandom.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

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
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{
		
		if(e.isBedSpawn() == true) return;
		
		if(plugin.config.getBoolean("Options." + "Continous Random Spawn") == true && plugin.config.getList("Options." + "Worlds to enable random spawn in").contains(e.getPlayer().getWorld().getName()))
		{
			Location spawnLocation = plugin.randomSpawn(e.getPlayer().getWorld(), e.getPlayer());
			e.getPlayer().teleport(spawnLocation.add(0, 2, 0) , TeleportCause.UNKNOWN);
			e.getPlayer().sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");
			return;
		}
		if(plugin.config.getList("Options." + "Worlds to enable random spawn in").contains(e.getPlayer().getWorld().getName()))
		{
			String worldName = e.getRespawnLocation().getWorld().getName();
			String playerName = e.getPlayer().getName();
			double savedX = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".X");
			double savedY = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".Y");
			double savedZ = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + worldName + ".Z");
			
			Location savedSpawn = new Location(e.getPlayer().getWorld(), savedX, savedY, savedZ);
			e.setRespawnLocation(savedSpawn);
		}
		else return;
	}


}

