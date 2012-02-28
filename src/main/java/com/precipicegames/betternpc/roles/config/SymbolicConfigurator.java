package com.precipicegames.betternpc.roles.config;

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

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFactory;
import com.precipicegames.betternpc.RoleList;
import com.precipicegames.betternpc.roles.SymbolicRole;
import com.precipicegames.tutorialsign.widgets.ConfigDialog;
import com.precipicegames.tutorialsign.widgets.ConfigSaveApply;
import com.precipicegames.tutorialsign.widgets.Dialog;
import com.precipicegames.tutorialsign.widgets.RoleListWidget;
import com.precipicegames.tutorialsign.widgets.RoleWidgetItem;

public class SymbolicConfigurator extends ConfigDialog {

	private SymbolicRole role;
	private RoleListWidget roleList;
	private LinkField path;
	private class LinkField extends GenericTextField {
		public void onTypingFinished() {
			setSelected(path.getText());
		}
	}
	private class popB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			path.setText(pop(path.getText()));
			setSelected(path.getText());
		}
	}
	private class resetB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			path.setText(role.getLinkString());
			setSelected(path.getText());
		}
	}
	private class enterB extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			RoleWidgetItem item = (RoleWidgetItem)roleList.getSelectedItem();
			if(item == null) {return;}
			if(item.getRole() instanceof RoleList) {
				RoleList list = (RoleList) item.getRole(); 
				if(list.getRoleCount() >= 1) {
					path.setText(push(path.getText(),0));
					setSelected(path.getText());
				}
			}
		}
	}
	private class configure extends GenericButton {
		public void onButtonClick(ButtonClickEvent event) {
			try {
				SymbolicRole finder = (SymbolicRole)RoleFactory.newRole("SymbolicRole", new MemoryConfiguration());
				finder.setLink(path.getText());
				Role selected = finder.followLink(getNpc());
				Stack<Dialog> d = getStack();
				d.push(SymbolicConfigurator.this);
				Dialog rDialog = selected.getConfigureDialog(getPlayer(),getNpc(), d);
				PopupScreen popup = new GenericPopup();
				popup.attachWidget(BukkitPlugin.plugin, rDialog);
				getPlayer().getMainScreen().attachPopupScreen(popup);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SymbolicConfigurator(SymbolicRole role, SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		super(p, npc, d);
		this.role = role;
		path = new LinkField();
		path.setText(role.getLinkString());
		path.setMaximumLines(1);
		Role followed = role.followLink(npc);
		if(followed != null && followed instanceof RoleList) {
			RoleList fList = (RoleList) followed;
			roleList = new RoleListWidget(fList.getRoles());
		} else {
			roleList = new RoleListWidget(npc.getRoles());
		}
		GenericContainer top = new GenericContainer();
		top.setLayout(ContainerType.HORIZONTAL);
		top.addChild(roleList);
		GenericContainer rightSide = new GenericContainer();
		rightSide.setMaxWidth(30);
		rightSide.addChild(new popB().setText("Up"));
		rightSide.addChild(new enterB().setText("->"));
		top.addChild(rightSide);
		
		GenericContainer bottom = new GenericContainer();
		bottom.setLayout(ContainerType.HORIZONTAL);
		ConfigSaveApply saveclose = new ConfigSaveApply(this);
		bottom.addChild(path);
		bottom.addChild(new resetB().setText("R").setMaxWidth(10));
		bottom.addChild(new configure().setText("C").setMaxWidth(10));
		bottom.addChild(saveclose);
		this.mscBody.addChild(top);
		this.mscBody.addChild(bottom);
	}
	public void setSelected(String rpath) {
		Role selected = null;
		int index = 0;
		try {
			SymbolicRole finder = (SymbolicRole)RoleFactory.newRole("SymbolicRole", new MemoryConfiguration());
			String[] nodes = path.getText().split(":");
			index = Integer.parseInt(nodes[nodes.length-1]);
			String newPath = pop(path.getText());
			finder.setLink(newPath);
			selected = finder.followLink(getNpc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(selected != null && selected instanceof RoleList) {
			RoleList fList = (RoleList) selected;
			roleList.setList(fList.getRoles());
		} else {
			roleList.setList(getNpc().getRoles());
		}
		roleList.setSelection(index);
	}
	private String pop(String rpath){
		String[] nodes = path.getText().split(":");
		int size = nodes.length -1;
		String newPath = new String();
		for(int i = 0; i < size; i++){
			if(i != 0) {
				newPath += ":" + nodes[i];
			} else {
				newPath += nodes[0];
			}
		}
		return newPath;
	}
	private String push(String rpath, int index){
		if(rpath.isEmpty()) {
			return Integer.toString(index);
		} else {
			return rpath + ":" + Integer.toString(index);
		}
	}

	@Override
	public void save() {
		this.role.setLink(path.getText());
	}
}
