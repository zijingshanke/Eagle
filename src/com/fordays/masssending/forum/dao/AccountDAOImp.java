package com.fordays.masssending.forum.dao;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.AccountListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AccountDAOImp extends BaseDAOSupport implements AccountDAO {
	private TransactionTemplate transactionTemplate;

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Account account = (Account) this.getHibernateTemplate().get(
					Account.class, new Long(id));
			this.getHibernateTemplate().delete(account);
		}
	}

	public void hideById(long id) throws AppException {
		if (id > 0) {
			Account account = (Account) this.getHibernateTemplate().get(
					Account.class, new Long(id));
			account.setStatus(new Long(88));
			this.getHibernateTemplate().merge(account);
		}
	}

	public Account getAccountById(long id) throws AppException {
		Account account;
		if (id > 0) {
			account = (Account) this.getHibernateTemplate().get(Account.class,
					new Long(id));
			return account;
		} else
			return new Account();
	}

	public List getAccountByForumId(long forumId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Account where 1=1");
		hql.add(" and forum.id= ? ");
		hql.addParamter(forumId);

		return this.list(hql);
	}

	public List getAccountByForumIdAndStatus(long forumId, long status)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Account where 1=1");
		hql.add(" and forum.id= ? ");
		hql.addParamter(forumId);

		hql.add(" and status= ? ");
		hql.addParamter(status);

		return this.list(hql);
	}


	public List list() throws AppException {
		String hql = "from Account where 1=1";
		return this.list(hql);
	}

	public List listByStatus(long status) throws AppException {
		String hql = "from Account where 1=1";
		hql += " and status=" + status;
		return this.list(hql);
	}

	public List list(AccountListForm alf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Account where 1=1");

		if (alf.getStatus() != null) {
			if (alf.getStatus() != 0) {
				if (alf.getStatus() == 88) {
					alf.setShowHidden(true);
				}
				hql.add(" and status = ? ");
				hql.addParamter(alf.getStatus());
			}
		}

		if (alf.getLoginName() != null) {
			if (alf.getLoginName().toString().trim() != "") {
				hql.add(" and loginName like ? ");
				hql.addParamter("%" + alf.getLoginName().trim() + "%");
			}
		}

		if (alf.isShowHidden() == false) {
			hql.add(" and status not in(88)");
		}

		hql.add(" order by id desc ");
		return this.list(hql, alf);
	}

	public long merge(Account account) throws AppException {
		this.getHibernateTemplate().merge(account);
		return account.getId();
	}

	public long save(Account account) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(account);
		return account.getId();
	}

	public long update(Account account) throws AppException {
		if (account.getId() > 0)
			return save(account);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
}
