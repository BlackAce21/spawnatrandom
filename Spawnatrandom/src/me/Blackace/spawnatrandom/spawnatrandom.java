package me.Blackace.spawnatrandom;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.Blackace.spawnatrandom.commands.commandRandomSpawn;
import me.Blackace.spawnatrandom.listeners.JoinListener;
import me.Blackace.spawnatrandom.listeners.PlayerTeleportListener;
import me.Blackace.spawnatrandom.listeners.RespawnListener;
import me.Blackace.spawnatrandom.util.MyConfig;
import me.Blackace.spawnatrandom.util.MyConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class spawnatrandom extends JavaPlugin
{
	Logger randomSpawnLogger = Bukkit.getLogger();
	MyConfigManager spawnconfigmanager;
	MyConfigManager configmanager;
	public MyConfig spawnsConfig;
	public MyConfig config;
	JoinListener joinlistener;
	RespawnListener respawnlistener;
	PlayerTeleportListener playerteleportlistener;

	@Override
	public void onEnable()
	{

		randomSpawnLogger.info("Spawn At Random is charging its flux capacitor!");

		//Listeners
		this.joinlistener = new JoinListener(this);
		this.respawnlistener = new RespawnListener(this);
		this.playerteleportlistener = new PlayerTeleportListener(this);
		randomSpawnLogger.info("Join Listener enabled!");
		
		//commands
		this.getCommand("randomspawn").setExecutor(new commandRandomSpawn(this));

		//config
		spawnconfigmanager = new MyConfigManager(this);
		spawnsConfig = spawnconfigmanager.getNewConfig("Spawns.yml", new String[]{"This is a listing of all first spawns", "all ints are placed as doubles"});
		
		configmanager = new MyConfigManager(this);
		config = configmanager.getNewConfig("config.yml", new String[]{"This is the main config file for randomspawn", "do not delete the # anywhere" , "it may break the config ;)"});
		config.set("Options." + "Command based autospawn", false , " ");
		config.set("Options." + "Continous Random Spawn", false , " ");
		if(config.get("Options." + "Worlds to enable random spawn in") == null)
		{
			List<String> worlds = new ArrayList<String>();
			worlds.add("exampleWorld");
			config.set("Options." + "Worlds to enable random spawn in", worlds , " ");
		}
		config.saveConfig();
		
	}

	@Override
	public void onDisable()
	{
		


	}


	public Location randomSpawn(World world, Player player)
	{
		Location randomSpawn;
		String playerName = player.getName();

		double randomX;
		double randomZ;
		double randomY;


		do
		{
			do
			{
				do
				{
					randomX = randomX(world.getName());
					randomZ = randomZ(world.getName());
					while(world.getChunkAt((int) randomX, (int) randomZ).isLoaded() == false)
					{
						world.getChunkAt((int) randomX, (int) randomZ).load();
					}
					randomY = getValidHighestY(world, randomX, randomZ);
					randomSpawn = new Location(world, randomX, randomY, randomZ);
				}
				while(world.getBiome((int) randomX, (int) randomZ) == Biome.OCEAN || world.getBiome((int) randomX, (int) randomZ) == Biome.BEACH || world.getBiome((int) randomX, (int) randomZ) == Biome.RIVER);
			}
			while(randomSpawn.add(0, 1, 0).getBlock().isLiquid() == true || randomSpawn.subtract(0, 1, 0).getBlock().isLiquid() == true);
		}
		while(randomY == -1);



		String worldName = randomSpawn.getWorld().getName();
		spawnsConfig.set("spawns." + playerName + ".Worlds." + worldName + ".X", randomSpawn.getX());
		spawnsConfig.set("spawns." + playerName + ".Worlds." + worldName + ".Y", randomSpawn.getY());
		spawnsConfig.set("spawns." + playerName + ".Worlds." + worldName + ".Z", randomSpawn.getZ());
		spawnsConfig.set("spawns." + playerName + ".Worlds." + worldName + ".Has RSpawned Before", true);
		spawnsConfig.saveConfig();
		return randomSpawn;

	}

	public double randomX(String worldName)
	{
		double test = (Math.random() * 80000);

		if (test > 40000)
		{
			double negativeX = ((test - 40000) * -1);
			double adjustX = config.getDouble("Center Point Locations." + "World." + worldName + ".X");
			negativeX = negativeX + adjustX;
			return negativeX;
		}
		else return test;
	}
	public double randomZ(String worldName)
	{
		double test = (Math.random() * 80000);

		if (test > 40000)
		{
			double negativeZ = ((test - 40000) * -1);
			double adjustZ = config.getDouble("Center Point Locations." + "World." + worldName + ".Z:");
			negativeZ = negativeZ + adjustZ;
			return negativeZ;
		}
		else return test;
	}	


	public double getValidHighestY(World world, double x, double z)
	{
		double y;
		Material blockid = Material.AIR;
		y = 256;
		while(y > 1 && blockid == Material.AIR  || blockid == null || blockid.name().contains("Cloud") == true)
		{
			y--;
			blockid = world.getBlockAt((int) x, (int) y, (int) z).getType();
		}

		if(y == 1 && blockid == Material.AIR || blockid == null) return -1;
		else
		{
			if(y <= 100)
			{
				if(blockid.isSolid())
				{
					return ++y;
				}
				else return -1;
			}
			else return -1;
		}

	}




}



