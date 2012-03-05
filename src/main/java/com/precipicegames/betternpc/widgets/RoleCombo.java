package com.precipicegames.betternpc.widgets;

import java.util.ArrayList;

import org.getspout.spoutapi.gui.GenericComboBox;

import com.precipicegames.betternpc.RoleFactory;

public class RoleCombo extends GenericComboBox {
  private ArrayList<String> displayedTypes;

  public RoleCombo(boolean deep) {
    super();
    displayedTypes = new ArrayList<String>();
    for (String type : RoleFactory.getRegisteredTypes()) {
      String subdata = "";
      boolean unique = RoleFactory.isUnique(type);
      boolean list = RoleFactory.isUnique(type);
      if (unique) {
        subdata += "Unique ";
      }
      if (list) {
        subdata += "SubList ";
      }
      if (!deep && unique) {
        continue;
      }
      displayedTypes.add(type);
    }
    this.setItems(displayedTypes);
  }
}
