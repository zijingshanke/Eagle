package com.fordays.masssending.forum.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.SectionListForm; //import com.fordays.masssending.forum.SectionListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class SectionDAOImp extends BaseDAOSupport implements SectionDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Section section = (Section) this.getHibernateTemplate().get(
					Section.class, new Long(id));
			this.getHibernateTemplate().delete(section);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Section section = (Section) this.getHibernateTemplate().get(
					Section.class, new Long(id));
			section.setStatus(new Long(88));
			this.getHibernateTemplate().merge(section);
		}
	}

	public Section getSectionById(long id) throws AppException {
		Section section;
		if (id > 0) {
			section = (Section) this.getHibernateTemplate().get(Section.class,
					new Long(id));
			return section;
		} else
			return new Section();
	}

	public List list() throws AppException {
		String hql = "from Section where 1=1";
		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = "from Section where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(SectionListForm slf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Section where 1=1");

		if (slf.getStatus() != null) {
			if (slf.getStatus() != 0) {
				if (slf.getStatus() == 88) {
					slf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(slf.getStatus());
			}
		}

		long forumId = slf.getForumId();
		if (forumId > 0) {
			hql.add(" and forum.id = ? ");
			hql.addParamter(forumId);
		}

		String forumName = slf.getForumName();
		if ("".equals(slf.getForumName()) == false
				&& (slf.getForumName() != null)) {
			forumName = forumName.toUpperCase();
			hql.add(" and upper(forum.name) like ? ");
			hql.addParamter("%" + slf.getForumName() + "%");
		}

		String name = slf.getName();
		if ("".equals(name) == false && (name != null)) {
			name = name.toUpperCase();
			hql.add(" and upper(name) like ? ");
			hql.addParamter("%" + slf.getName() + "%");
		}

		if (slf.isShowHidden() == false) {
			hql.add(" and status not in(88) ");
		}

		hql.add(" order by id desc ");
		
		return this.list(hql, slf);
	}

	public List getSectionsByForumId(long forumId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Section where 1=1");
		hql.add(" and forum.id= ? ");
		hql.addParamter(forumId);

		return this.list(hql);
	}

	public List getSectionsByForumIdAndStatus(long forumId, long status)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Section where 1=1");
		hql.add(" and forum.id= ? ");
		hql.addParamter(forumId);

		hql.add(" and status=? ");
		hql.addParamter(status);

		return this.list(hql);
	}

	public long merge(Section Section) throws AppException {
		this.getHibernateTemplate().merge(Section);
		return Section.getId();
	}

	public long save(Section Section) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(Section);
		return Section.getId();
	}

	public long update(Section Section) throws AppException {
		if (Section.getId() > 0)
			return save(Section);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

}
