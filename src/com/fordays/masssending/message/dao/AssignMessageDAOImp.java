package com.fordays.masssending.message.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AssignMessageDAOImp extends BaseDAOSupport implements
		AssignMessageDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			AssignMessage forum = (AssignMessage) this.getHibernateTemplate()
					.get(AssignMessage.class, new Long(id));
			this.getHibernateTemplate().delete(forum);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			AssignMessage forum = (AssignMessage) this.getHibernateTemplate()
					.get(AssignMessage.class, new Long(id));
			forum.setStatus(new Long(88));
			this.getHibernateTemplate().merge(forum);
		}
	}

	public AssignMessage getAssignMessageById(long id) throws AppException {
		AssignMessage forum;
		if (id > 0) {
			forum = (AssignMessage) this.getHibernateTemplate().get(
					AssignMessage.class, new Long(id));
			return forum;
		} else
			return new AssignMessage();
	}

	public List getAssignMessageByAssignId(AssignMessageListForm amlf)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from AssignMessage am where am.assignment.id=?");
		hql.addParamter(amlf.getAssignmentId());

		return this.list(hql, amlf);
	}

	public List list() throws AppException {
		String hql = "from AssignMessage where 1=1";
		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = "from AssignMessage where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(AssignMessageListForm amlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from AssignMessage assignMessage where 1=1");
		if (amlf.getStatus() != null) {
			if (amlf.getStatus() != 0) {
				if (amlf.getStatus() == 88) {
					amlf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(amlf.getStatus());
			}
		}

		String assignmentName = amlf.getAssignmentName();
		if (assignmentName != null && "".equals(assignmentName) == false) {
			hql.add(" and assignment.name like ? ");
			hql.addParamter("%" + assignmentName + "%");
		}

		String forumName = amlf.getForumName();
		if (forumName != null && "".equals(forumName) == false) {
			forumName = forumName.toUpperCase();
			hql.add(" and upper(messageSection.section.forum.name) like ? ");
			hql.addParamter("%" + forumName + "%");
		}

		String sectionName = amlf.getSectionName();
		if (sectionName != null && "".equals(sectionName) == false) {
			sectionName = sectionName.toUpperCase();
			hql.add(" and upper(messageSection.section.name) like ? ");
			hql.addParamter("%" + sectionName + "%");
		}

		String userName = amlf.getUserName();
		if (userName != null && "".equals(userName) == false) {
			hql.add(" and sysUser.userName = ? ");
			hql.addParamter(userName);
		}

		String beginDate = amlf.getBeginDate();
		String endDate = amlf.getEndDate();
		if ("".equals(beginDate) == false && "".equals(endDate) == true) {
			hql
					.add(" and to_char(assignMessage.sendedtime,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(beginDate);
		}
		if ("".equals(beginDate) == true && "".equals(endDate) == false) {
			hql
					.add(" and to_char(assignMessage.sendedtime,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(endDate);
		}
		if ("".equals(beginDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  to_char(assignMessage.sendedtime,'yyyy-mm-dd hh24:mi:ss')  between ? and ? ");
			hql.addParamter(beginDate);
			hql.addParamter(endDate);
		}
		if (amlf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		hql.add(" order by assignMessage.sendedtime desc ");

		return this.list(hql, amlf);
	}

	public long merge(AssignMessage assignMessage) throws AppException {
		this.getHibernateTemplate().merge(assignMessage);
		return assignMessage.getId();
	}

	public long save(AssignMessage assignMessage) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(assignMessage);
		return assignMessage.getId();
	}

	public long update(AssignMessage assignMessage) throws AppException {
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
