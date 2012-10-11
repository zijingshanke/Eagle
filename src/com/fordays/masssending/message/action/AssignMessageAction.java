package com.fordays.masssending.message.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.biz.AssignMessageBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AssignMessageAction extends BaseAction {
	private AssignMessageBiz assignMessageBiz;

	/**
	 * 更新发帖任务
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessage assignment = (AssignMessage) form;
		Inform inf = new Inform();
		try {
			AssignMessage tempAssignMessage = assignMessageBiz
					.getTempAssignMessage(assignment);

			assignMessageBiz.updateAsAssignMessage(tempAssignMessage);

			request.setAttribute("assignment", tempAssignMessage);

			inf.setMessage("你已成功更新了发帖任务");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新发帖任务出错！错误信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 保存发帖任务
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessage tempAssignMessage = (AssignMessage) form;
		Inform inf = new Inform();

		try {
			assignMessageBiz.saveAsAssignMessage(tempAssignMessage);
			assignMessageBiz.saveAssignMessageLog(tempAssignMessage, "0");

			inf.setMessage("你已成功增加了发帖任务");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加发帖任务失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量保存发帖任务
	 */
	public ActionForward insertBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessage assignMessage = (AssignMessage) form;
		Inform inf = new Inform();

		try {
			List<AssignMessage> tempAMlist = assignMessageBiz
					.getassignMessageByBatchRequest(request, assignMessage);

			for (int i = 0; i < tempAMlist.size(); i++) {
				AssignMessage tempAssignMessage = tempAMlist.get(i);
				assignMessageBiz.saveAsAssignMessage(tempAssignMessage);
				assignMessageBiz.saveAssignMessageLog(tempAssignMessage, "0");
			}

			inf.setMessage("你已成功批量增加了发帖任务");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加发帖任务失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAssignMessageBiz(AssignMessageBiz assignMessageBiz) {
		this.assignMessageBiz = assignMessageBiz;
	}
}
