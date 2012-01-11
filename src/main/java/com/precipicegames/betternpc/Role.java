package com.precipicegames.betternpc;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface Role {
	public void startRole(Player p, NPC npc, RoleList parent);
	public String getName();
	public String getDetails();
	public ConfigurationSection getConfig();
	public void loadConfig(ConfigurationSection config);
	public ConfigDialog getConfigureDialog();
}
