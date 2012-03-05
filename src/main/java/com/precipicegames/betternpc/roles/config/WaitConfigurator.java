package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.WaitRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;

public class WaitConfigurator extends ConfigDialog {

  private WaitRole role;
  private GenericTextField TextField;

  public WaitConfigurator(WaitRole role, SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		super(p, npc, d);
		this.role = role;
		// TODO Auto-generated constructor stub
		TextField = new GenericTextField();
		TextField.setTooltip("Wait in ticks");
		TextField.setText(Integer.toString(role.getTicks()));
		this.mscBody.addChild(TextField);
		this.mscBody.addChild(new GenericContainer());
		this.mscBody.addChild(new ConfigSaveApply(this));
	}

  @Override
  public void save() {
    int ticks = 0;
    try {
      ticks = Integer.parseInt(this.TextField.getText());
    } catch (NumberFormatException e) {
      return;
    }
    role.setTicks(ticks);
  }

}
