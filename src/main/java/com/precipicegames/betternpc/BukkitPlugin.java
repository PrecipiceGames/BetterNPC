package com.precipicegames.betternpc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.topcat.npclib.NPCManager;

public class BukkitPlugin extends JavaPlugin implements Listener {

	public static BukkitPlugin plugin;
	public static DialogManager dialog;
	public HashMap<String,NPC> LoadedNPC;
	public HashSet<Player> editors;
	public final Logger logger = Logger.getLogger("Minecraft");
	protected NPCManager npcman;
	private YamlConfiguration config;
	
	public void onEnable(){
		final PluginDescriptionFile pdffile = this.getDescription();
		PluginManager pm = this.getServer().getPluginManager();
		
		this.LoadedNPC = new HashMap<String,NPC>();
		this.editors = new HashSet<Player>();
		npcman = new NPCManager(this);
		dialog = new DialogManager(this);
		
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
		pm.registerEvents(this, this);
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
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command must be used by a player!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("nplace")){
			// Create an NPC, We must create a new configuration section in which the npc will get its construction details
			ConfigurationSection cs = LocationConfigSerializer.toConfig(player.getLocation());
			MemoryConfiguration npcConfig = new MemoryConfiguration();
			npcConfig.set("location", cs);
			NPC npc = new NPC("DefaultNPC", this, npcConfig);
			//Actually spawn the npc
			npc.respawn();
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("nedit")){
			if(!player.hasPermission("npc.edit")) {
				player.sendMessage("You do not have permission to execute that command");
			}
			if(this.editors.contains(player)) {
				this.editors.remove(player);
				player.sendMessage("You are no longer editing npcs!");
			} else {
				this.editors.add(player);
				player.sendMessage("You can now edit NPCs!");
			}
		}
	return false;
	}
}
