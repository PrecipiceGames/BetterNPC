package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.AudioRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;

public class AudioConfigurator extends ConfigDialog {

  private AudioRole role;
  private GenericTextField path;

  public AudioConfigurator(AudioRole role, SpoutPlayer p, NPC npc,
      Stack<Dialog> d) {
    super(p, npc, d);
    this.role = role;
    path = new GenericTextField();
    path.setMaximumCharacters(500);
    path.setTooltip("URL for sound file, ogg only");
    this.mscBody.addChild(path);
    this.mscBody.addChild(new ConfigSaveApply(this));
  }

  @Override
  public void save() {
    role.setAudioUrl(path.getText());
  }

}
