package com.precipicegames.betternpc;

import java.util.Stack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.roles.EndExecutionRole;
import com.precipicegames.betternpc.roles.unique.RightClickRole;

public class EventDispatcher implements Listener {

  private BukkitPlugin plugin;

  public EventDispatcher(BukkitPlugin p) {
    this.plugin = p;
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (plugin.npcman.isNPC(event.getRightClicked())) {
      if (event.isCancelled()) {
        return;
      }

      String id = plugin.npcman.getNPCIdFromEntity(event.getRightClicked());
      NPC n = plugin.LoadedNPC.get(id);
      System.out.println("clicked an entity");
      if (n == null) {
        return;
      }

      if (plugin.editors.contains(event.getPlayer())) {
        SpoutPlayer p = SpoutManager.getPlayer(event.getPlayer());
        n.configure(p);
      } else {
        RightClickRole role = (RightClickRole) n.getRole(RightClickRole.class);
        if (role == null) {
          return;
        }
        Stack<Role> s = new Stack<Role>();
        s.push(new EndExecutionRole());
        role.startRole(event.getPlayer(), n, s);
      }
    }
  }
}
