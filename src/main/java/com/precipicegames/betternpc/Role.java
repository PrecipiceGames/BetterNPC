package com.precipicegames.betternpc;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.widgets.Dialog;

public interface Role extends RoleFinishHandler {
  public void startRole(Player p, NPC npc, Stack<Role> s);

  public String getName();

  public String getDetails();

  public ConfigurationSection getConfig();

  public void loadConfig(ConfigurationSection config);

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d);
}
