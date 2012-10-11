package com.fordays.masssending.forum.biz;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.AccountListForm;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.dao.AccountDAO;
import com.fordays.masssending.forum.dao.ForumDAO;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.dao.MessageSectionDAO;
import com.neza.exception.AppException;

/**
 * 帐号管理业务实现类
 */
public class AccountBizImp implements AccountBiz {
	private AccountDAO accountDAO;
	private ForumDAO forumDAO;
	private MessageSectionDAO messageSectionDAO;

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void deleteById(long id) throws AppException {
		accountDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		accountDAO.hideById(id);
	}

	public Account getAccountById(long id) throws AppException {
		return accountDAO.getAccountById(id);
	}

	public List getAccountByForumId(long forumId) throws AppException {
		return accountDAO.getAccountByForumId(forumId);
	}

	public List getAccountByForumIdAndStatus(long forumId, long status)
			throws AppException {
		return accountDAO.getAccountByForumIdAndStatus(forumId, status);
	}

	public List list(AccountListForm accountlist) throws AppException {
		return accountDAO.list(accountlist);
	}

	public long merge(Account account) throws AppException {
		return accountDAO.merge(account);
	}

	public long save(Account account) throws AppException {
		return accountDAO.save(account);
	}

	public long saveAccountAsForum(Account account) throws AppException {
		long forumId = account.getForumId();
		Forum forum = forumDAO.getForumById(forumId);

		account.setForum(forum);
		return save(account);
	}

	public long update(Account account) throws AppException {
		return accountDAO.update(account);
	}

	public long updateAsForum(Account account) throws AppException {
		long forumId = account.getForumId();
		Forum forum = forumDAO.getForumById(forumId);

		account.setForum(forum);
		return update(account);
	}

	public List getAccounts() throws AppException {
		return accountDAO.list();
	}

	public List getAccountsByStatus(long status) throws AppException {
		return accountDAO.listByStatus(status);
	}

	public List getAccounts(AccountListForm ulf) throws AppException {
		return accountDAO.list(ulf);
	}

	public Account setTempForm(Account account) throws AppException {
		Account tempAccount = (Account) getAccountById(account.getId());

		tempAccount.setLoginName(account.getLoginName());
		tempAccount.setLoginPassword(account.getLoginPassword());
		tempAccount.setForumId(account.getForumId());

		tempAccount.setStatus(account.getStatus());
		return tempAccount;
	}

	public void printAccountsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String messageSectionIdStr = request.getParameter("messageSectionId")
				.trim();
		String batchFlagStr = request.getParameter("batchFlag");

		String select_xml_start = "<select name=\"accountId\" class=\"lef\" >";

		if (batchFlagStr != null && "".equals(batchFlagStr) == false) {
			batchFlagStr = batchFlagStr.trim();
			select_xml_start = "<select name=\"accountId\" id=\"accountId"
					+ batchFlagStr + "\"" + " class=\"lef\" >";
		}

		String select_xml_end = "</select>";
		String xml = "";
		String last_xml = "";

		long messageSectionId = Long.parseLong(messageSectionIdStr);
		MessageSection messageSection = messageSectionDAO
				.getMessageSectionById(messageSectionId);

		long forumId = messageSection.getSection().getForum().getId();

		List selectList = getAccountByForumIdAndStatus(forumId, new Long(1));

		if (selectList.size() == 0) {
			// -----
		} else {
			Iterator it = selectList.iterator();
			while (it.hasNext()) {
				Account account = (Account) it.next();
				xml += "<option value=" + "\"" + account.getId() + "\"" + ">";
				xml += account.getLoginName() + "</option>";
			}
			last_xml = select_xml_start + xml + select_xml_end;
		}
		out.print(last_xml);
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	public void setMessageSectionDAO(MessageSectionDAO messageSectionDAO) {
		this.messageSectionDAO = messageSectionDAO;
	}
}
