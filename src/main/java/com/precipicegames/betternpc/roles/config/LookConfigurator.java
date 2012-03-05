package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.bukkit.Location;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.LookRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;
import com.precipicegames.betternpc.widgets.LocationWidget;

public class LookConfigurator extends ConfigDialog {

  private LookRole role;
  private GenericCheckBox checkbox;
  private LocationWidget location;

  public LookConfigurator(LookRole role, SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    super(p, npc, d);
    this.role = role;
    checkbox = new GenericCheckBox();
    checkbox.setTooltip("Look at player");
    checkbox.setChecked(role.isTargetplayer());
    location = new LocationWidget(npc,false);
    if(role.getLooklocation() != null) {
      location.setLocation(role.getLooklocation());
    }
    this.mscBody.addChildren(checkbox, location);
    this.mscBody.addChild(new GenericContainer());
    this.mscBody.addChild(new ConfigSaveApply(this));
  }

  @Override
  public void save() {
    this.role.setTargetplayer(checkbox.isChecked());
    try {
      Location l = location.getLocation();
      this.role.setLooklocation(l);
    } catch (Exception e) {}
  }

}
