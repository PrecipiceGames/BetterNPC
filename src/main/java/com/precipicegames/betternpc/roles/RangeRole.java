package com.precipicegames.betternpc.roles;

import com.precipicegames.betternpc.Role;
import com.precipicegames.betternpc.UniqueRole;

public class RangeRole extends SymbolicRole implements Role, UniqueRole {
  public String getName() {
    return "Range Role";
  }
}
