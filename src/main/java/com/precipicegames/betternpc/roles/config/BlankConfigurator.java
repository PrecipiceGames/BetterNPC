package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.Dialog;

public class BlankConfigurator extends ConfigDialog {
  private class closeB extends GenericButton {
    public void onButtonClick(ButtonClickEvent event) {
      close();
    }
  }

  public BlankConfigurator(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    super(p, npc, d);
    this.mscBody.addChild(new GenericLabel("No Settings!"));
    this.mscBody.addChild(new closeB().setText("Close"));
  }

  @Override
  public void save() {
  }

}
