package com.fordays.masssending.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.system.SysLogListForm;
import com.fordays.masssending.system.biz.SysLogBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class SysLogAction extends BaseAction {
	private SysLogBiz sysLogBiz;

	// 显示所有
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		SysLogListForm tslf = (SysLogListForm) form;
		if (tslf == null)
			tslf = new SysLogListForm();
		tslf.setLocate(com.fordays.masssending.system.SysLog.TYPE_LOGIN);
		tslf.setList(sysLogBiz.getSysLogs(tslf));
		request.setAttribute("tslf", tslf);
		forwardPage = "listsyslog";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward listclient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		SysLogListForm tslf = (SysLogListForm) form;
		if (tslf == null)
			tslf = new SysLogListForm();
		tslf.setLocate(new Long(2));
		tslf.setList(sysLogBiz.getSysLogs(tslf));
		request.setAttribute("tslf", tslf);
		forwardPage = "listclientsyslog";
		return (mapping.findForward(forwardPage));
	}

	public void setSysLogBiz(SysLogBiz sysLogBiz) {
		this.sysLogBiz = sysLogBiz;
	}

}