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
			if(label.equalsIgnoreCase("randomspawn"))
			{

				if(sender.isOp())
				{
					Player playerToSpawn = Bukkit.getPlayer(args[0]);
					if(playerToSpawn != null)
					{
						if(playerToSpawn.isOnline())
						{
							if(args.length == 2)
							{
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + Bukkit.getWorld(args[1]).getName() + ".X", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + Bukkit.getWorld(args[1]).getName() + ".Y", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + Bukkit.getWorld(args[1]).getName() + ".Z", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + Bukkit.getWorld(args[1]).getName() + ".Has RSpawned Before", false , "");
								plugin.spawnsConfig.saveConfig();

								if(!(sender instanceof Player) && plugin.spawnsConfig.getBoolean("spawns." + Bukkit.getPlayer(args[0]).getName() + ".Worlds." + Bukkit.getWorld(args[1]).getName() + ".Has RSpawned Before") == true)
								{
									Bukkit.getPlayer(args[0]).sendMessage(ChatColor.RED + "You have already random spawned once for the target world. Contact an admin if you feel this is in error.");
									return false;
								}

								World world1 = Bukkit.getServer().getWorld(args[1]);
								Location randomSpawn = plugin.randomSpawn(world1, playerToSpawn);
								playerToSpawn.teleport(randomSpawn);
								if(sender instanceof Player)
								{
									playerToSpawn.sendMessage(ChatColor.GOLD + "You were randomly spawned by admin " + sender.getName() + " your respawn point has been reset for world " + world1.getName() + "!");
								}
								else playerToSpawn.sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");
							}
							else
							{
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + playerToSpawn.getWorld().getName() + ".X", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + playerToSpawn.getWorld().getName() + ".Y", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + playerToSpawn.getWorld().getName() + ".Z", 0.0 , "");
								plugin.spawnsConfig.set("spawns." + playerToSpawn.getName() + ".Worlds." + playerToSpawn.getWorld().getName() + ".Has RSpawned Before", false , "");
								plugin.spawnsConfig.saveConfig();

								World world = playerToSpawn.getWorld();
								Location randomSpawn = plugin.randomSpawn(world, playerToSpawn);
								playerToSpawn.teleport(randomSpawn);
								if(sender instanceof Player)
								{
									playerToSpawn.sendMessage(ChatColor.GOLD + "You were randomly spawned by admin " + sender.getName() + " your respawn point has been reset for world " + world.getName() + "!");
								}
								else playerToSpawn.sendMessage(ChatColor.GOLD + "You wake up in an unfamiliar place...");

							}
						}
						else sender.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online!");
					}
					else sender.sendMessage(ChatColor.RED + "Player does not exist. Use /randomspawn <playername>");
				}
				else sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");

			}
			else sender.sendMessage(ChatColor.RED + "Use /randomspawn <playername>");
		

		return true;
	}






}
