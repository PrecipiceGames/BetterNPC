package com.precipicegames.betternpc;

import java.util.Iterator;

public interface RoleList {
	public Role[] getRoles();
	public int getRoleCount();
	public int addRole(Role role);
	public void delRole(Role role);
	public void delRole(int index);;
	public Role getRole(int index);
	public Iterator<Role> getRoleIterator();
	public Role[] getRoles(Class<? extends Role> roletype);
	public Role getRole(Class<? extends UniqueRole> roletype);
}
