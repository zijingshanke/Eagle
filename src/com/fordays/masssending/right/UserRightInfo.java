package com.fordays.masssending.right;

import com.fordays.masssending.user.SysUser;
import com.neza.base.*;


public class UserRightInfo extends BaseRightInfo {
 	private static final long serialVersionUID = 1L;

	private SysUser user;

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}


	
	public long getUserId() {
		return user.getUserId();
	}


}
