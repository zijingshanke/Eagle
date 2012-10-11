package com.fordays.masssending.message.dao;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class MessageDAOImp extends BaseDAOSupport implements MessageDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Message forum = (Message) this.getHibernateTemplate().get(
					Message.class, new Long(id));
			this.getHibernateTemplate().delete(forum);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Message forum = (Message) this.getHibernateTemplate().get(
					Message.class, new Long(id));
			forum.setStatus(new Long(88));
			this.getHibernateTemplate().merge(forum);
		}
	}

	public Message getMessageById(long id) throws AppException {
		Message forum;
		if (id > 0) {
			forum = (Message) this.getHibernateTemplate().get(Message.class,
					new Long(id));
			return forum;
		} else
			return new Message();
	}

	public List list() throws AppException {
		String hql = "from Message where 1=1";
		return this.list(hql);
	}
	
	public List listByStatus(long status) throws AppException {
		String hql = "from Message where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(MessageListForm mlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Message where 1=1");

		if (mlf.getStatus() != null) {
			if (mlf.getStatus() != 0) {
				if (mlf.getStatus() == 88) {
					mlf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(mlf.getStatus());
			}
		}

		String title = mlf.getTitle();
		if (title != null && "".equals(title) == false) {
			hql.add(" and title like ? ");
			hql.addParamter("%" + title + "%");
		}

		if (mlf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}
		
		hql.add(" order by id desc ");

		return this.list(hql, mlf);
	}

	public long merge(Message assignment) throws AppException {
		this.getHibernateTemplate().merge(assignment);
		return assignment.getId();
	}

	public long save(Message assignment) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(assignment);
		return assignment.getId();
	}

	public long update(Message assignment) throws AppException {
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
