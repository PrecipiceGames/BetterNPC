package com.precipicegames.tutorialsign.widgets;

import org.getspout.spoutapi.gui.ListWidgetItem;

import com.precipicegames.betternpc.Role;

public class RoleWidgetItem extends ListWidgetItem {
	private Role r;
	public RoleWidgetItem(Role r,int index) {
		super();
		this.r = r;
		this.setTitle("Id: " + index + r.getName());
		this.setText(r.getDetails());
	}
	public Role getRole() {
		return r;
	}
	public void render(int index) {
		this.setTitle("Id: " + index + r.getName());
	}
}