package me.Blackace.spawnatrandom.commands;

import java.util.List;

import me.Blackace.spawnatrandom.spawnatrandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class commandMySpawn implements CommandExecutor {
	
	spawnatrandom plugin;
	
	public commandMySpawn(spawnatrandom instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(label.equalsIgnoreCase("myspawn"))
		{
			if(sender instanceof Player)
			{
				if(((Player) sender).getPlayer().isFlying())
				{
					sender.sendMessage(ChatColor.RED + "You can't do this while flying");
					return true;
				}
				List<Player> players = ((Player) sender).getWorld().getPlayers();
				for(int i = players.size() - 1 ; i >= 0 ; i--)
				{
					if(((Player) sender).getPlayer().hasLineOfSight((Entity) players.get(i)))
					{
						if(players.get(i) != ((Player) sender).getPlayer())
						{
						sender.sendMessage("There are enemy players nearby. You can not use that right now.");
						return true;
						}
					}
				}
				if(((Player) sender).getPlayer().getNoDamageTicks() >= 200)
				{
					String playerName = ((Player) sender).getName();
					double savedX = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".X");
					double savedY = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".Y");
					double savedZ = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".Z");
					
					Location savedSpawn = new Location(Bukkit.getServer().getWorld("HorizonsPvP"), savedX, savedY, savedZ);
					((Player) sender).teleport(savedSpawn);
					return true;
				}
				else 
				{
					sender.sendMessage(ChatColor.RED + "You must not have taken damage within 10 seconds to do this.");
					return true;
				}
			}
		}
		return false;
	}

}
