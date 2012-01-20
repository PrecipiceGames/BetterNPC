package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.UniqueRole;

public class RightClickRole extends SymbolicRole implements Role, UniqueRole {

	public void startRole(Player p, NPC npc, Stack<Role> s) {
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Right Click Role";
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return "Linked with";
	}

	public ConfigurationSection getConfig() {
		// TODO Auto-generated method stub
		return super.getConfig();
	}

	public void loadConfig(ConfigurationSection config) {
		super.loadConfig(config);
	}

	public ConfigDialog getConfigureDialog() {
		// TODO Auto-generated method stub
		return super.getConfigureDialog();
	}

	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
		Role r = s.pop();
		r.handleFinished(p, npc, s);
	}

}
