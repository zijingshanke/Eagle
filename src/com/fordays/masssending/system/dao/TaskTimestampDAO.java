package com.fordays.masssending.system.dao;

import com.fordays.masssending.system.TaskTimestamp;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface TaskTimestampDAO extends BaseDAO {
	public void save(TaskTimestamp tasktimestamp) throws AppException;

	public void updateStatus(TaskTimestamp tasktimestamp) throws AppException;
}
