package com.fordays.masssending.message.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.AssignmentListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AssignmentDAOImp extends BaseDAOSupport implements AssignmentDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Assignment assign = (Assignment) this.getHibernateTemplate().get(
					Assignment.class, new Long(id));
			this.getHibernateTemplate().delete(assign);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Assignment assign = (Assignment) this.getHibernateTemplate().get(
					Assignment.class, new Long(id));
			assign.setStatus(new Long(88));
			this.getHibernateTemplate().merge(assign);
		}
	}

	public Assignment getAssignmentById(long id) throws AppException {
		Assignment forum;
		if (id > 0) {
			forum = (Assignment) this.getHibernateTemplate().get(
					Assignment.class, new Long(id));
			return forum;
		} else
			return new Assignment();
	}

	public List list() throws AppException {
		String hql = "from Assignment where 1=1 ";
		return this.list(hql);
	}
	
	public List listByStatus(long status) throws AppException {
		String hql = "from Assignment where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(AssignmentListForm alf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Assignment where 1=1");

		if (alf.getStatus() != null) {
			if (alf.getStatus() != 0) {
				if (alf.getStatus() == 88) {
					alf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(alf.getStatus());
			}
		}

		if (alf.isShowHidden() == false) {
			hql.add(" and status not in(88)");
		}

		hql.add(" order by id desc ");
		return this.list(hql, alf);
	}

	public long merge(Assignment assignment) throws AppException {
		this.getHibernateTemplate().merge(assignment);
		return assignment.getId();
	}

	public long save(Assignment assignment) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(assignment);
		return assignment.getId();
	}

	public long update(Assignment assignment) throws AppException {
		if (assignment.getId() > 0)
			return save(assignment);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

}
