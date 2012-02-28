package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.tutorialsign.widgets.Dialog;

public class AudioRole implements Role {
	private String audioUrl = null;
	public void startRole(Player p, NPC npc, Stack<Role> s) {
		SpoutPlayer sp = SpoutManager.getPlayer(p);
		if(this.getAudioUrl() != null && sp.isSpoutCraftEnabled()) {
			SpoutManager.getSoundManager().playCustomMusic(BukkitPlugin.plugin, sp, this.getAudioUrl(), false);
		}
		Role r = s.pop();
		r.handleFinished(p, npc, s);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Audio Role";
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return "";
	}

	public ConfigurationSection getConfig() {
		MemoryConfiguration config = new MemoryConfiguration();
		if(this.audioUrl != null) {
			config.set("url", this.audioUrl);
		}
		return config;
	}

	public void loadConfig(ConfigurationSection config) {
		config.getString("url", null);
	}

	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		// TODO Auto-generated method stub
		return null;
	}

}
