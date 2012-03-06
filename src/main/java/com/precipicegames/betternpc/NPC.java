package com.precipicegames.betternpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.betternpc.roles.unique.SkinRole;
import com.precipicegames.betternpc.widgets.Dialog;
import com.topcat.npclib.entity.HumanNPC;

public class NPC implements RoleList {
  private ArrayList<Role> roles;
  private BukkitPlugin plugin;

  public BukkitPlugin getPlugin() {
    return plugin;
  }

  public HumanNPC humannpc;

  public HumanNPC getHumannpc() {
    return humannpc;
  }

  private Location spawnloc;
  private String name;
  private String id;
  private ConfigurationSection config;

  private static String getRandomId(BukkitPlugin p) {

    String id = Integer.toString((int) Math.round(Math.random() * 10000));
    // Get an unused random ID
    while (p.npcman.getNPC(id) != null) {
      id = Integer.toString((int) Math.round(Math.random() * 10000));
    }
    return id;
  }

  public NPC(String displayname, BukkitPlugin p, ConfigurationSection cs) {
    this(displayname, p, NPC.getRandomId(p), cs);
  }

  public NPC(String displayname, BukkitPlugin p, String id,
      ConfigurationSection cs) {
    roles = new ArrayList<Role>();
    plugin = p;
    name = displayname;
    this.id = id;
    this.config = cs;
    if (this.config == null) {
      this.config = new MemoryConfiguration();
    }
    load();
  }

  private void load() {
    name = config.getString("name", "Default Name");
    if (config.isConfigurationSection("location")) {
      this.spawnloc = LocationConfigSerializer.fromConfig(
          config.getConfigurationSection("location"), this.plugin.getServer());
    } else {
      this.spawnloc = LocationConfigSerializer.fromConfig(
          new MemoryConfiguration(), this.plugin.getServer());
    }
    // Clear out old entries
    while (getRoleCount() >= 1) {
      // This will always delete an element
      this.delRole(0);
    }

    if (!config.isList("roles")) {
      return;
    }
    List<?> l = config.getList("roles");
    for (Object o : l) {
      MemoryConfiguration mem = new MemoryConfiguration();
      if (o instanceof Map<?, ?>) {
        @SuppressWarnings("unchecked")
        Map<String, Object> m = (Map<String, Object>) o;
        ConfigurationSection s = mem.createSection("dummy", m);
        if (s.isString("type")) {
          Role r = null;
          try {
            r = RoleFactory.newRole(s.getString("type"), s);
            addRole(r);
          } catch (Exception e) {
          }
        }
      }
    }
  }

  public YamlConfiguration save() {
    YamlConfiguration config = new YamlConfiguration();
    config.set("name", name);
    config.set("location", LocationConfigSerializer.toConfig(spawnloc));
    List<ConfigurationSection> l = new ArrayList<ConfigurationSection>();
    for(Role r : this.getRoles()) {
      ConfigurationSection cs = r.getConfig();
      cs.set("type", RoleFactory.getName(r.getClass()));
      l.add(cs);
    }
    config.set("roles", l);
    return config;
  }

  public Location getCurrentLocation() {
    if (humannpc != null) {
      return humannpc.getBukkitEntity().getLocation();
    } else {
      return spawnloc;
    }
  }

  public void respawn() {
    if (humannpc == null || humannpc.getBukkitEntity().isDead()) {
      humannpc = (HumanNPC) plugin.npcman.spawnHumanNPC(name, spawnloc, id);
    }
    applySkin();
  }

  private void applySkin() {
    Role r = this.getRole(SkinRole.class);
    if(r != null && r instanceof SkinRole) {
      SkinRole srole = (SkinRole) r;
      srole.setSkin(this);
    }
  }

  public Role[] getRoles() {
    return roles.toArray(new Role[0]);
  }

  public int getRoleCount() {
    return roles.size();
  }

  public int addRole(Role role) {
    if (role instanceof UniqueRole) {
      if (this.getRole(((UniqueRole) role).getClass()) != null) {
        return -1;
      }
    }
    roles.add(role);
    return roles.size() - 1;
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
    for (Role r : roles) {
      if (roletype.isInstance(r)) {
        ret.add(r);
      }
    }
    return ret.toArray(new Role[0]);
  }

  public Role getRole(Class<? extends UniqueRole> roletype) {
    for (Role r : roles) {
      if (roletype == r.getClass()) {
        return r;
      }
    }
    return null;
  }

  public Iterator<Role> getRoleIterator() {
    return roles.iterator();
  }

  public void scheduleEvent(Runnable e) {
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, e);
  }

  public void configure(SpoutPlayer p) {
    Stack<Dialog> dialogs = new Stack<Dialog>();
    NPCconfigurator d = new NPCconfigurator(p, this, dialogs);
    GenericPopup popup = new GenericPopup();
    popup.attachWidget(this.plugin, d);
    p.getMainScreen().attachPopupScreen(popup);
  }

  public void setName(String text) {
    if (this.humannpc != null) {
      this.humannpc.setName(text);
    }
    this.name = text;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
