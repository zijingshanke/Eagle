package com.fordays.masssending.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fordays.masssending.system.LoginLogListForm;
import com.fordays.masssending.system.biz.LoginLogBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginLogListAction extends BaseAction {
	private LoginLogBiz loginlogBiz;

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String locate = request.getParameter("locate");
		String forwardPage = "";
		LoginLogListForm lllf = (LoginLogListForm) form;
		if (lllf == null)
			lllf = new LoginLogListForm();
		lllf.setLocate(new Long(locate));
		lllf.setList(loginlogBiz.getLoginLogs(lllf));
		request.setAttribute("lllf", lllf);
		request.setAttribute("locate", locate);
		
		forwardPage = "listclientloginlog";

		return (mapping.findForward(forwardPage));
	}

	public void setLoginlogBiz(LoginLogBiz loginlogBiz) {
		this.loginlogBiz = loginlogBiz;
	}
}