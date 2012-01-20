package com.precipicegames.betternpc.test;
import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import org.junit.Test;

import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.RoleFactory;
import com.precipicegames.betternpc.roles.AudioRole;
import com.precipicegames.betternpc.roles.EndExecutionRole;
import com.precipicegames.betternpc.roles.LookRole;
import com.precipicegames.betternpc.roles.MessageRole;
import com.precipicegames.betternpc.roles.RandomRole;
import com.precipicegames.betternpc.roles.SequenceRole;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class RoleFactoryTest {

	@Test
	public void testNewRole() {
		ArrayList<String> testedRoles = new ArrayList<String>();
		for(String name : RoleFactory.getRegisteredTypes()){
			testedRoles.add(name);
		}
		for(String rolename : testedRoles) {
			Role r = null;
			try {
				MemoryConfiguration cs = new MemoryConfiguration();
				r = RoleFactory.newRole(rolename, cs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Could not create the instance for roletype: " + rolename);
			}
			if (r == null) {
				fail("Not found roletype: " + rolename);
			}
		}
	}
	@Test
	public void testGetName() {
		String name = RoleFactory.getName(LookRole.class);
		try {
			if(!(RoleFactory.newRole(name, new MemoryConfiguration()) instanceof LookRole)) {
				fail("Created test role of wrong type");
			}
		} catch (Exception e) {
			fail("Could not create the object");
			e.printStackTrace();
		}
	}
	@Test
	public void testLogic() {
		try {
			RandomRole role1 = new RandomRole();
			SequenceRole role2 = new SequenceRole();
			RandomRole role3 = new RandomRole();
			MessageRole message = new MessageRole();
			//AudioRole role4 = new AudioRole();
			message.setText("Test Event Message");
			role1.addRole(role2);
			System.out.println("index: " + role2.addRole(message));
			System.out.println("index: " + role2.addRole(message));
			System.out.println("index: " + role2.addRole(message));
			TestPlayer testp = new TestPlayer();
			System.out.println("player: " + testp.hashCode());
			role2.addRole(role3);
			//role3.addRole(role4);
			//role1.addRole(role4);
			Stack<Role> s = new Stack<Role>();
			s.push(new EndExecutionRole());
			role1.startRole(testp, null, s);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error testing logic");
		}
	}
}
