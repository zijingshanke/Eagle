package com.fordays.masssending.message.biz;

import java.util.List;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.AssignMessageLogListForm;
import com.neza.exception.AppException;

/**
 * 发帖任务日志管理业务接口
 */
public interface AssignMessageLogBiz {

	public List getAssignMessageLogs() throws AppException;

	public List getAssignMessageLogsByStatus(long status) throws AppException;

	public List getAssignMessageLogs(AssignMessageLogListForm amlf)
			throws AppException;

	public List getAssignMessageLogByAssignMessageId(
			AssignMessageLogListForm amlf) throws AppException;

	public long save(AssignMessageLog assignmessageLog) throws AppException;

	public long merge(AssignMessageLog assignmessageLog) throws AppException;

	public long update(AssignMessageLog assignmessageLog) throws AppException;

	public AssignMessageLog getAssignMessageLogById(long id)
			throws AppException;

	public void deleteById(long id) throws AppException;
}
