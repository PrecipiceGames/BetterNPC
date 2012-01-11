package com.precipicegames.betternpc;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NPC implements RoleList {
	private ArrayList<Role> roles;
	private Plugin plugin;
	public NPC(String displayname, Plugin p){
		roles = new ArrayList<Role>();
		plugin = p;
	}
	public Role[] getRoles() {
		return roles.toArray(new Role[0]);
	}

	public int getRoleCount() {
		return roles.size();
	}

	public int addRole(Role role) {
		if(role instanceof UniqueRole)	{
			if(this.getRole(((UniqueRole)role).getClass()) == null) {
				return -1;
			}
		}
		roles.add(role);
		return roles.size()-1;
	}

	public void delRole(Role role) {
		roles.remove(role);
	}

	public void delRole(int index) {
		roles.remove(index);
	}

	public Role getRole(int index) {
		return roles.get(index);
	}

	public Role[] getRoles(Class<? extends Role> roletype) {
		ArrayList<Role> ret = new ArrayList<Role>();
		for(Role r : roles) {
			if(roletype.isInstance(r)) {
				ret.add(r);
			}
		}
		return ret.toArray(new Role[0]);
	}

	public Role getRole(Class<? extends UniqueRole> roletype) {
		for(Role r : roles) {
			if(roletype.isInstance(r)) {
				return r;
			}
		}
		return null;
	}
	public Iterator<Role> getRoleIterator() {
		return roles.iterator();
	}
	public void handleFinished(Player p, NPC n,Role role) {
		// TODO Auto-generated method stub
		
	}
	public void scheduleEvent(RoleFinishEvent e) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, e);
	}
}
