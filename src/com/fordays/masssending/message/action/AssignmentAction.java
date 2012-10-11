package com.fordays.masssending.message.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.biz.AssignmentBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AssignmentAction extends BaseAction {
	private AssignmentBiz assignmentBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Assignment assignment = (Assignment) form;
		Inform inf = new Inform();
		try {
			Assignment tempAssignment = assignmentBiz.setTempForm(assignment);

			assignmentBiz.update(tempAssignment);

			request.setAttribute("assignment", tempAssignment);

			inf.setMessage("你已成功更新了任务");
			inf.setForwardPage("/message/assignmentlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新任务出错！错误信息是:" + ex.getMessage());
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
		Assignment assignment = (Assignment) form;
		Inform inf = new Inform();

		try {
			Assignment tempAssignment = assignmentBiz.setTempForm(assignment);

			assignmentBiz.save(tempAssignment);
			request.setAttribute("assignment", tempAssignment);
			inf.setMessage("你已成功增加了任务");
			inf.setForwardPage("/message/assignmentlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加任务失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAssignmentBiz(AssignmentBiz assignmentBiz) {
		this.assignmentBiz = assignmentBiz;
	}

}
