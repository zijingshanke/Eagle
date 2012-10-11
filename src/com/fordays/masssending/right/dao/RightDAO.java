package com.fordays.masssending.right.dao;

import java.util.List;


import com.neza.base.BaseDAO;
import com.neza.exception.AppException;


public interface RightDAO extends BaseDAO {
	public List listRoleRights()throws AppException;
	public List listRoleRightsByUserId(long userId) throws AppException;
}
