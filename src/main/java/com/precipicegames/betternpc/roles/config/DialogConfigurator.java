package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.event.screen.SliderDragEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericSlider;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.DialogRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.ConfigSaveApply;
import com.precipicegames.betternpc.widgets.Dialog;

public class DialogConfigurator extends ConfigDialog {
  private class Slider extends GenericSlider {
    public void onSliderDrag(SliderDragEvent event) {
      float pos = event.getNewPosition();
      displaytime.setText(Integer.toString((int) (pos * 200)));
    }
  }

  private GenericTextField txt;
  private GenericLabel displaytime;
  private GenericCheckBox async;
  private DialogRole role;

  public DialogConfigurator(DialogRole role, SpoutPlayer p, NPC npc,
      Stack<Dialog> d) {
    super(p, npc, d);
    this.role = role;
    GenericContainer buttonbox = new ConfigSaveApply(this);

    txt = new GenericTextField();
    txt.setMaximumLines(4);
    txt.setMaximumCharacters(800);
    txt.setText(role.getText());
    async = new GenericCheckBox();
    async.setChecked(role.isAsync());
    async.setText("Async");

    GenericContainer sliderbox = new GenericContainer();
    sliderbox.setLayout(ContainerType.HORIZONTAL);
    sliderbox.setTooltip("Ticks");
    sliderbox.addChild(new Slider());
    displaytime = new GenericLabel(Integer.toString(role.getTime()));
    sliderbox.addChild(displaytime);
    this.mscBody.addChild(txt);
    this.mscBody.addChild(sliderbox);
    this.mscBody.addChild(async);
    this.mscBody.addChild(new GenericContainer());
    this.mscBody.addChild(buttonbox);
  }

  public void save() {
    role.setAsync(this.async.isChecked());
    role.setText(this.txt.getText());
    role.setTime(Integer.parseInt(this.displaytime.getText()));
  }

}
