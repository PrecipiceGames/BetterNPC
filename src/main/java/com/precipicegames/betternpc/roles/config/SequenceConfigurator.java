package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.bukkit.configuration.MemoryConfiguration;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFactory;
import com.precipicegames.betternpc.roles.SequenceRole;
import com.precipicegames.tutorialsign.widgets.ConfigDialog;
import com.precipicegames.tutorialsign.widgets.ConfigSaveApply;
import com.precipicegames.tutorialsign.widgets.Dialog;
import com.precipicegames.tutorialsign.widgets.RoleCombo;
import com.precipicegames.tutorialsign.widgets.RoleListWidget;
import com.precipicegames.tutorialsign.widgets.RoleWidgetItem;

public class SequenceConfigurator extends ConfigDialog {
	private SequenceRole role;
	private RoleListWidget roleList;
	private RoleCombo newRole;
	
	private class NewRoleB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			String type = newRole.getSelectedItem();
			Role r = null;
			try {
				r = RoleFactory.newRole(type, new MemoryConfiguration());
			} catch (Exception e) {
				getPlayer().sendMessage("There was an error creating the new Role of type +" + type);
				e.printStackTrace();
			}
			if(r != null) {
				roleList.addItem(new RoleWidgetItem(r,roleList.getSize()));
			}
		}
	}
	private class ConfigureRoleB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			RoleWidgetItem item = (RoleWidgetItem)roleList.getSelectedItem();
			Role r = item.getRole();
			Stack<Dialog> d = getStack();
			d.push(SequenceConfigurator.this);
			Dialog rDialog = r.getConfigureDialog(getPlayer(),getNpc(), d);
			PopupScreen popup = new GenericPopup();
			popup.attachWidget(BukkitPlugin.plugin, rDialog);
			getPlayer().getMainScreen().attachPopupScreen(popup);
		}
	}
	private class DeleteRoleB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			RoleWidgetItem item = (RoleWidgetItem)roleList.getSelectedItem();
			roleList.removeItem(item);
			roleList.renumber();
			roleList.setDirty(true);
		}
	}
	
	public SequenceConfigurator(SequenceRole role, SpoutPlayer p, NPC npc , Stack<Dialog> d) {
		super(p,npc,d);
		this.role = role;
		GenericContainer buttonbox = new ConfigSaveApply(this);
		this.mscBody.setLayout(ContainerType.HORIZONTAL);
		roleList = new RoleListWidget(role.getRoles());
		GenericContainer rSide = new GenericContainer();
		newRole = new RoleCombo(false);
		rSide.addChild(newRole);
		{   NewRoleB newB = new NewRoleB();
			newB.setText("Add");
			rSide.addChild(newB); }
		{   ConfigureRoleB newB = new ConfigureRoleB();
			newB.setText("Configure");
			rSide.addChild(newB); }
		{   DeleteRoleB newB = new DeleteRoleB();
			newB.setText("Delete");
			rSide.addChild(newB); }
		rSide.addChild(buttonbox);
		this.mscBody.addChild(roleList);
		this.mscBody.addChild(rSide);
	}

	public void save() {
		//First erase all roles
		int count = role.getRoleCount();
		while(count > 0) {
			role.delRole(0);
		}
		//Rebuild index
		for(RoleWidgetItem item : (RoleWidgetItem[])roleList.getItems()) {
			role.addRole(item.getRole());
		}
		//Thats all folks!
	}
}
