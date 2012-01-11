package com.precipicegames.betternpc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TutorialSign extends JavaPlugin{

	public static TutorialSign plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public TutorialSignPlayerListener playerlistener = new TutorialSignPlayerListener(this);
	public TutorialSignBlockListener blockListener = new TutorialSignBlockListener(this);
	private YamlConfiguration config; 
	
	public HashMap<String, Tutorial> tutorials = new HashMap<String,Tutorial>();
	
	public void onEnable(){
		final PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Plugin" + pdffile.getName() + " version " + pdffile.getVersion() + " is now enabled.");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerlistener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this.playerlistener, Event.Priority.Normal, this);
		
		config = new YamlConfiguration();
		File configFile = new File(getDataFolder(),"tutorials.yml");
		config.addDefaults(new DefaultConfiguration());
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
		
		loadTutorials();
		System.out.println(this + ": Loaded " + tutorials.size() + " tutorial(s)");
		
	}
	
	private void loadTutorials() {
		ConfigurationSection sc = config.getConfigurationSection("tutorials");
		if(sc == null)
			return;
		for(String key : sc.getKeys(false)) {
			if(sc.isConfigurationSection(key)) {
				tutorials.put(key, new Tutorial(key, sc.getConfigurationSection(key)));
			}
		}
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
	public void runTutorial(Tutorial tut, Player p)
	{
		
	}
}
