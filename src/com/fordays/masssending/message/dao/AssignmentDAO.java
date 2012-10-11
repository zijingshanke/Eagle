package com.fordays.masssending.message.dao;

import java.util.List;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.AssignmentListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AssignmentDAO extends BaseDAO {
	public List list() throws AppException;

	public List listByStatus(long status) throws AppException;

	public List list(AssignmentListForm forumlist) throws AppException;

	public long save(Assignment assignment) throws AppException;

	public long merge(Assignment assignment) throws AppException;

	public long update(Assignment assignment) throws AppException;

	public Assignment getAssignmentById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
