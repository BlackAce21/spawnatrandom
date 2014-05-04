package me.Blackace.spawnatrandom.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.Blackace.spawnatrandom.spawnatrandom;


public class JoinListener implements Listener
{

	spawnatrandom plugin;

	public JoinListener(spawnatrandom instance)
	{
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if(plugin.config.getBoolean("Options." + "Command based autospawn") == true) return;
		Player player = event.getPlayer();
		World world = player.getWorld();
		String worldName = world.getName();
		
		
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".X", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Y", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Z", 0.0 , "");
		plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before", false , "");
		plugin.spawnsConfig.saveConfig();
		
		if (player.hasPlayedBefore() == false || plugin.spawnsConfig.getBoolean("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before") == false) 
		{
			Location spawnLocation = plugin.randomSpawn(world, player);
			player.teleport(spawnLocation.add(0, 2, 0) , TeleportCause.UNKNOWN);
			player.sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");
			plugin.spawnsConfig.set("spawns." + player.getName() + ".Worlds." + worldName + ".Has RSpawned Before", true);
			plugin.spawnsConfig.saveConfig();
		}
		else
		{
			return;
		}
		
		
	}


}
