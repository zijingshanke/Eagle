package com.fordays.masssending.forum.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.ForumListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ForumDAOImp extends BaseDAOSupport implements ForumDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Forum forum = (Forum) this.getHibernateTemplate().get(Forum.class,
					new Long(id));
			this.getHibernateTemplate().delete(forum);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Forum forum = (Forum) this.getHibernateTemplate().get(Forum.class,
					new Long(id));
			forum.setStatus(new Long(88));
			this.getHibernateTemplate().merge(forum);
		}
	}

	public Forum getForumById(long id) throws AppException {
		Forum forum;
		if (id > 0) {
			forum = (Forum) this.getHibernateTemplate().get(Forum.class,
					new Long(id));
			return forum;
		} else
			return new Forum();
	}

	public List list() throws AppException {
		String hql = " from Forum where 1=1 ";

		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = " from Forum where 1=1 ";

		hql += " and status=" + status;

		return this.list(hql);
	}

	public List list(ForumListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Forum where 1=1");

		if (ulf.getStatus() != null) {
			if (ulf.getStatus() != 0) {
				if (ulf.getStatus() == 88) {
					ulf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(ulf.getStatus());
			}
		}

		if (ulf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		hql.add(" order by id desc ");

		return this.list(hql, ulf);
	}

	public long merge(Forum forum) throws AppException {
		this.getHibernateTemplate().merge(forum);
		return forum.getId();
	}

	public long save(Forum forum) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(forum);
		return forum.getId();
	}

	public long update(Forum forum) throws AppException {
		if (forum.getId() > 0)
			return save(forum);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public Forum getForumByName(String name) throws AppException {
		String hql = "from Forum where name='" + name + "'";
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			return (Forum) query.list().get(0);
		}
		return null;
	}
}
