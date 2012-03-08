package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.bukkit.configuration.MemoryConfiguration;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFactory;
import com.precipicegames.betternpc.roles.SequenceRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;
import com.precipicegames.betternpc.widgets.RoleListWidget;
import com.precipicegames.betternpc.widgets.RoleWidgetItem;

public class SequenceConfigurator extends ConfigDialog {
  private SequenceRole role;
  private RoleListWidget roleList;
  private GenericTextField newRole;

  private class NewRoleB extends GenericButton {
    public void onButtonClick(ButtonClickEvent event) {
      String type = newRole.getText();
      Role r = null;
      try {
        r = RoleFactory.newRole(type, new MemoryConfiguration());
      } catch (Exception e) {
        getPlayer().sendMessage(
            "There was an error creating the new Role of type +" + type);
        e.printStackTrace();
      }
      if (r != null) {
        roleList.addItem(new RoleWidgetItem(r, roleList.getSize()));
      }
    }
  }

  private class ConfigureRoleB extends GenericButton {
    public void onButtonClick(ButtonClickEvent event) {
      RoleWidgetItem item = (RoleWidgetItem) roleList.getSelectedItem();
      if (item == null) {
        return;
      }
      Role r = item.getRole();
      Stack<Dialog> d = getStack();
      d.push(SequenceConfigurator.this);
      Dialog rDialog = r.getConfigureDialog(getPlayer(), getNpc(), d);
      PopupScreen popup = new GenericPopup();
      popup.attachWidget(BukkitPlugin.plugin, rDialog);
      getPlayer().getMainScreen().closePopup();
      getPlayer().getMainScreen().attachPopupScreen(popup);
    }
  }

  private class DeleteRoleB extends GenericButton {
    public void onButtonClick(ButtonClickEvent event) {
      ListWidgetItem item = roleList.getSelectedItem();
      if (item == null) {
        return;
      }
      if (item instanceof RoleWidgetItem) {
        roleList.removeItem(item);
        roleList.renumber();
        roleList.setDirty(true);
      }
    }
  }

  public SequenceConfigurator(SequenceRole role, SpoutPlayer p, NPC npc,
      Stack<Dialog> d) {
    super(p, npc, d);
    this.role = role;
    GenericContainer buttonbox = new ConfigSaveApply(this);
    this.mscBody.setLayout(ContainerType.HORIZONTAL);
    roleList = new RoleListWidget(role.getRoles());
    GenericContainer rSide = new GenericContainer();
    rSide.setMaxWidth(90);
    newRole = new GenericTextField();
    newRole.setMaxHeight(20);
    newRole.setMaximumCharacters(50);
    rSide.addChild(newRole);
    {
      NewRoleB newB = new NewRoleB();
      newB.setText("Add");
      newB.setMaxHeight(15);
      rSide.addChild(newB);
    }
    {
      ConfigureRoleB newB = new ConfigureRoleB();
      newB.setText("Configure");
      newB.setMaxHeight(15);
      rSide.addChild(newB);
    }
    {
      DeleteRoleB newB = new DeleteRoleB();
      newB.setText("Delete");
      newB.setMaxHeight(15);
      rSide.addChild(newB);
    }
    rSide.addChild(new GenericContainer());
    rSide.addChild(buttonbox);
    this.mscBody.addChild(roleList);
    this.mscBody.addChild(rSide);
  }

  public void save() {
    // First erase all roles
    while (role.getRoleCount() > 0) {
      role.delRole(0);
    }
    // Rebuild index
    for (ListWidgetItem item : roleList.getItems()) {
      if (item instanceof RoleWidgetItem) {
        role.addRole(((RoleWidgetItem) item).getRole());
      }
    }
    // Thats all folks!
  }
}
