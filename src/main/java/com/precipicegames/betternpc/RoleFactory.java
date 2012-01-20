package com.precipicegames.betternpc;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.precipicegames.betternpc.roles.AudioRole;
import com.precipicegames.betternpc.roles.DialogRole;
import com.precipicegames.betternpc.roles.LookRole;
import com.precipicegames.betternpc.roles.MessageRole;
import com.precipicegames.betternpc.roles.RandomRole;
import com.precipicegames.betternpc.roles.RangeRole;
import com.precipicegames.betternpc.roles.RightClickRole;
import com.precipicegames.betternpc.roles.SequenceRole;

public class RoleFactory {
	static private HashMap<String,Class<? extends Role>> roletypes = new HashMap<String,Class<? extends Role>>();
	static {
		roletypes.put("AudioRole", AudioRole.class);
		roletypes.put("DialogRole", DialogRole.class);
		roletypes.put("RandomRole", RandomRole.class);
		roletypes.put("RangeRole", RangeRole.class);
		roletypes.put("RightClickRole", RightClickRole.class);
		roletypes.put("SequenceRole", SequenceRole.class);
		roletypes.put("LookRole", LookRole.class);
		roletypes.put("MessageRole", MessageRole.class);
	}
	static public Role newRole(String type, ConfigurationSection cs) throws InstantiationException, IllegalAccessException {
		if(!roletypes.containsKey(type)) {
			return null;
		}
		Role x = roletypes.get(type).newInstance();
		x.loadConfig(cs);
		return x;
	}
	static public String[] getRegisteredTypes() {
		return roletypes.keySet().toArray(new String[0]);
	}
	static public String getName(Class<? extends Role> roletype) {
		for(Entry<String, Class<? extends Role>> e : roletypes.entrySet()) {
			if(roletype == e.getValue()) {
				return e.getKey();
			}
		}
		return null;
	}
}
