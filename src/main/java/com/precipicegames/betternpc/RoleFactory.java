package com.precipicegames.betternpc;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.precipicegames.betternpc.roles.*;
import com.precipicegames.betternpc.roles.unique.OnDeathRole;
import com.precipicegames.betternpc.roles.unique.RightClickRole;
import com.precipicegames.betternpc.roles.unique.SkinRole;

public class RoleFactory {
  static private HashMap<String, Class<? extends Role>> roletypes = new HashMap<String, Class<? extends Role>>();
  static {
    roletypes.put("AudioRole", AudioRole.class);
    roletypes.put("DialogRole", DialogRole.class);
    roletypes.put("RandomRole", RandomRole.class);
    roletypes.put("RangeRole", RangeRole.class);
    roletypes.put("RightClickRole", RightClickRole.class);
    roletypes.put("SequenceRole", SequenceRole.class);
    roletypes.put("LookRole", LookRole.class);
    roletypes.put("MessageRole", MessageRole.class);
    roletypes.put("SymbolicRole", SymbolicRole.class);
    roletypes.put("WaitRole", WaitRole.class);
    roletypes.put("AliasedRole", AliasedRole.class);
    roletypes.put("EndRole", EndExecutionRole.class);
    roletypes.put("OnDeathRole", OnDeathRole.class);
    roletypes.put("RespawnRole", RespawnRole.class);
    roletypes.put("SkinRole", SkinRole.class);
    roletypes.put("MoveRole", MoveRole.class);
  }

  static public Role newRole(String type, ConfigurationSection cs)
      throws InstantiationException, IllegalAccessException {
    if (!roletypes.containsKey(type)) {
      return null;
    }
    Role x = roletypes.get(type).newInstance();
    x.loadConfig(cs);
    return x;
  }

  static public String[] getRegisteredTypes() {
    return roletypes.keySet().toArray(new String[0]);
  }

  static public boolean isUnique(String type) {
    if (!roletypes.containsKey(type)) {
      return false;
    }
    return roletypes.get(type).isAssignableFrom(UniqueRole.class);
  }

  static public boolean isRoleList(String type) {
    if (!roletypes.containsKey(type)) {
      return false;
    }
    return roletypes.get(type).isAssignableFrom(RoleList.class);
  }

  static public String getName(Class<? extends Role> roletype) {
    for (Entry<String, Class<? extends Role>> e : roletypes.entrySet()) {
      if (roletype == e.getValue()) {
        return e.getKey();
      }
    }
    return null;
  }
}
