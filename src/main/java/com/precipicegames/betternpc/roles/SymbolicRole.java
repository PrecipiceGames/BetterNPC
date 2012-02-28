package com.precipicegames.betternpc.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleList;
import com.precipicegames.tutorialsign.widgets.Dialog;

public class SymbolicRole implements Role {
	private ArrayList<Integer> link;
	public SymbolicRole() {
		link = new ArrayList<Integer>();
	}
	public Role followLink(NPC npc) {
		Iterator<Integer> itr = link.iterator();
		Role activerole = null;
		if(itr.hasNext())
		{
			activerole = npc.getRole(itr.next());
		}
		while(itr.hasNext()) {
			if(activerole instanceof RoleList) {
				RoleList activerolelist = (RoleList)activerole;
				activerole = activerolelist.getRole(itr.next());
			}
		}
		return activerole;
	}
	public void startRole(Player p, NPC npc, Stack<Role> s) {
		Role activerole = this.followLink(npc);
		if(activerole != null) {
			s.push(this);
			activerole.startRole(p, npc, s);
		} else {
			Role r = s.pop();
			r.handleFinished(p, npc, s);
		}
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConfigurationSection getConfig() {
		// TODO Auto-generated method stub
		MemoryConfiguration config = new MemoryConfiguration();
		config.set("node", getLinkString());
		return config;
	}
	public String getLinkString() {
		String s = "";
		for(Integer I : link) {
			if(!s.isEmpty()) {
				s += ":";
			}
			s += I.toString();
		}
		return s;
	}
	public void setLink(String linkPath) {
		link.clear();
		String[] nodes = linkPath.split(":");
		for(String s : nodes) {
			if(s.isEmpty()) {
				continue;
			}
			int i = Integer.parseInt(s);
			link.add(i);
		}
	}
	public void loadConfig(ConfigurationSection config) {
		String nodelist = config.getString("node","");
		this.setLink(nodelist);
	}
	public void handleFinished(Player p, NPC npc, Stack<Role> s) {
		Role r = s.pop();
		r.handleFinished(p, npc, s);
	}
	public Dialog getConfigureDialog(SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		// TODO Auto-generated method stub
		return null;
	}
}
