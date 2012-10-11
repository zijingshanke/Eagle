package com.fordays.masssending.system.biz;



import java.util.List;

import com.fordays.masssending.system.LoginLog;
import com.fordays.masssending.system.LoginLogListForm;
import com.neza.exception.AppException;

public interface LoginLogBiz {
	public void saveLoginLog(LoginLog loginlog) throws AppException;
	public List getLoginLogs(LoginLogListForm lllf) throws AppException;
}