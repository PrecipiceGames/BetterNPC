package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.MessageConfigurator;
import com.precipicegames.betternpc.widgets.Dialog;

public class MessageRole implements Role {
  private String text = "Default text";

  public void handleFinished(Player p, NPC npc, Stack<Role> s) {
    Role r = s.pop();
    r.handleFinished(p, npc, s);
  }

  public void startRole(Player p, NPC npc, Stack<Role> s) {
    if(p != null) {
      p.sendMessage(getText());
    }
    handleFinished(p, npc, s);
  }

  public String getName() {
    // TODO Auto-generated method stub
    return "Messege Role";
  }

  public String getDetails() {
    // TODO Auto-generated method stub
    return text;
  }

  public ConfigurationSection getConfig() {
    MemoryConfiguration config = new MemoryConfiguration();
    config.set("message", this.text);
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    this.text = config.getString("message", "Default text");
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
    // TODO Auto-generated method stub
    return new MessageConfigurator(this, p, npc, d);
  }

}
