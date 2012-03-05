package com.precipicegames.betternpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

public class GenericRoleList implements RoleList {
  private ArrayList<Role> roles;

  public GenericRoleList() {
    roles = new ArrayList<Role>();
  }

  public Role[] getRoles() {
    return roles.toArray(new Role[0]);
  }

  public int getRoleCount() {
    return roles.size();
  }

  public int addRole(Role role) {
    if (role instanceof UniqueRole) {
      if (this.getRole(((UniqueRole) role).getClass()) == null) {
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
      if (roletype.isInstance(r)) {
        return r;
      }
    }
    return null;
  }

  public Iterator<Role> getRoleIterator() {
    return roles.iterator();
  }

  public ConfigurationSection getConfig() {
    List<ConfigurationSection> subconfigs = new ArrayList<ConfigurationSection>();
    MemoryConfiguration config = new MemoryConfiguration();
    for (Role r : this.getRoles()) {
      String type = RoleFactory.getName(r.getClass());
      ConfigurationSection roleconfig = r.getConfig();
      roleconfig.set("type", type);
      subconfigs.add(roleconfig);
    }
    config.set("roles", subconfigs);
    return config;
  }

  public void loadConfig(ConfigurationSection config) {
    if (!config.isList("roles")) {
      return;
    }
    List<?> l = config.getList("roles");
    for (Object o : l) {
      MemoryConfiguration mem = new MemoryConfiguration();
      System.out.println(o.getClass());
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
}
