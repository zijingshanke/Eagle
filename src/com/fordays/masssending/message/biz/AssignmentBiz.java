package com.fordays.masssending.message.biz;

import java.util.List;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.AssignmentListForm;
import com.neza.exception.AppException;

/**
 * 任务管理业务接口
 */
public interface AssignmentBiz {
	public List getAssignments() throws AppException;

	public List getAssignmentsByStatus(long status) throws AppException;

	public List getAssignments(AssignmentListForm assignmentlist)
			throws AppException;

	public long save(Assignment assignment) throws AppException;

	public long merge(Assignment assignment) throws AppException;

	public long update(Assignment assignment) throws AppException;

	public Assignment getAssignmentById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

	public Assignment setTempForm(Assignment assignment) throws AppException;
}
