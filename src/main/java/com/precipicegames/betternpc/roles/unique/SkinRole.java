package com.precipicegames.betternpc.roles.unique;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.UniqueRole;
import com.precipicegames.betternpc.roles.config.SkinConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;

public class SkinRole implements Role, UniqueRole {
  private String url;

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    handleFinished(p,npc,s); //essentially do nothing
  }
  
  public void setSkin(NPC n) {
    if(url != null && !url.isEmpty()) {
      n.humannpc.getSpoutPlayer().setSkin(url);
    }
  }

  public String getName() {
    // TODO Auto-generated method stub
    return "Skin Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return "";
  }

  public ConfigurationSection getConfig() {
    MemoryConfiguration config = new MemoryConfiguration();
    if(url != null && !url.isEmpty()) {
      config.set("skinURL",url);
    }
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    if(config.isString("skinURL")) {
      url = config.getString("skinURL");
    }
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    return new SkinConfigurator(this,p,npc,d);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

}
