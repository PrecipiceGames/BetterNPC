package com.precipicegames.betternpc;

import java.util.HashMap;
import java.util.Stack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.roles.EndExecutionRole;
import com.precipicegames.betternpc.roles.unique.OnDeathRole;
import com.precipicegames.betternpc.roles.unique.RightClickRole;

public class EventDispatcher implements Listener {
  
  private class DEntry {
    long time;
    private Player player;
    private DEntry(Player p, long time) {
      this.time  = time;
      this.player = p;
    }
    private Player getPlayer() {
      if(System.currentTimeMillis()-this.time < 1000 * 20) {
        return this.player;
      } else {
        return null;
      }
    }
  }
  
  private HashMap<Entity,DEntry> dTable = new HashMap<Entity,DEntry>(); 

  private BukkitPlugin plugin;

  public EventDispatcher(BukkitPlugin p) {
    this.plugin = p;
  }
  
  private NPC getNPC(Entity e) {
    if (plugin.npcman.isNPC(e)) {
      String id = plugin.npcman.getNPCIdFromEntity(e);
      if(id == null) {
        return null;
      }
      NPC n = plugin.LoadedNPC.get(id);
      return n;
    } else {
      return null;
    }
  }
  
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    NPC n = this.getNPC(event.getEntity());
    if(n == null) {
      return;
    }
    Player cause = null;
    DEntry entry = this.dTable.get(event.getEntity());
    if(entry != null) {
      cause = entry.getPlayer();
    }
    OnDeathRole role = (OnDeathRole) n.getRole(OnDeathRole.class);
    if (role == null) {
      return;
    }
    Stack<Role> s = new Stack<Role>();
    s.push(new EndExecutionRole());
    role.startRole(cause, n, s);
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if(event instanceof EntityDamageByEntityEvent) {
     EntityDamageByEntityEvent devent = (EntityDamageByEntityEvent) event;
     if(devent.getDamager() instanceof Player) {
       DEntry entry = new DEntry((Player)devent.getDamager(),System.currentTimeMillis());
       this.dTable.put(event.getEntity(), entry);
     }
    }
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    NPC n = this.getNPC(event.getRightClicked());
    if (n != null) {
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
