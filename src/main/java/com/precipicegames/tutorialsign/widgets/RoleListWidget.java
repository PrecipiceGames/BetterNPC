package com.precipicegames.tutorialsign.widgets;

import java.util.List;

import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.ListWidgetItem;

import com.precipicegames.betternpc.Role;

public class RoleListWidget extends GenericListWidget {

	public RoleListWidget(Role[] roles) {
		super();
		this.setList(roles);
	}
	public RoleListWidget() {
		super();
	}
	public void setList(Role[] roles) {
		this.clear();
		int index = 0;
		for(Role r : roles) {
			this.addItem(new RoleWidgetItem(r,index));
			index++;
		}
	}
	public void renumber() {
		int index = 0;
		for(RoleWidgetItem item : (RoleWidgetItem[]) this.getItems()) {
			item.render(index);
			index++;
		}
	}
}
