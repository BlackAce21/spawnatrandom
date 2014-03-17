package me.Blackace.spawnatrandom;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

		Player player = event.getPlayer();

		World world = player.getWorld();
		
		if (player.hasPlayedBefore() == false) 
		{
			Location spawnLocation = plugin.randomSpawn(world, player);
			player.teleport(spawnLocation.add(0, 2, 0));
			player.sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");
		}
		else
		{
			player.sendMessage(ChatColor.GOLD + "You look like you've been here before...");
			return;
		}

	}


}
