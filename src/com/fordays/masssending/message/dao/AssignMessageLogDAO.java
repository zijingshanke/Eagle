package com.fordays.masssending.message.dao;

import java.util.List;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.AssignMessageLogListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AssignMessageLogDAO extends BaseDAO {
	public List list() throws AppException;
	public List listByStatus(long status) throws AppException;
	public List list(AssignMessageLogListForm amlf) throws AppException;

	public List getAssignMessageLogByAssignMessageId(
			AssignMessageLogListForm amlf) throws AppException;

	public long save(AssignMessageLog assignmessageLog) throws AppException;

	public long merge(AssignMessageLog assignmessageLog) throws AppException;

	public long update(AssignMessageLog assignmessageLog) throws AppException;

	public AssignMessageLog getAssignMessageLogById(long id)
			throws AppException;

	public void deleteById(long id) throws AppException;
}
