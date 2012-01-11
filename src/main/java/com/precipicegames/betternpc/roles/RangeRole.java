package com.precipicegames.betternpc.roles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleList;
import com.precipicegames.betternpc.UniqueRole;

public class RangeRole implements Role, UniqueRole {
	private Role active;
	public void startRole(Player p, NPC npc, RoleList parent) {
		//Do nothing for this role
	}
	public void setActiveRole(Role r) {
		active = r;
	}
	
	public Role getActiveRole(Role r) {
		return active;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return "Range Role";
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		if(active != null) {
			return active.getName() + " is the subrole";
		} else {
			return "No role configured";
		}
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
