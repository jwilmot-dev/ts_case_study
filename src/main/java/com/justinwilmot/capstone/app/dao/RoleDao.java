package com.justinwilmot.capstone.app.dao;

//RoleDAO

import com.justinwilmot.capstone.app.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
