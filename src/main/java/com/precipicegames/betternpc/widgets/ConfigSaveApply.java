package com.precipicegames.betternpc.widgets;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;

public class ConfigSaveApply extends GenericContainer {
  ConfigDialog configD;

  private class SaveButton extends GenericButton {
    public SaveButton() {
      super("Save");
    }

    public void onButtonClick(ButtonClickEvent event) {
      configD.save();
      configD.close();
    }
  }

  private class CloseButton extends GenericButton {
    public CloseButton() {
      super("Close");
    }

    public void onButtonClick(ButtonClickEvent event) {
      configD.close();
    }
  }

  public ConfigSaveApply(ConfigDialog dialog) {
    this.configD = dialog;
    setLayout(ContainerType.HORIZONTAL);
    addChild(new SaveButton());
    addChild(new CloseButton());
  }

}
