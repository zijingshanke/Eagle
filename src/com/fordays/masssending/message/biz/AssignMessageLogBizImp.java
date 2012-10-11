package com.fordays.masssending.message.biz;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.AssignMessageLogListForm;
import com.fordays.masssending.message.dao.AssignMessageLogDAO;
import com.neza.exception.AppException;

/**
 * 发帖任务业务实现类
 * 
 */
public class AssignMessageLogBizImp implements AssignMessageLogBiz {
	private AssignMessageLogDAO assignMessageLogDAO;

	public void deleteById(long id) throws AppException {
		assignMessageLogDAO.deleteById(id);
	}

	public AssignMessageLog getAssignMessageLogById(long id)
			throws AppException {
		return assignMessageLogDAO.getAssignMessageLogById(id);
	}

	public List getAssignMessageLogByAssignMessageId(
			AssignMessageLogListForm amlf) throws AppException {
		return assignMessageLogDAO.getAssignMessageLogByAssignMessageId(amlf);
	}

	public List list(AssignMessageLogListForm assignMessagelist)
			throws AppException {
		return assignMessageLogDAO.list(assignMessagelist);
	}

	public long merge(AssignMessageLog assignMessage) throws AppException {
		return assignMessageLogDAO.merge(assignMessage);
	}

	public long save(AssignMessageLog assignMessage) throws AppException {
		return assignMessageLogDAO.save(assignMessage);
	}

	public long update(AssignMessageLog assignMessageLog) throws AppException {
		return assignMessageLogDAO.update(assignMessageLog);
	}

	public List getAssignMessageLogs() throws AppException {
		return assignMessageLogDAO.list();
	}

	public List getAssignMessageLogsByStatus(long status) throws AppException {
		return assignMessageLogDAO.listByStatus(status);
	}

	public List getAssignMessageLogs(
			AssignMessageLogListForm assignMessageLoglist) throws AppException {
		return assignMessageLogDAO.list(assignMessageLoglist);
	}

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void setAssignMessageLogDAO(AssignMessageLogDAO assignMessageLogDAO) {
		this.assignMessageLogDAO = assignMessageLogDAO;
	}
}
