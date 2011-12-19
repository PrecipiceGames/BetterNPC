package com.precipicegames.tutorialsign;

import org.bukkit.configuration.MemoryConfiguration;

public class DefaultConfiguration extends MemoryConfiguration {
	public DefaultConfiguration()
	{
		super();
		this.addDefault("Auto-generated", true);
	}
}
