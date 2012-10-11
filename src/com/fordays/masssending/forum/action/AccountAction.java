package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.biz.AccountBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AccountAction extends BaseAction {
	private AccountBiz accountBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Account account = (Account) form;
		Inform inf = new Inform();
		try {
			Account tempAccount = accountBiz.setTempForm(account);

			accountBiz.updateAsForum(tempAccount);

			request.setAttribute("account", tempAccount);
			inf.setMessage("你已成功更新了帐号");
			inf.setForwardPage("/forum/accountlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新帐号出错！错误信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Account account = (Account) form;
		Inform inf = new Inform();

		try {
			Account tempAccount = accountBiz.setTempForm(account);

			accountBiz.saveAccountAsForum(tempAccount);

			request.setAttribute("Account", tempAccount);
			inf.setMessage("你已成功增加了帐号");
			inf.setForwardPage("/forum/accountlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加帐号失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insertBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Account account = (Account) form;
		Inform inf = new Inform();

		String[] names = request.getParameterValues("loginName");
		String[] values = request.getParameterValues("loginPassword");

		try {

			if (names != null && values != null) {
				int arrayLen = names.length;
				System.out.println("arrayLength:" + arrayLen);

				for (int i = 0; i < arrayLen; i++) {
					String name = names[i];
					String value = values[i];

					account.setLoginName(name);
					account.setLoginPassword(value);
					Account tempAccount = accountBiz.setTempForm(account);
					accountBiz.saveAccountAsForum(tempAccount);
					request.setAttribute("Account", tempAccount);
				}
				inf.setMessage("你已成功增加了帐号");
				inf.setForwardPage("/forum/accountlist.do");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("获取帐号信息失败");
				inf.setBack(true);
			}
		} catch (Exception ex) {
			inf.setMessage("增加帐号失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

}
