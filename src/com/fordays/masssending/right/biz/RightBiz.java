package com.fordays.masssending.right.biz;


import com.fordays.masssending.right.UserRightInfo;
import com.neza.exception.AppException;


public interface RightBiz {
	public void setRights(UserRightInfo uri,long userId) throws AppException;
}
