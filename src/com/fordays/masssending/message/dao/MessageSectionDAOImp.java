package com.fordays.masssending.message.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.MessageSectionListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class MessageSectionDAOImp extends BaseDAOSupport implements
		MessageSectionDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			MessageSection forum = (MessageSection) this.getHibernateTemplate()
					.get(MessageSection.class, new Long(id));
			this.getHibernateTemplate().delete(forum);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			MessageSection forum = (MessageSection) this.getHibernateTemplate()
					.get(MessageSection.class, new Long(id));
			forum.setStatus(new Long(88));
			this.getHibernateTemplate().merge(forum);
		}
	}

	public MessageSection getMessageSectionById(long id) throws AppException {
		MessageSection forum;
		if (id > 0) {
			forum = (MessageSection) this.getHibernateTemplate().get(
					MessageSection.class, new Long(id));
			return forum;
		} else
			return new MessageSection();
	}

	public List getMessageSectionBySectionId(MessageSectionListForm mslf)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from MessageSection am where am.assignment.id=?");
		hql.addParamter(mslf.getSectionId());
		return this.list(hql, mslf);
	}

	public List list() throws AppException {
		String hql = "from MessageSection where 1=1";
		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = "from MessageSection where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(MessageSectionListForm mslf) throws AppException {
		Hql hql = new Hql();
		hql.add("from MessageSection where 1=1");

		if (mslf.getStatus() != null) {
			if (mslf.getStatus() != 0) {
				if (mslf.getStatus() == 88) {
					mslf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(mslf.getStatus());
			}
		}

		String forumName = mslf.getForumName();
		if (forumName != null && "".equals(forumName) == false) {
			forumName = forumName.toUpperCase();
			hql.add(" and upper(section.forum.name) like ? ");
			hql.addParamter("%" + forumName + "%");
		}

		String sectionName = mslf.getSectionName();
		if (sectionName != null && "".equals(sectionName) == false) {
			sectionName = sectionName.toUpperCase();
			hql.add(" and upper(section.name) like ? ");
			hql.addParamter("%" + sectionName + "%");
		}

		String messageTitle = mslf.getMessageTitle();
		if (messageTitle != null && "".equals(messageTitle) == false) {
			messageTitle = messageTitle.toUpperCase();
			hql.add(" and upper(message.title) like ? ");
			hql.addParamter("%" + messageTitle + "%");
		}

		if (mslf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		if (mslf.isDescById()) {
			hql.add(" order by id desc ");
		}

		return this.list(hql, mslf);
	}

	public long merge(MessageSection messageSection) throws AppException {
		this.getHibernateTemplate().merge(messageSection);
		return messageSection.getId();
	}

	public long save(MessageSection messageSection) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(messageSection);
		return messageSection.getId();
	}

	public long update(MessageSection messageSection) throws AppException {
		if (messageSection.getId() > 0)
			return save(messageSection);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
}
