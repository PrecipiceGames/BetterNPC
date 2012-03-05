package com.precipicegames.betternpc.widgets;

import org.bukkit.Location;
import org.bukkit.World;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTextField;

import com.precipicegames.betternpc.NPC;

public class LocationWidget extends GenericContainer {
  GenericTextField boxX;
  GenericTextField boxZ;
  GenericTextField boxY;
  GenericTextField world;
  GenericTextField yaw;
  GenericTextField pitch;
  Location loc = null;
  private NPC npc;
  public LocationWidget(NPC n, boolean showPoint) {
    super();
    this.npc = n;
    boxX = new GenericTextField();
    boxY = new GenericTextField();
    boxZ = new GenericTextField();
    yaw = new GenericTextField();
    pitch = new GenericTextField();
    world = new GenericTextField();
    
    // Tooltips
    boxX.setTooltip("X location");
    boxY.setTooltip("Y location");
    boxZ.setTooltip("Z location");
    yaw.setTooltip("Yaw");
    pitch.setTooltip("Pitch");
    world.setTooltip("World");
    this.addChildren(boxX,boxY,boxZ);
    if(showPoint) {
      this.addChildren(yaw,pitch);
    }
  }

  public LocationWidget(NPC n, Location l, boolean showWorld, boolean showPoint) {
    this(n, showPoint);
    setLocation(l);
    showWorld(showWorld);
  }

  public void setLocation(Location l) {
    loc = l;
    boxX.setText(Double.toString(l.getX()));
    boxY.setText(Double.toString(l.getY()));
    boxZ.setText(Double.toString(l.getZ()));
    yaw.setText(Float.toString(l.getYaw()));
    pitch.setText(Float.toString(l.getPitch()));
    world.setText(loc.getWorld().getName());
  }
  
  public Location getLocation() throws IllegalArgumentException {
    double x,y,z;
    float yaw = 0;
    float pitch = 0;
    World world = null;
    try {
    x = Double.parseDouble(boxX.getText());
    y = Double.parseDouble(boxY.getText());
    z = Double.parseDouble(boxZ.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException();
    }
    try {
      yaw = Float.parseFloat(this.yaw.getText());
      pitch = Float.parseFloat(this.pitch.getText());
    } catch (Exception e) {}
    try {
      for(World w : npc.getPlugin().getServer().getWorlds()) {
        if(w.getName().equalsIgnoreCase(this.world.getText())) {
         world = w; 
        }
      }
      if(world == null) {
        world = npc.getCurrentLocation().getWorld();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
    return new Location(world, x, y, z, yaw, pitch);
  }
  
  private void showWorld(boolean show) {
    boolean found = false;
    for(Widget child : this.getChildren()) {
      if(child == this.world) {
        found = true;
        break;
      }
    }
    if(show && !found) {
      this.addChild(this.world);
    }
    if(!show && found) {
      this.removeChild(world);
    }
  }
}
