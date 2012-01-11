package com.precipicegames.betternpc.roles;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.precipicegames.betternpc.ConfigDialog;
import com.precipicegames.betternpc.GenericRoleList;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFinishEvent;
import com.precipicegames.betternpc.RoleList;


public class SequenceRole extends GenericRoleList implements Role{
	public HashMap<Player,Iterator<Role>> rolestatus;
	private RoleList parent;
	public SequenceRole() {
		super();
	}
	public void performRole(Player p, NPC npc, RoleList parent) {
		Iterator<Role> currentRole = this.getRoleIterator();
		if(currentRole.hasNext()) {
			currentRole.next().startRole(p, npc, this);
		}
		this.parent = parent;
		rolestatus.put(p, currentRole);
		
	}
	public void handleFinished(Player p, NPC n ,Role r) {
		Iterator<Role> currentRole = rolestatus.get(p);
		if(currentRole == null)
			return;
		
		if(currentRole.hasNext()) {
			currentRole.next().startRole(p, n, this);
		} else {
			RoleFinishEvent e = new RoleFinishEvent(p, n, this, parent);
			n.scheduleEvent(e);
			rolestatus.remove(p);
		}
	}
	public String getName() {
		return "Sequence Role";
	}
	public String getDetails() {
		// TODO Auto-generated method stub
		return this.getRoleCount() + " number of subroles";
	}
	public ConfigurationSection getConfig() {

		return null;
	}
	public void loadConfig(ConfigurationSection config) {
		// TODO Auto-generated method stub
	}
	public ConfigDialog getConfigureDialog() {
		// TODO Auto-generated method stub
		return null;
	}
	public void startRole(Player p, NPC npc, RoleList parent) {
		// TODO Auto-generated method stub
		
	}
	public void finishRole(Player p, NPC npc, RoleList parent) {
		// TODO Auto-generated method stub
		
	}

}
