package com.precipicegames.betternpc.roles;

import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.roles.config.BlankConfigurator;
import com.precipicegames.tutorialsign.widgets.Dialog;

public class EndExecutionRole implements Role {

	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
	}

	public void startRole(Player p, NPC npc, Stack<Role> s) {
	}

	public String getName() {
		return "End Execution";
	}

	public String getDetails() {
		return "";
	}

	public ConfigurationSection getConfig() {
		return new MemoryConfiguration();
	}

	public void loadConfig(ConfigurationSection config) {
	}

	public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		// TODO Auto-generated method stub
		return new BlankConfigurator(p,npc,d);
	}

}
