package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFactory;
import com.precipicegames.tutorialsign.widgets.Dialog;

public class AliasedRole implements Role {
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String label;
	private Role role; 
	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
		Role r = s.pop();
		r.handleFinished(p, npc, s);
	}

	public void startRole(Player p, NPC npc, Stack<Role> s) {
		s.push(this);
		role.startRole(p, npc, s);
	}
	public String getDetails() {
		return this.getLabel();
	}

	public ConfigurationSection getConfig() {
		MemoryConfiguration config = new MemoryConfiguration();
		config.set("label", this.label);
		if(this.role != null) {
			String type = RoleFactory.getName(role.getClass());
			ConfigurationSection roleconfig = role.getConfig();
			config.set("type", type);
			config.set("role", roleconfig);
		}
		return config;
	}

	public void loadConfig(ConfigurationSection config) {
		this.label = config.getString("label","Default Label");
		if(config.isString("type")) {
			Role r = null;
			try {
				ConfigurationSection rconfig = config.getConfigurationSection("role");
				if(rconfig == null) {
					rconfig = new MemoryConfiguration();
				}
				r = RoleFactory.newRole(config.getString("type"),rconfig);
				setRole(r);
			} catch (Exception e) {	}
		}
	}

	public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		// TODO Auto-generated method stub
		return null;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return "Alias Role";
	}

}
