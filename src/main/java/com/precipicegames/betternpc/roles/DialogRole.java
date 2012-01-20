package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;

public class DialogRole implements Role {
	private String text;
	public void startRole(Player p, NPC npc, Stack<Role> s) {
		// TODO Auto-generated method stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Dialog Role";
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return getText();
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

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
		// TODO Auto-generated method stub
		
	}

}
