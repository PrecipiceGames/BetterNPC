package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.BlankConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;

public class RespawnRole implements Role {

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if(npc != null) {
      npc.respawn();
    }
    handleFinished(p,npc,s);
  }

  public String getName() {
    // TODO Auto-generated method stub
    return "Respawn Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return "";
  }

  public ConfigurationSection getConfig() {
    // TODO Auto-generated method stub
    return new MemoryConfiguration();
  }

  public void loadConfig(ConfigurationSection config) {
    
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    return new BlankConfigurator(p,npc,d);
  }

}
