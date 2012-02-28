package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;

public class RandomRole extends SequenceRole implements Role {

	public void startRole(Player p, NPC npc, Stack<Role> s) {
		
		if(this.getRoleCount() <= 0) {
			this.handleFinished(p, npc, s);
		} else {
			int random = (int) Math.round(Math.random()*(this.getRoleCount()-1));
			s.push(this);
			this.getRole(random).startRole(p, npc, s);
		}
	}
	public void handleFinished(Player p, NPC n, Stack<Role> s) {
		Role r = s.pop();
		r.handleFinished(p, n, s);
	}
	public String getName() {
		// TODO Auto-generated method stub
		return "RandomRole";
	}
	public ConfigurationSection getConfig() {
		return super.getConfig();
	}
	public void loadConfig(ConfigurationSection config) {
		super.loadConfig(config);
	}
}