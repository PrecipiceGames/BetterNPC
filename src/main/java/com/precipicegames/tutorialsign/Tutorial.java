package com.precipicegames.tutorialsign;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Tutorial {
	ConfigurationSection conf;
	String name;
	HashMap<Player, TutorialWidget> tutorialWidgets;
	HashMap<Player, Boolean> guiEnabled;
	Widget tutorialWidget;
	enum TutorialState {
		PLAYING,
		STOPED,
	}
	public class dialogEvent implements Runnable
	{
		Player player;
		boolean halt = false;
		String text;
		dialogEvent(Player p, String s)
		{
			player = p;
			text = s;
		}
		public void run() {
			if(halt == true)
				return;
			SpoutPlayer sp = SpoutManager.getPlayer(player);
			
			//Construct SpoutCraftTutorial UI
			if(sp.isSpoutCraftEnabled() && guiEnabled.get(player).booleanValue())
			{
				if(!tutorialWidgets.containsKey(player))
					constructui(sp);
				tutorialWidgets.get(player).setText(text);
			}
		}
	}
	HashMap <Player, TutorialState> tutStates;
	public Tutorial(String name, ConfigurationSection config)
	{
		conf = config;
		this.name = name;
	}
	public void stop(Player p)
	{
		SpoutPlayer sp = SpoutManager.getPlayer(p);
		this.tutStates.put(p, TutorialState.STOPED);
		if(sp.isSpoutCraftEnabled())
		{
			SpoutManager.getSoundManager().stopMusic(sp);
		}
	}
	public void constructui(SpoutPlayer p) {
		
	}
	public void destructui(SpoutPlayer p) {
		
	}
}
