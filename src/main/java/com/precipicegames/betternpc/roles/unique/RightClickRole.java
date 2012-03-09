package com.precipicegames.betternpc.roles.unique;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Stack;

import org.bukkit.entity.Player;

import com.precipicegames.betternpc.BukkitPlugin;
import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.UniqueRole;
import com.precipicegames.betternpc.roles.SymbolicRole;

public class RightClickRole extends SymbolicRole implements Role, UniqueRole {
  private HashMap<String,WeakReference<Stack<Role>>> locks = new HashMap<String,WeakReference<Stack<Role>>>();
  
  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if(p != null) {
      WeakReference<Stack<Role>> wr = locks.get(p.getName());
      if(wr != null && wr.get() != null) {
        BukkitPlugin.dialog.skip(p, wr.get());
        super.handleFinished(p, npc, s);
        return;
      }
      locks.put(p.getName(), new WeakReference<Stack<Role>>(s));
    }
    super.startRole(p, npc, s);
  }
  
  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    if(p != null) {
      WeakReference<Stack<Role>> wr = locks.get(p.getName());
      if(wr != null) {
        locks.remove(p.getName());
      }
    }
    super.handleFinished(p, npc, s);
  }
  
  public String getName() {
    // TODO Auto-generated method stub
    return "Right Click Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return "Linked with";
  }

}
