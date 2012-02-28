package com.precipicegames.tutorialsign.widgets;

import org.bukkit.Location;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Dialog extends GenericContainer {
	protected GenericContainer mscBody;
	public final int WIDTH = 300;
	public final int HEIGHT = 100;
	private Location location;
	private double range;
	private long time;

	public Dialog()
	{
		super();
		GenericGradient bground = new GenericGradient();
		GenericGradient fground = new GenericGradient();
		GenericContainer msc = new GenericContainer();
		
		GenericContainer mscWindow = new GenericContainer();
		mscBody = new GenericContainer();
		mscWindow.setLayout(ContainerType.VERTICAL);
		mscWindow.setMargin(5);
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
		msc.shiftYPos(-HEIGHT/2);
		
		msc.setLayout(ContainerType.OVERLAY);
		msc.setAlign(WidgetAnchor.CENTER_CENTER);
		msc.setAnchor(WidgetAnchor.CENTER_CENTER);
		msc.insertChild(0,mscWindow);
		msc.insertChild(1,bground);
		msc.insertChild(2, fground);
		this.addChild(msc);
	}
}
