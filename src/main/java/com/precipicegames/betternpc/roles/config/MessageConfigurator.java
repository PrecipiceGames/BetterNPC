package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.MessageRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;

public class MessageConfigurator extends ConfigDialog {

  private GenericTextField text;
  private MessageRole role;

  public MessageConfigurator(MessageRole role, SpoutPlayer p, NPC npc,
      Stack<Dialog> d) {
    super(p, npc, d);
    this.role = role;
    text = new GenericTextField();
    text.setTooltip("URL for sound file, ogg only");
    text.setText(role.getText());
    this.mscBody.addChild(text);
    this.mscBody.addChild(new GenericContainer());
    this.mscBody.addChild(new ConfigSaveApply(this));
  }

  @Override
  public void save() {
    role.setText(text.getText());
  }
}
