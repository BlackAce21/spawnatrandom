package me.Blackace.spawnatrandom;


import java.util.logging.Logger;

import me.Blackace.spawnatrandom.commands.commandRandomSpawn;
import me.Blackace.spawnatrandom.listeners.JoinListener;
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
	MyConfigManager manager;
	public MyConfig spawnsConfig;
	public MyConfig config;
	JoinListener joinlistener;
	RespawnListener respawnlistener;

	@Override
	public void onEnable()
	{

		randomSpawnLogger.info("Spawn At Random is charging its flux capacitor!");

		//Listeners
		this.joinlistener = new JoinListener(this);
		this.respawnlistener = new RespawnListener(this);
		randomSpawnLogger.info("Join Listener enabled!");
		
		//commands
		this.getCommand("randomspawn").setExecutor(new commandRandomSpawn(this));

		//config
		manager = new MyConfigManager(this);
		spawnsConfig = manager.getNewConfig("Spawns.yml", new String[]{"This is a listing of all first spawns", "all ints are placed as doubles"});
		
		config = manager.getNewConfig("config.yml", new String[]{"This is the main config file for randomspawn", "do not delete the # anywhere" , "it may break the config ;)"});
		config.set("Options:." + "Command based autospawn:", false , "-Setting Command based autospawn to true will disable automatic randomspawning on your configured worlds and instead use the command method which can be set to a command block for having a spawn in another world for example then have them randomSpawn in another world");
		config.set("Options:." + "Continous Random Spawn:", false , "-Setting Continuous Random Spawn to true will also make players Random Spawn each time they die within that worlds border.");
		config.set("Options:." + "Worlds to Spawn in:.", "theworldname", "-Use the list below to set up a list of worlds to randomSpawn in on first join.");
		
		
	}

	@Override
	public void onDisable()
	{
		spawnsConfig.saveConfig();


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
		spawnsConfig.set("spawns." + playerName + ".Has RSpawned Before", true);
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



