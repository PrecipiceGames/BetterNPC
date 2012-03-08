package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.LookConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;
import com.topcat.npclib.pathing.NPCPath;
import com.topcat.npclib.pathing.PathReturn;

public class MoveRole extends LookRole implements Role {
  
  private class pathfinishedCB implements PathReturn {
    public void run(NPCPath arg0) {
      
    }
  }

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if(p != null) {
      if (this.isTargetplayer()) {
        Location loc = p.getLocation().clone();
        npc.humannpc.walkTo(loc);
        //npc.humannpc.moveTo(loc);
      } else {
        npc.humannpc.walkTo(this.getLooklocation());
      }
    }
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public String getName() {
    // TODO Auto-generated method stub
    return "Move Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return "Moves NPC to location";
  }

  public ConfigurationSection getConfig() {
    // TODO Auto-generated method stub
    return super.getConfig();
  }

  public void loadConfig(ConfigurationSection config) {
    super.loadConfig(config);
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    return new LookConfigurator(this, p, npc, d);
  }

}
