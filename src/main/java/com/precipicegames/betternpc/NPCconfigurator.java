package com.precipicegames.betternpc;

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

import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;
import com.precipicegames.betternpc.widgets.RoleListWidget;
import com.precipicegames.betternpc.widgets.RoleWidgetItem;

public class NPCconfigurator extends ConfigDialog {

  private GenericTextField name;
  private GenericTextField newRole;
  private RoleListWidget roleList;

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
        roleList.renumber();
      }
      save();
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
      d.push(NPCconfigurator.this);
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
      save();
    }
  }

  public NPCconfigurator(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    super(p, npc, d);
    name = new GenericTextField();
    name.setText(npc.getName());
    roleList = new RoleListWidget(npc.getRoles());

    GenericContainer buttonbox = new ConfigSaveApply(this);
    GenericContainer top = new GenericContainer();
    top.setLayout(ContainerType.HORIZONTAL);
    newRole = new GenericTextField();
    newRole.setMaxHeight(18);
    newRole.setMaximumCharacters(50);
    newRole.setMargin(2);
    GenericContainer roleWin = new GenericContainer();
    roleWin.addChild(newRole);
    roleWin.addChild(roleList);

    GenericContainer rSide = new GenericContainer();
    rSide.setMaxWidth(50);
    // newRole = new RoleCombo(true);

    {
      NewRoleB newB = new NewRoleB();
      newB.setText("Add");
      newB.setMaxHeight(15);
      newB.setMaxWidth(50);
      rSide.addChild(newB);
    }
    {
      ConfigureRoleB newB = new ConfigureRoleB();
      newB.setText("Configure");
      newB.setMaxHeight(15);
      newB.setMaxWidth(50);
      rSide.addChild(newB);
    }
    {
      DeleteRoleB newB = new DeleteRoleB();
      newB.setText("Delete");
      newB.setMaxHeight(15);
      newB.setMaxWidth(50);
      rSide.addChild(newB);
    }
    rSide.addChild(new GenericContainer());
    top.addChild(rSide);
    top.addChild(roleWin);

    GenericContainer bottom = new GenericContainer();
    bottom.setLayout(ContainerType.HORIZONTAL);
    bottom.addChild(name);
    bottom.addChild(new GenericContainer());
    bottom.addChild(buttonbox);
    bottom.setMaxHeight(20);
    this.mscBody.addChild(top);
    this.mscBody.addChild(bottom);
  }

  public void save() {
    int count = this.getNpc().getRoleCount();
    while (count > 0) {
      this.getNpc().delRole(0);
      count--;
    }
    // Rebuild index
    for (ListWidgetItem item : roleList.getItems()) {
      if (item instanceof RoleWidgetItem) {
        this.getNpc().addRole(((RoleWidgetItem) item).getRole());
      }
    }
    getNpc().setName(this.name.getText());
  }
}
