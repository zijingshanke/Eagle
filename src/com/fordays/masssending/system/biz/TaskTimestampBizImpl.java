package com.fordays.masssending.system.biz;


import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.system.TaskTimestamp;
import com.fordays.masssending.system.dao.TaskTimestampDAO;
import com.neza.exception.AppException;

public class TaskTimestampBizImpl implements TaskTimestampBiz {
	private TransactionTemplate transactionTemplate;
	private TaskTimestampDAO tasktimestampDAO;
	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void saveTaskTimestamp(TaskTimestamp taskTimestamp)
			throws AppException {
		tasktimestampDAO.save(taskTimestamp);
	}


	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO) {
		this.tasktimestampDAO = tasktimestampDAO;
	}


	public void  updateTaskTimestampStatus(TaskTimestamp taskTimestamp)throws AppException{
		 tasktimestampDAO.updateStatus(taskTimestamp);
	}
	
}
