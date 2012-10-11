package com.fordays.masssending.message.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.AssignMessageLogListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AssignMessageLogDAOImp extends BaseDAOSupport implements
		AssignMessageLogDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			AssignMessageLog log = (AssignMessageLog) this
					.getHibernateTemplate().get(AssignMessageLog.class,
							new Long(id));
			this.getHibernateTemplate().delete(log);
		}
	}

	public AssignMessageLog getAssignMessageLogById(long id)
			throws AppException {
		AssignMessageLog log;
		if (id > 0) {
			log = (AssignMessageLog) this.getHibernateTemplate().get(
					AssignMessageLog.class, new Long(id));
			return log;
		} else
			return new AssignMessageLog();
	}

	public List getAssignMessageLogByAssignMessageId(
			AssignMessageLogListForm amlf) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AssignMessageLog am  ");

		long assignMessageId = amlf.getAssignMessageId();
		if (assignMessageId > 0) {
			hql.add(" where am.assignMessage.id=? ");
			hql.addParamter(assignMessageId);
		}

		return this.list(hql, amlf);
	}

	public List list() throws AppException {
		String hql = "from AssignMessageLog where 1=1";
		return this.list(hql);
	}
	
	public List listByStatus(long status) throws AppException {
		String hql = "from AssignMessageLog where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(AssignMessageLogListForm amlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from AssignMessageLog amlog where 1=1");
		if (amlf.getStatus() != null) {
			if (amlf.getStatus() != 0) {
				if (amlf.getStatus() == 88) {
					amlf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(amlf.getStatus());
			}
		}

		String beginDate = amlf.getBeginDate();
		String endDate = amlf.getEndDate();

		if ("".equals(beginDate) == false && "".equals(endDate) == true) {
			hql.add(" and to_char(amlog.logTime,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(beginDate);
		}
		if ("".equals(beginDate) == true && "".equals(endDate) == false) {
			hql.add(" and to_char(amlog.logTime,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(endDate);
		}
		if ("".equals(beginDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  to_char(amlog.logTime,'yyyy-mm-dd hh24:mi:ss')  between ? and ? ");
			hql.addParamter(beginDate);
			hql.addParamter(endDate);
		}

		if (amlf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		hql.add(" order by amlog.logTime desc ");
		return this.list(hql, amlf);
	}

	public long merge(AssignMessageLog assignMessage) throws AppException {
		this.getHibernateTemplate().merge(assignMessage);
		return assignMessage.getId();
	}

	public long save(AssignMessageLog assignMessage) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(assignMessage);
		return assignMessage.getId();
	}

	public long update(AssignMessageLog assignMessage) throws AppException {
		if (assignMessage.getId() > 0)
			return save(assignMessage);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
}
