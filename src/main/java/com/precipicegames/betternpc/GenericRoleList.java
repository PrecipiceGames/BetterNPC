package com.precipicegames.betternpc;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;

public class GenericRoleList implements RoleList {
	private ArrayList<Role> roles;
	public GenericRoleList(){
		roles = new ArrayList<Role>();
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
		// TODO Auto-generated method stub
		return roles.iterator();
	}
	public void handleFinished(Player p, NPC n, Role role) {
		// TODO Auto-generated method stub
		
	}
}
