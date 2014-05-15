package me.Blackace.spawnatrandom.commands;

import me.Blackace.spawnatrandom.spawnatrandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
				Player player = ((Player) sender).getPlayer();
				if(player.getWorld().getName().contains("Admin") || player.getWorld().getName().contains("gather"))
				{
					String playerName = player.getName();
					double savedX = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".X");
					double savedY = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".Y");
					double savedZ = plugin.spawnsConfig.getDouble("spawns." + playerName + ".Worlds." + "HorizonsPvP" + ".Z");

					Location savedSpawn = new Location(Bukkit.getServer().getWorld("HorizonsPvP"), savedX, savedY, savedZ);
					player.teleport(savedSpawn);
					sender.sendMessage(ChatColor.GOLD + "Teleported back to your spawn.");

					return true;
				}
			}
		}
		return true;
	}

}
