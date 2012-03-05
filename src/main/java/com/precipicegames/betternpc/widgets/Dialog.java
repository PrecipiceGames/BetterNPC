package com.precipicegames.betternpc.widgets;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class Dialog extends GenericContainer {
  protected GenericContainer mscBody;
  public final int WIDTH = 300;
  public final int HEIGHT = 200;

  public Dialog() {
    super();
    GenericGradient bground = new GenericGradient();
    GenericGradient fground = new GenericGradient();

    GenericContainer mscWindow = new GenericContainer();
    mscBody = new GenericContainer();
    mscWindow.setLayout(ContainerType.VERTICAL);
    mscWindow.setMargin(5);
    mscWindow.setMinWidth(WIDTH - 10);
    mscWindow.setMinHeight(HEIGHT - 10);
    bground.setHeight(HEIGHT);
    bground.setWidth(WIDTH);
    bground.setColor(new Color(127, 127, 127));

    fground.setColor(new Color(0, 0, 0));
    fground.setPriority(RenderPriority.High);

    fground.setMargin(1);
    fground.setWidth(WIDTH);
    fground.setHeight(HEIGHT);
    // bground.setFixed(true);
    bground.setPriority(RenderPriority.Highest);
    mscWindow.addChild(mscBody);

    this.setHeight(HEIGHT);
    this.setWidth(WIDTH);
    this.shiftXPos(-WIDTH / 2);
    this.shiftYPos(-HEIGHT / 2);

    this.setLayout(ContainerType.OVERLAY);
    this.setAlign(WidgetAnchor.CENTER_CENTER);
    this.setAnchor(WidgetAnchor.CENTER_CENTER);
    this.insertChild(0, mscWindow);
    this.insertChild(1, bground);
    this.insertChild(2, fground);
  }
}
