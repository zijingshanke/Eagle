package com.fordays.masssending.message.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.AssignMessageLogListForm;
import com.fordays.masssending.message.biz.AssignMessageLogBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class AssignMessageLogListAction extends BaseAction {
	private AssignMessageLogBiz assignMessageLogBiz;

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageLogListForm mlf = (AssignMessageLogListForm) form;
		int id = mlf.getId();
		if (id > 0) {
			AssignMessageLog assignMessageLog = (AssignMessageLog) assignMessageLogBiz
					.getAssignMessageLogById(id);
			assignMessageLog.setThisAction("view");
			request.setAttribute("assignMessageLog", assignMessageLog);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewassignMessageLog";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward viewLogAsAssignMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AssignMessageLogListForm amloglf = (AssignMessageLogListForm) form;
		long id = amloglf.getAssignMessageId();
		if (id > 0) {
			List assignMessageLogList = assignMessageLogBiz
					.getAssignMessageLogByAssignMessageId(amloglf);
			if (assignMessageLogList.size() > 0) {
				AssignMessageLog assignMessageLog = (AssignMessageLog) assignMessageLogList
						.get(0);
				AssignMessage assignMessage = assignMessageLog
						.getAssignMessage();
				amloglf.setAssignMessage(assignMessage);
			}

			amloglf.setList(assignMessageLogList);
			request.setAttribute("amloglf", amloglf);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewLogAsAssignMessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageLogListForm amloglf = (AssignMessageLogListForm) form;
		if (amloglf == null)
			amloglf = new AssignMessageLogListForm();
		amloglf.setList(assignMessageLogBiz.getAssignMessageLogs(amloglf));

		request.setAttribute("amloglf", amloglf);
		forwardPage = "listassignMessageLog";
		return (mapping.findForward(forwardPage));
	}

	public void setAssignMessageLogBiz(AssignMessageLogBiz assignMessageLogBiz) {
		this.assignMessageLogBiz = assignMessageLogBiz;
	}
}
