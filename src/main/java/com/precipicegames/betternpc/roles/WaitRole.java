package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.widgets.Dialog;

public class WaitRole implements Role {
  int ticks = 0;

  private class finished implements Runnable {
    private Player player;
    private NPC npc;
    private Stack<Role> stack;

    public finished(Player p, NPC npc, Stack<Role> s) {
      player = p;
      this.npc = npc;
      stack = s;
    }

    public void run() {
      handleFinished(player, npc, stack);
    }
  }

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    finished task = new finished(p, npc, s);
    npc.getPlugin().getServer().getScheduler()
        .scheduleSyncDelayedTask(npc.getPlugin(), task, ticks);
  }

  public String getName() {
    return "Wait Role";
  }

  public String getDetails() {
    return "Waiting for " + this.ticks + " ticks";
  }

  public ConfigurationSection getConfig() {
    ConfigurationSection config = new MemoryConfiguration();
    config.set("ticks", ticks);
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    ticks = config.getInt("ticks", 0);
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    // TODO Auto-generated method stub
    return null;
  }
}
