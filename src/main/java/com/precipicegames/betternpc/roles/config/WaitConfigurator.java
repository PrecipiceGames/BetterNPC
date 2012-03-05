package com.precipicegames.betternpc.roles.config;

import java.util.Stack;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.NPC;
import com.precipicegames.betternpc.roles.WaitRole;
import com.precipicegames.betternpc.widgets.ConfigDialog;
import com.precipicegames.betternpc.widgets.Dialog;

public class WaitConfigurator extends ConfigDialog {

	private WaitRole role;

	public WaitConfigurator(WaitRole role, SpoutPlayer p, NPC npc, Stack<Dialog> d) {
		super(p, npc, d);
		this.role = role;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
