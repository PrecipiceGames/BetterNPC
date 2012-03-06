package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.unique.SkinRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;

public class SkinConfigurator extends ConfigDialog {

  private SkinRole role;
  private GenericTextField url;

  public SkinConfigurator(SkinRole r ,SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    super(p, npc, d);
    this.role = r;
    url = new GenericTextField();
    url.setMaximumLines(1);
    url.setMaximumCharacters(400);
    if(r.getUrl() != null) {
      url.setText(r.getUrl());
    }
    url.setTooltip("URL for this NPC's skin");
    this.mscBody.addChild(url);
    this.mscBody.addChild(new ConfigSaveApply(this));
  }

  @Override
  public void save() {
    role.setUrl(url.getText());
    role.setSkin(getNpc());
  }

}
