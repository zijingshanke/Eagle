package com.fordays.masssending.message.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.base.MainTask;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageListForm;
import com.fordays.masssending.message.AssignMessageListener;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.biz.AssignMessageBiz;
import com.fordays.masssending.message.biz.AssignmentBiz;
import com.fordays.masssending.message.biz.MessageSectionBiz;
import com.fordays.masssending.user.biz.UserBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AssignMessageListAction extends BaseAction {
	private AssignMessageBiz assignMessageBiz;
	private MessageSectionBiz messageSectionBiz;
	private AssignmentBiz assignmentBiz;
	private UserBiz userBiz;

	/**
	 * 执行回帖任务（开发中）
	 */
	public ActionForward replyMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			AssignMessageListForm amlf = (AssignMessageListForm) form;

			assignMessageBiz.executeReplyMessage(amlf);

			inf.setMessage("执行回帖任务完成,请查看任务完成情况!");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("listExecuteAssignMessage");
		} catch (Exception ex) {
			inf.setMessage("执行发帖任务异常，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 执行发帖任务
	 */
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			AssignMessageListForm amlf = (AssignMessageListForm) form;

			assignMessageBiz.executeSendMessage(amlf);

			inf.setMessage("执行发帖任务完成,请查看任务完成情况!");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("listExecuteAssignMessage");
		} catch (Exception ex) {
			inf.setMessage("执行发帖任务异常，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 执行发帖任务(每个发帖任务有时间间隔)
	 */
	public ActionForward sendMessageASpace(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			AssignMessageListForm amlf = (AssignMessageListForm) form;

			AssignMessageListener amListener = new AssignMessageListener(amlf,
					assignMessageBiz);
			System.out.println("4==================");
			MainTask.put(amListener);
			// ----------
			System.out.println("2==================");

			inf.setMessage("指令已发出,本次批量发帖任务预计需要" + amlf.getTotalTime()
					+ "分钟,请稍后查看任务完成情况!");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("listExecuteAssignMessage");
		} catch (Exception ex) {
			inf.setMessage("执行发帖任务异常，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 等待执行的发帖任务列表
	 */
	public ActionForward listExecuteAssignMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AssignMessageListForm amlf = (AssignMessageListForm) form;
		if (amlf == null) {
			return list(mapping, form, request, response);
		} else {
			amlf.setList(assignMessageBiz.getAssignMessages(amlf));

			request.setAttribute("amlf", amlf);

			forwardPage = "listexecuteAssignMessage";
			return (mapping.findForward(forwardPage));
		}
	}

	/**
	 * 查看失败的发帖任务详情
	 */
	public ActionForward viewMemo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageListForm mlf = (AssignMessageListForm) form;
		int id = mlf.getId();
		if (id > 0) {
			AssignMessage assignMessage = (AssignMessage) assignMessageBiz
					.getAssignMessageById(id);
			assignMessage.setThisAction("view");
			request.setAttribute("assignMessage", assignMessage);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewassignMessageError";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 将 AssignMessage、MessageSection相关信息放入request
	 */
	public HttpServletRequest putAttributeAsAssignMessage(
			HttpServletRequest request) throws AppException {
		request = assignMessageBiz.putAttributeAsAssignMessageByStatus(request,
				new Long(1));
		return messageSectionBiz.putAttributeAsMessageSectionByStatus(request,
				new Long(1));
	}

	/**
	 * 发帖任务统计
	 */
	public ActionForward assignMessageCounter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AssignMessageListForm amlf = (AssignMessageListForm) form;
		if (amlf == null)
			amlf = new AssignMessageListForm();

		amlf.setList(assignMessageBiz.assignMessageCounter(amlf));

		request.setAttribute("amclf", amlf);
		forwardPage = "listassignMessageCounter";
		return (mapping.findForward(forwardPage));
	}

	// -------------------------------------------------------------------------------------
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AssignMessage assignMessage = new AssignMessage();
		assignMessage.setThisAction("insert");
		assignMessage.setStatus(new Long(1));

		request.setAttribute("assignMessage", assignMessage);
		putAttributeAsAssignMessage(request);

		String forwardPage = "editassignMessage";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量添加发帖任务
	 */
	public ActionForward addBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AssignMessage assignMessage = new AssignMessage();
		assignMessage.setThisAction("insertBatch");
		assignMessage.setStatus(new Long(1));

		request.setAttribute("assignMessage", assignMessage);
		putAttributeAsAssignMessage(request);

		String forwardPage = "editassignMessageBatch";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageListForm mlf = (AssignMessageListForm) form;

		int id = 0;
		if (mlf.getSelectedItems().length > 0) {
			id = mlf.getSelectedItems()[0];
		} else
			id = mlf.getId();
		if (id > 0) {
			AssignMessage assignMessage = (AssignMessage) assignMessageBiz
					.getAssignMessageById(id);

			assignMessage.setThisAction("update");
			request.setAttribute("assignMessage", assignMessage);

			request.setAttribute("assignmentlist", assignmentBiz
					.getAssignments());// 任务列表

		} else
			request.setAttribute("assignMessage", new Message());
		forwardPage = "editassignMessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageListForm mlf = (AssignMessageListForm) form;
		int id = mlf.getId();
		if (id > 0) {
			AssignMessage assignMessage = (AssignMessage) assignMessageBiz
					.getAssignMessageById(id);
			assignMessage.setThisAction("view");
			request.setAttribute("assignMessage", assignMessage);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewassignMessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AssignMessageListForm amlf = (AssignMessageListForm) form;
		if (amlf == null)
			amlf = new AssignMessageListForm();
		amlf.setList(assignMessageBiz.getAssignMessages(amlf));

		request.setAttribute("amlf", amlf);
		forwardPage = "listassignMessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AssignMessageListForm mlf = (AssignMessageListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < mlf.getSelectedItems().length; i++) {
				id = mlf.getSelectedItems()[i];
				if (id > 0)
					// assignMessageBiz.deleteById(id);
					assignMessageBiz.hideById(id);

			}
			inf.setMessage("删除发帖任务成功!");
			inf.setForwardPage("/message/assignMessagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除发帖任务失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAssignMessageBiz(AssignMessageBiz assignMessageBiz) {
		this.assignMessageBiz = assignMessageBiz;
	}

	public void setAssignmentBiz(AssignmentBiz assignmentBiz) {
		this.assignmentBiz = assignmentBiz;
	}

	public void setMessageSectionBiz(MessageSectionBiz messageSectionBiz) {
		this.messageSectionBiz = messageSectionBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
}
