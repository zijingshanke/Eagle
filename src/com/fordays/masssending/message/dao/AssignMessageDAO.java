package com.fordays.masssending.message.dao;

import java.util.List;

import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageListForm;
import com.neza.base.BaseDAO;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public interface AssignMessageDAO extends BaseDAO {
	public List list() throws AppException;
	public List listByStatus(long status) throws AppException;
	public List list(AssignMessageListForm amlf) throws AppException;

	public List getAssignMessageByAssignId(AssignMessageListForm amlf)
			throws AppException;

	public long save(AssignMessage assignmessage) throws AppException;

	public long merge(AssignMessage assignmessage) throws AppException;

	public long update(AssignMessage assignmessage) throws AppException;

	public AssignMessage getAssignMessageById(long id) throws AppException;

	public void deleteById(long id) throws AppException;
	public void hideById(long id) throws AppException;
}
