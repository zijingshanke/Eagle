package com.fordays.masssending.system.dao;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.system.TaskTimestamp;
import com.neza.base.BaseDAOSupport;
import com.neza.exception.AppException;

public class TaskTimestampDAOImpl extends BaseDAOSupport implements
		TaskTimestampDAO {

	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void save(TaskTimestamp tasktimestamp) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(tasktimestamp);
	}

	public void updateStatus(TaskTimestamp tasktimestamp) throws AppException {
		this.getHibernateTemplate().update(tasktimestamp);
	}

}
