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
					randomX = randomX();
					randomZ = randomZ();
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




		spawnsConfig.set("spawns." + playerName + ".X", randomSpawn.getX());
		spawnsConfig.set("spawns." + playerName + ".Y", randomSpawn.getY());
		spawnsConfig.set("spawns." + playerName + ".Z", randomSpawn.getZ());
		spawnsConfig.set("spawns." + playerName + ".World", randomSpawn.getWorld().getName());
		spawnsConfig.saveConfig();
		return randomSpawn;

	}

	public double randomX()
	{
		double test = (Math.random() * 80000);

		if (test > 40000)
		{
			double negativeX = ((test - 40000) * -1);
			return negativeX;
		}
		else return test;
	}
	public double randomZ()
	{
		double test = (Math.random() * 80000);

		if (test > 40000)
		{
			double negativeZ = ((test - 40000) * -1);
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



