package com.fordays.masssending.forum.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.TopicListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class TopicDAOImp extends BaseDAOSupport implements TopicDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Topic topic = (Topic) this.getHibernateTemplate().get(Topic.class,
					new Long(id));
			this.getHibernateTemplate().delete(topic);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Topic topic = (Topic) this.getHibernateTemplate().get(Topic.class,
					new Long(id));
			topic.setStatus(new Long(88));
			this.getHibernateTemplate().merge(topic);
		}
	}

	public Topic getTopicById(long id) throws AppException {
		Topic topic;
		if (id > 0) {
			topic = (Topic) this.getHibernateTemplate().get(Topic.class,
					new Long(id));
			return topic;
		} else
			return new Topic();
	}

	public List list() throws AppException {
		String hql = "from Topic where 1=1";
		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = "from Topic where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(TopicListForm tlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Topic where 1=1");

		if (tlf.getStatus() != null) {
			if (tlf.getStatus() != 0) {
				if (tlf.getStatus() == 88) {
					tlf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(tlf.getStatus());
			}
		}

		if (tlf.getSectionId() > 0) {
			hql.add(" and section.id = ? ");
			hql.addParamter(tlf.getSectionId());
		}

		if (tlf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		return this.list(hql, tlf);
	}

	public List getTopicsBySectionId(long sectionId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Topic where 1=1");

		hql.add(" and section.id= ? ");
		hql.addParamter(sectionId);

		return this.list(hql);
	}

	public List getTopicsBySectionIdAndStatus(long sectionId, long status)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Topic where 1=1");

		hql.add(" and section.id= ? ");
		hql.addParamter(sectionId);

		hql.add(" and status= ? ");
		hql.addParamter(status);

		return this.list(hql);
	}

	public long merge(Topic Topic) throws AppException {
		this.getHibernateTemplate().merge(Topic);
		return Topic.getId();
	}

	public long save(Topic Topic) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(Topic);
		return Topic.getId();
	}

	public long update(Topic Topic) throws AppException {
		if (Topic.getId() > 0)
			return save(Topic);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

}
