package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.LocationConfigSerializer;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.LookConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;

public class LookRole implements Role {
  private boolean targetplayer = true;
  private Location looklocation;

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if (targetplayer) {
      npc.humannpc.lookAtPoint(p.getLocation());
    } else {
      npc.humannpc.lookAtPoint(looklocation);
    }
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public String getName() {
    return "Look Role";
  }

  public String getDetails() {
    return "Looks at a player or location";
  }

  public ConfigurationSection getConfig() {
    MemoryConfiguration config = new MemoryConfiguration();
    config.set("target-player", this.targetplayer);
    if (this.looklocation != null) {
      config.set("location",
          LocationConfigSerializer.toConfig(this.looklocation));
    }
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    this.targetplayer = config.getBoolean("target-player", true);
    if (config.isConfigurationSection("location")) {
      looklocation = LocationConfigSerializer.fromConfig(config,
          BukkitPlugin.plugin.getServer());
    }
  }

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    return new LookConfigurator(this, p, npc, d);
  }

  public boolean isTargetplayer() {
    return targetplayer;
  }

  public void setTargetplayer(boolean targetplayer) {
    this.targetplayer = targetplayer;
  }

  public Location getLooklocation() {
    return looklocation;
  }

  public void setLooklocation(Location looklocation) {
    this.looklocation = looklocation;
  }

}
