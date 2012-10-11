package com.fordays.masssending.message.biz;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.AssignmentListForm;
import com.fordays.masssending.message.dao.AssignmentDAO;
import com.neza.exception.AppException;

/**
 * 任务管理业务实现类
 */
public class AssignmentBizImp implements AssignmentBiz {
	private AssignmentDAO assignmentDAO;

	public void deleteById(long id) throws AppException {
		assignmentDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		assignmentDAO.hideById(id);
	}

	public Assignment getAssignmentById(long id) throws AppException {
		return assignmentDAO.getAssignmentById(id);
	}

	public List list(AssignmentListForm assignmentlist) throws AppException {
		return assignmentDAO.list(assignmentlist);
	}

	public long merge(Assignment assignment) throws AppException {
		return assignmentDAO.merge(assignment);
	}

	public long save(Assignment assignment) throws AppException {
		return assignmentDAO.save(assignment);
	}

	public long update(Assignment assignment) throws AppException {
		return assignmentDAO.update(assignment);
	}

	public List getAssignments() throws AppException {
		return assignmentDAO.list();
	}

	public List getAssignmentsByStatus(long status) throws AppException {
		return assignmentDAO.listByStatus(status);
	}

	public List getAssignments(AssignmentListForm ulf) throws AppException {
		return assignmentDAO.list(ulf);
	}

	public Assignment setTempForm(Assignment form) throws AppException {
		Assignment tempAssignment = (Assignment) getAssignmentById(form.getId());
		tempAssignment.setName(form.getName());
		tempAssignment.setContent(form.getContent());
		tempAssignment.setFounder(form.getFounder());
		tempAssignment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		tempAssignment.setBeginTime(form.getBeginTime());
		tempAssignment.setFinishTime(form.getFinishTime());
		tempAssignment.setStatus(form.getStatus());

		return tempAssignment;
	}

	public void setAssignmentDAO(AssignmentDAO assignmentDAO) {
		this.assignmentDAO = assignmentDAO;
	}

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
}
