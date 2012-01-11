package com.precipicegames.betternpc.roles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleList;

public class AudioRole implements Role {

	public void startRole(Player p, NPC npc, RoleList parent) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	public void loadConfig(ConfigurationSection config) {
		// TODO Auto-generated method stub
	}

	public ConfigDialog getConfigureDialog() {
		// TODO Auto-generated method stub
		return null;
	}

}
