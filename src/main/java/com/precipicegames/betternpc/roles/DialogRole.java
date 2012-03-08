package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.DialogConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;

public class DialogRole implements Role {
  private class dialogTimeoutEvent implements Runnable {
    Player p;
    NPC npc;
    Stack<Role> s;

    public void run() {
      handleFinished(p, npc, s);
    }
  }

  private String text;
  private int time;
  private boolean async;

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if(p == null) {
      this.handleFinished(p, npc, s);
      return;
    }
    BukkitPlugin.dialog.displayDialog(p, this);
    dialogTimeoutEvent timeoutevent = new dialogTimeoutEvent();
    timeoutevent.p = p;
    timeoutevent.npc = npc;
    timeoutevent.s = s;
    if (!async) {
      BukkitPlugin.plugin.getServer().getScheduler()
          .scheduleSyncDelayedTask(BukkitPlugin.plugin, timeoutevent, time);
    } else {
      this.handleFinished(p, npc, s);
    }
  }

  public String getName() {
    // TODO Auto-generated method stub
    return "Dialog Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return getText();
  }

  public ConfigurationSection getConfig() {
    MemoryConfiguration config = new MemoryConfiguration();
    config.set("text", getText());
    config.set("time", getTime());
    config.set("async", async);
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    setText(config.getString("text", "Default text"));
    time = config.getInt("time", 60);
    async = config.getBoolean("async", false);
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public boolean isAsync() {
    return async;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    return new DialogConfigurator(this, p, npc, d);
  }

}
