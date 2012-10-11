package com.fordays.masssending.message.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.AssignmentListForm;
import com.fordays.masssending.message.biz.AssignmentBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AssignmentListAction extends BaseAction {
	private AssignmentBiz assignmentBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignmentListForm asslf = (AssignmentListForm) form;

		int id = 0;
		if (asslf.getSelectedItems().length > 0) {
			id = asslf.getSelectedItems()[0];
		} else
			id = asslf.getId();
		if (id > 0) {
			Assignment patternemail = (Assignment) assignmentBiz
					.getAssignmentById(id);

			patternemail.setThisAction("update");
			request.setAttribute("assignment", patternemail);
		} else
			request.setAttribute("assignment", new Assignment());
		forwardPage = "editassignment";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignmentListForm asslf = (AssignmentListForm) form;
		int id = asslf.getId();
		if (id > 0) {
			Assignment patternemail = (Assignment) assignmentBiz
					.getAssignmentById(id);
			patternemail.setThisAction("view");
			request.setAttribute("assignment", patternemail);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewassignment";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Assignment assignment = new Assignment();
		assignment.setThisAction("insert");
		assignment.setStatus(new Long(1));

		request.setAttribute("assignment", assignment);
		String forwardPage = "editassignment";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignmentListForm asslf = (AssignmentListForm) form;
		if (asslf == null)
			asslf = new AssignmentListForm();
		asslf.setList(assignmentBiz.getAssignments(asslf));

		request.setAttribute("asslf", asslf);
		forwardPage = "listassignment";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward listPatternEmails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AssignmentListForm asslf = (AssignmentListForm) form;
		if (asslf == null)
			asslf = new AssignmentListForm();

		asslf.setList(assignmentBiz.getAssignments(asslf));

		request.setAttribute("asslf", asslf);
		forwardPage = "listassignment";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AssignmentListForm asslf = (AssignmentListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < asslf.getSelectedItems().length; i++) {
				id = asslf.getSelectedItems()[i];
				if (id > 0)
					// assignmentBiz.deleteById(id);
					assignmentBiz.hideById(id);
			}
			inf.setMessage("删除任务成功!");
			inf.setForwardPage("/message/assignmentlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除任务失败，提示信息：" + ex.getMessage());
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
