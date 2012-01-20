package com.precipicegames.betternpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;

import com.topcat.npclib.entity.HumanNPC;

public class NPC implements RoleList {
	private ArrayList<Role> roles;
	private BukkitPlugin plugin;
	public HumanNPC humannpc;
	private Location spawnloc;
	private String name;
	private String id;
	private ConfigurationSection config;

	private static String getRandomId(BukkitPlugin p){
		
		String id = Integer.toString((int) Math.round(Math.random()*10000));
		//Get an unused random ID
		while(p.npcman.getNPC(id) != null) {
			id = Integer.toString((int) Math.round(Math.random()*10000));
		}
		return id;
	}
	
	public NPC(String displayname, BukkitPlugin p, ConfigurationSection cs){
		this(displayname,p,NPC.getRandomId(p),cs); 
	}
	
	public NPC(String displayname, BukkitPlugin p, String id, ConfigurationSection cs){
		roles = new ArrayList<Role>();
		plugin = p;
		name = "Default name";
		this.id = id;
		this.config = cs.getConfigurationSection(id);
		load();
		respawn();
	}
	
	private void load() {
		name = config.getString("name", "Default Name");
		ConfigurationSection loc;
		if(config.isConfigurationSection("location")) {
			loc = config.getConfigurationSection("location");
		} else {
			loc = new MemoryConfiguration();
		}
		double x = loc.getDouble("x", 0.0);
		double y = loc.getDouble("y", 0.0);
		double z = loc.getDouble("x", 0.0);
		float yaw = (float) loc.getDouble("yaw",0.0);
		float pitch = (float) loc.getDouble("pitch",0.0);
		String world = loc.getString("world", plugin.getServer().getWorlds().get(0).getName());
		this.spawnloc = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
		
		//Clear out old entries
		while(getRoleCount() >= 1) {
			//This will always delete an element
			this.delRole(0);
		}
		if(config.isList("roles")) {
			List<?> l = config.getList("roles");
			for(Object o: l) {
				if(o instanceof ConfigurationSection) {
					ConfigurationSection s = (ConfigurationSection) o;
					if(s.isString("type")) {
						Role r = null;
						try {
							r = RoleFactory.newRole(s.getString("type"),s);
						} catch (Exception e) {
							invalid();
						}
						addRole(r);
					}
				}
			}
		}
	}
	private void invalid() {
		
	}

	public Location getCurrentLocation() {
		if(humannpc != null) {
			return humannpc.getBukkitEntity().getLocation();
		} else {
			return spawnloc;
		}
	}
	public void respawn() {
		if(humannpc == null) {
			return;
		}
		if(humannpc.getBukkitEntity().isDead()) {
			humannpc = (HumanNPC) plugin.npcman.spawnHumanNPC(name, spawnloc, id);
		}
	}
	public Role[] getRoles() {
		return roles.toArray(new Role[0]);
	}

	public int getRoleCount() {
		return roles.size();
	}

	public int addRole(Role role) {
		if(role instanceof UniqueRole)	{
			if(this.getRole(((UniqueRole)role).getClass()) == null) {
				return -1;
			}
		}
		roles.add(role);
		return roles.size()-1;
	}

	public void delRole(Role role) {
		roles.remove(role);
	}

	public void delRole(int index) {
		roles.remove(index);
	}

	public Role getRole(int index) {
		return roles.get(index);
	}

	public Role[] getRoles(Class<? extends Role> roletype) {
		ArrayList<Role> ret = new ArrayList<Role>();
		for(Role r : roles) {
			if(roletype.isInstance(r)) {
				ret.add(r);
			}
		}
		return ret.toArray(new Role[0]);
	}

	public Role getRole(Class<? extends UniqueRole> roletype) {
		for(Role r : roles) {
			if(roletype.isInstance(r)) {
				return r;
			}
		}
		return null;
	}
	public Iterator<Role> getRoleIterator() {
		return roles.iterator();
	}
	public void handleFinished(Player p, NPC n,Role role) {
		// TODO Auto-generated method stub
	}
	public void scheduleEvent(Runnable e) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, e);
	}
}
