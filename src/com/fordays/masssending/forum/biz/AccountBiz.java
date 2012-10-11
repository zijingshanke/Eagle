package com.fordays.masssending.forum.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.AccountListForm;
import com.neza.exception.AppException;

/**
 * 帐号管理业务接口
 */
public interface AccountBiz {
	public List getAccounts() throws AppException;

	public List getAccountsByStatus(long status) throws AppException;

	public List getAccounts(AccountListForm accountlist) throws AppException;

	public long save(Account account) throws AppException;

	public long saveAccountAsForum(Account account) throws AppException;

	public long merge(Account account) throws AppException;

	public long update(Account account) throws AppException;

	public long updateAsForum(Account account) throws AppException;

	public Account getAccountById(long id) throws AppException;

	public List getAccountByForumId(long forumId) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

	public Account setTempForm(Account account) throws AppException;

	public void printAccountsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception;
}
