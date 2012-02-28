package com.precipicegames.betternpc;

import java.util.Stack;

import org.bukkit.configuration.MemoryConfiguration;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.tutorialsign.widgets.ConfigDialog;
import com.precipicegames.tutorialsign.widgets.ConfigSaveApply;
import com.precipicegames.tutorialsign.widgets.Dialog;
import com.precipicegames.tutorialsign.widgets.RoleCombo;
import com.precipicegames.tutorialsign.widgets.RoleListWidget;
import com.precipicegames.tutorialsign.widgets.RoleWidgetItem;

public class NPCconfigurator extends ConfigDialog {

	private GenericTextField name;
	private RoleCombo newRole;
	private RoleListWidget roleList;
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
			d.push(NPCconfigurator.this);
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

	public NPCconfigurator(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		super(p, npc, d);
		name = new GenericTextField();
		newRole = new RoleCombo(true);
		roleList = new RoleListWidget(npc.getRoles());
		
		GenericContainer buttonbox = new ConfigSaveApply(this);
		GenericContainer top = new GenericContainer();
		top.setLayout(ContainerType.HORIZONTAL);
		top.addChild(roleList);
		GenericContainer rSide = new GenericContainer();
		rSide.setWidth(30);
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
		top.addChild(rSide);
		
		GenericContainer bottom = new GenericContainer();
		bottom.setLayout(ContainerType.HORIZONTAL);
		bottom.addChild(name);
		bottom.addChild(buttonbox);
	}

	@Override
	public void save() {
		int count = this.getNpc().getRoleCount();
		while(count > 0) {
			this.getNpc().delRole(0);
		}
		//Rebuild index
		for(RoleWidgetItem item : (RoleWidgetItem[])roleList.getItems()) {
			this.getNpc().addRole(item.getRole());
		}
		getNpc().setName(this.name.getText());
	}
}
