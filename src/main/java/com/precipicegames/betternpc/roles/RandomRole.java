package com.precipicegames.betternpc.roles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.GenericRoleList;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFinishEvent;
import com.precipicegames.betternpc.RoleList;

public class RandomRole extends GenericRoleList implements Role {

	private RoleList parent;
	public void startRole(Player p, NPC npc, RoleList parent) {
		this.parent = parent;
		int random = (int) Math.round(Math.random()*this.getRoleCount());
		this.getRole(random).startRole(p, npc, this);
	}
	public void handleFinished(Player p, NPC n, Role role) {
		RoleFinishEvent e = new RoleFinishEvent(p, n, this, parent);
		n.scheduleEvent(e);
	}
	public String getName() {
		// TODO Auto-generated method stub
		return "RandomRole";
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return this.getRoleCount() + " subroles";
	}

	public ConfigurationSection getConfig() {
		return null;
	}

	public void loadConfig(ConfigurationSection config) {
	}
	public ConfigDialog getConfigureDialog() {
		return null;
	}
}