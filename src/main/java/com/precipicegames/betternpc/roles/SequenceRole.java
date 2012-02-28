package com.precipicegames.betternpc.roles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.GenericRoleList;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.SequenceConfigurator;
import com.precipicegames.tutorialsign.widgets.Dialog;


public class SequenceRole extends GenericRoleList implements Role{
	public HashMap<Player,Iterator<Role>> rolestatus;
	public SequenceRole() {
		super();
		rolestatus = new HashMap<Player,Iterator<Role>>();
	}
	public void startRole(Player p, NPC npc, Stack<Role> s) {
		Iterator<Role> currentRole = this.getRoleIterator();
		rolestatus.put(p, currentRole);
		if(currentRole.hasNext()) {
			s.push(this);
			currentRole.next().startRole(p, npc, s);
		} else {
			Role r = s.pop();
			r.handleFinished(p, npc, s);
		}
	}
	public void handleFinished(Player p, NPC n , Stack<Role> s) {
		Iterator<Role> currentRole = rolestatus.get(p);
		if(currentRole == null) {
			return;
		}
		if(currentRole.hasNext()) {
			s.push(this);
			currentRole.next().startRole(p, n, s);
		} else {
			Role r = s.pop();
			r.handleFinished(p, n, s);
			rolestatus.remove(p);
		}
	}
	public String getName() {
		return "Sequence Role";
	}
	public String getDetails() {
		return this.getRoleCount() + " number of subroles";
	}
	public ConfigurationSection getConfig() {
		return super.getConfig();
	}
	public void loadConfig(ConfigurationSection config) {
		super.loadConfig(config);
	}
	public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		return new SequenceConfigurator(this,p,npc,d);
	}

}
