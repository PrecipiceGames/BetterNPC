package com.precipicegames.betternpc;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface Role extends RoleFinishHandler {
	public void startRole(Player p, NPC npc, Stack<Role> s);
	public String getName();
	public String getDetails();
	public ConfigurationSection getConfig();
	public void loadConfig(ConfigurationSection config);
	public ConfigDialog getConfigureDialog();
}
