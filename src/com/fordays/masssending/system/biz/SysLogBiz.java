package com.fordays.masssending.system.biz;

import java.util.List;

import com.fordays.masssending.system.SysLog;
import com.fordays.masssending.system.SysLogListForm;
import com.neza.exception.AppException;

public interface SysLogBiz {

	public SysLog getSysLogById(int id) throws AppException;

	public List getSysLogs(SysLogListForm sllf) throws AppException;

	public void saveSysLog(SysLog syslog) throws AppException;

	public int updateSysLog(SysLog syslog) throws AppException;

	public void deleteSysLog(int id) throws AppException;

}