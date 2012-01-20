package com.precipicegames.betternpc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.topcat.npclib.NPCManager;

public class BukkitPlugin extends JavaPlugin implements Listener {

	public static BukkitPlugin plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	protected NPCManager npcman;
	private YamlConfiguration config;
	
	public void onEnable(){
		final PluginDescriptionFile pdffile = this.getDescription();
		PluginManager pm = this.getServer().getPluginManager();
		npcman = new NPCManager(this);
		
		config = new YamlConfiguration();
		File configFile = new File(getDataFolder(),"tutorials.yml");
		try {
			config.load(configFile);
		} catch (FileNotFoundException e1) {
			try {
				config.save(configFile);
			} catch (IOException e) {
				System.out.println(this + " had an error creating a default config file");
			}
		} catch (IOException e1) {
			System.out.println(this + " had an error reading the configuration file");
		} catch (InvalidConfigurationException e1) {
			System.out.println(this + " has an invalid configuration file");
		} finally {
			System.out.println(this + ": Loaded configuration file");
		}
		plugin = this;
		this.logger.info("Plugin" + pdffile.getName() + " version " + pdffile.getVersion() + " is now enabled.");
		
	}
	
	public void onDisable(){
		final PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Plugin" + pdffile.getName() + " version " + pdffile.getVersion() + " is now disabled.");
	}
	
	public FileConfiguration getConfig()
	{
		return this.config;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		SpoutPlayer splayer = (SpoutPlayer) sender;
		File confFile = new File("tutorials.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(confFile);
		if (cmd.getName().equalsIgnoreCase("tut")){
			if (args[0] != null){
				if (args[0].equalsIgnoreCase("stop")){
					SpoutManager.getSoundManager().stopMusic(splayer);
					splayer.sendMessage(ChatColor.DARK_GREEN + "The tutorial has stopped playing.");
					return true;
				}else if (args[0].equalsIgnoreCase("play")){
					if (args[1] != null && args[2] != null){
						if (splayer.isSpoutCraftEnabled()){
							String url = config.getString("tutorials.categories." + args[1] + "." + args[2] + ".url");
							SpoutManager.getSoundManager().playCustomMusic(plugin, splayer, url, true);
						}else{
							splayer.sendMessage(ChatColor.RED + "You need SpoutCraft to listen to tutorials!");
						}
					}
				}
			}
		}
	return false;
	}
}
