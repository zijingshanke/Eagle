package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.AccountListForm;
import com.fordays.masssending.forum.biz.AccountBiz;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AccountListAction extends BaseAction {
	private AccountBiz accountBiz;
	private ForumBiz forumBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AccountListForm alf = (AccountListForm) form;

		int id = 0;
		if (alf.getSelectedItems().length > 0) {
			id = alf.getSelectedItems()[0];
		} else
			id = alf.getId();
		if (id > 0) {
			Account account = (Account) accountBiz.getAccountById(id);

			request.setAttribute("forumlist", forumBiz.getForums());// 论坛列表
			account.setThisAction("update");
			request.setAttribute("account", account);
		} else
			request.setAttribute("account", new Account());
		forwardPage = "editaccount";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AccountListForm alf = (AccountListForm) form;
		int id = alf.getId();
		if (id > 0) {
			Account account = (Account) accountBiz.getAccountById(id);
			account.setThisAction("view");
			request.setAttribute("account", account);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewaccount";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Account account = new Account();
		account.setThisAction("insert");
		account.setStatus(new Long(1));

		request.setAttribute("forumlist", forumBiz
				.getForumsByStatus(new Long(1)));// 论坛列表
		request.setAttribute("account", account);
		String forwardPage = "editaccount";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量添加帐号
	 */
	public ActionForward addBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Account account = new Account();
		account.setThisAction("insertBatch");
		account.setStatus(new Long(1));

		request.setAttribute("forumlist", forumBiz
				.getForumsByStatus(new Long(1)));// 论坛列表

		request.setAttribute("account", account);
		String forwardPage = "editaccountBatch";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AccountListForm alf = (AccountListForm) form;
		if (alf == null)
			alf = new AccountListForm();
		alf.setList(accountBiz.getAccounts(alf));

		request.setAttribute("alf", alf);
		forwardPage = "listaccount";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward ajaxList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			accountBiz.printAccountsHtmlByAjaxList(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountListForm alf = (AccountListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < alf.getSelectedItems().length; i++) {
				id = alf.getSelectedItems()[i];
				if (id > 0)
					// accountBiz.deleteById(id);
					accountBiz.hideById(id);
			}
			inf.setMessage("删除帐号成功!");
			inf.setForwardPage("/forum/accountlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除帐号失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}

}
