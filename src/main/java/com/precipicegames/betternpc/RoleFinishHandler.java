package com.precipicegames.betternpc;

import java.util.Stack;

import org.bukkit.entity.Player;

public interface RoleFinishHandler {
  public void handleFinished(Player p, NPC npc, Stack<Role> s);
}
