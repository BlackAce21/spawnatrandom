package me.Blackace.spawnatrandom.commands;

import me.Blackace.spawnatrandom.spawnatrandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandRandomSpawn implements CommandExecutor {
	
	spawnatrandom plugin;
	
	public commandRandomSpawn(spawnatrandom instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;

		if(args.length == 1)	
		{
			if(label.equalsIgnoreCase("randomspawn"))
			{

				if(player.isOp())
				{
					Player playerToSpawn = Bukkit.getPlayer(args[0]);
					if(Bukkit.getPlayer(args[0]) != null)
					{
						if(playerToSpawn.isOnline())
						{
							World world = playerToSpawn.getWorld();
							Location randomSpawn = plugin.randomSpawn(world, playerToSpawn);
							playerToSpawn.teleport(randomSpawn);
							playerToSpawn.sendMessage(ChatColor.GOLD + "You were randomly spawned by an admin!");
						}
						else player.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online!");
					}
					else player.sendMessage(ChatColor.RED + "Player does not exist. Use /randomspawn <playername>");
				}
				else player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");

			}
			else player.sendMessage(ChatColor.RED + "Use /randomspawn <playername>");
		}
		else player.sendMessage(ChatColor.RED + "Invalid Arguements! /randomspawn <playername>");

		return true;
	}
	
	
	
	
	
	
}
