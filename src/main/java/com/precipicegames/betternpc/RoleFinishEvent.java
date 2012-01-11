package com.precipicegames.betternpc;

import org.bukkit.entity.Player;

public class RoleFinishEvent implements Runnable{
	private Role role;
	private NPC npc;
	private Player player;
	private RoleList rolelist;

	public RoleFinishEvent(Player p, NPC npc, Role role, RoleList list){
		this.player = p;
		this.npc = npc;
		this.role = role;
		this.rolelist = list;
	}

	public void run() {
		if(rolelist == null) {
			return;
		}
		rolelist.handleFinished(player, npc, role);
	}
}
