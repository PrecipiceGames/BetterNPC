package com.precipicegames.betternpc;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.roles.DialogRole;

public class DialogManager {
	private HashMap<Player, Widget> dialogindex;
	private BukkitPlugin plugin;
	int WIDTH = 300;
	int HEIGHT = 100;
	DialogManager(BukkitPlugin p) {
		plugin = p;
	}
	private class displayTimer implements Runnable {
		private Widget widget;
		private Player player;
		public displayTimer(Widget w, Player p) {
			widget = w;
			player = p;
		}
		public void run() {
			Widget olddialog = dialogindex.get(player);
			if(olddialog != null && olddialog == widget) {
				SpoutPlayer sp = SpoutManager.getPlayer(player);
				sp.getMainScreen().removeWidget(olddialog);
			}
		}
	}
	public void displayDialog(Player p, DialogRole d) {
		SpoutPlayer sp = SpoutManager.getPlayer(p);
		if(!sp.isSpoutCraftEnabled()) {
			return;
		}
		int displayticks = d.getTime();

		Widget w = createDialogWidget(d);
		Widget olddialog = dialogindex.get(p);
		if(olddialog != null) {
			sp.getMainScreen().removeWidget(olddialog);
		}
		dialogindex.put(p, w);
		sp.getMainScreen().attachWidget(plugin, w);
		if(displayticks >= 0) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new displayTimer(w, p), displayticks);
		}
	}
	private Widget createDialogWidget(DialogRole d) {

		GenericGradient bground = new GenericGradient();
		GenericGradient fground = new GenericGradient();
		GenericContainer msc = new GenericContainer();
		
		GenericContainer mscWindow = new GenericContainer();
		GenericContainer mscBody = new GenericContainer();
		mscWindow.setLayout(ContainerType.VERTICAL);
		mscWindow.setMargin(5);
		GenericLabel mscTestBox = new GenericLabel();
		mscTestBox.setMaxWidth(WIDTH - 10);
		mscTestBox.setMaxHeight(HEIGHT - 10);
		mscTestBox.setText(d.getText());
		
		mscBody.addChild(mscTestBox);
		bground.setHeight(HEIGHT);
		bground.setWidth(WIDTH);
		bground.setColor(new Color(127,127,127));

		fground.setColor(new Color(0,0,0));
		fground.setPriority(RenderPriority.High);
		
		fground.setMargin(1);
		fground.setWidth(WIDTH);
		fground.setHeight(HEIGHT);
		//bground.setFixed(true);
		bground.setPriority(RenderPriority.Highest);
		mscWindow.addChild(mscBody);
		
		msc.setHeight(HEIGHT);
		msc.setWidth(WIDTH);
		msc.shiftXPos(-WIDTH/2);
		msc.shiftYPos(-HEIGHT*(2/3));
		
		msc.setLayout(ContainerType.OVERLAY);
		msc.setAlign(WidgetAnchor.CENTER_CENTER);
		msc.setAnchor(WidgetAnchor.CENTER_CENTER);
		msc.insertChild(0,mscWindow);
		msc.insertChild(1,bground);
		msc.insertChild(2, fground);
		return msc;
	}
}
