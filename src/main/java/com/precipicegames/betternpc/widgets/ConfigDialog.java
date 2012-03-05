package com.precipicegames.betternpc.widgets;

import java.util.Stack;

import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;

public abstract class ConfigDialog extends Dialog {
  private SpoutPlayer player;
  private Stack<Dialog> stack;
  private NPC npc;

  public NPC getNpc() {
    return npc;
  }

  public void setNpc(NPC npc) {
    this.npc = npc;
  }

  public ConfigDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    player = p;
    stack = d;
    this.npc = npc;
  }

  public abstract void save();

  public void close() {
    if (stack == null) {
      player.getMainScreen().closePopup();
      return;
    }
    if (!stack.isEmpty()) {
      GenericPopup popup = new GenericPopup();
      popup.attachWidget(BukkitPlugin.plugin, stack.pop());
      player.getMainScreen().closePopup();
      player.getMainScreen().attachPopupScreen(popup);
    } else {
      player.getMainScreen().closePopup();
    }
  }

  public SpoutPlayer getPlayer() {
    return player;
  }

  public void setPlayer(SpoutPlayer player) {
    this.player = player;
  }

  public Stack<Dialog> getStack() {
    return stack;
  }

  public void setStack(Stack<Dialog> stack) {
    this.stack = stack;
  }
}
