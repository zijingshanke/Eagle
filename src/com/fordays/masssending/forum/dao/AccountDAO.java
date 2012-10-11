package com.fordays.masssending.forum.dao;

import java.util.List;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.AccountListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AccountDAO extends BaseDAO {
	public List list() throws AppException;

	public List listByStatus(long status) throws AppException;

	public List list(AccountListForm accountlist) throws AppException;

	public long save(Account account) throws AppException;

	public long merge(Account account) throws AppException;

	public long update(Account account) throws AppException;

	public Account getAccountById(long id) throws AppException;

	public List getAccountByForumId(long forumId) throws AppException;

	public List getAccountByForumIdAndStatus(long forumId, long status)
			throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
