package com.fordays.masssending.system.dao;

import java.util.List;

import com.fordays.masssending.system.LoginLog;
import com.fordays.masssending.system.LoginLogListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface LoginLogDAO extends BaseDAO {

	public long save(LoginLog loginlog) throws AppException; 
	public List list(LoginLogListForm lllf) throws AppException;
}
