package com.fordays.masssending.system.biz;

import com.fordays.masssending.system.TaskTimestamp;
import com.neza.exception.AppException;

public interface TaskTimestampBiz {
	public void saveTaskTimestamp(TaskTimestamp taskTimestamp)
			throws AppException;

	public void updateTaskTimestampStatus(TaskTimestamp taskTimestamp)
			throws AppException;

}