package com.precipicegames.tutorialsign;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.precipicegames.tutorialsign.dialogs.Dialog;
import com.precipicegames.tutorialsign.dialogs.DialogProperties;

public class DialogManager {
	private DialogBehaviour defaultBehaviour;
	private HashMap<Player,DialogBehaviour> playerBehaviours;
	private HashMap<Player,Dialog> playerDialogs;
	private HashMap<Player,DialogProperties> dProperties;
	public enum DialogBehaviour {
		TEXT,
		UI
	}
	public void show(Player p, DialogProperties dp)
	{
		
	}
	public void setBehaviour(Player p, DialogBehaviour behave)
	{
		playerBehaviours.put(p, behave);
	}
	public void setDefaultBehaviour(DialogBehaviour behave)
	{
		defaultBehaviour = behave;
	}
}
