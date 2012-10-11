package com.fordays.masssending.message.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageListForm;
import com.fordays.masssending.message.biz.MessageBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class MessageListAction extends BaseAction {
	private MessageBiz messageBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageListForm mlf = (MessageListForm) form;

		int id = 0;
		if (mlf.getSelectedItems().length > 0) {
			id = mlf.getSelectedItems()[0];
		} else
			id = mlf.getId();
		if (id > 0) {
			Message patternemail = (Message) messageBiz.getMessageById(id);

			patternemail.setThisAction("update");
			request.setAttribute("message", patternemail);
		} else
			request.setAttribute("message", new Message());
		forwardPage = "editmessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageListForm mlf = (MessageListForm) form;
		int id = mlf.getId();
		if (id > 0) {
			Message patternemail = (Message) messageBiz.getMessageById(id);
			patternemail.setThisAction("view");
			request.setAttribute("message", patternemail);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewmessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Message message = new Message();
		message.setThisAction("insert");
		message.setStatus(new Long(1));

		request.setAttribute("message", message);
		String forwardPage = "editmessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageListForm mlf = (MessageListForm) form;
		if (mlf == null)
			mlf = new MessageListForm();
		mlf.setList(messageBiz.getMessages(mlf));

		request.setAttribute("mlf", mlf);
		forwardPage = "listmessage";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		MessageListForm mlf = (MessageListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < mlf.getSelectedItems().length; i++) {
				id = mlf.getSelectedItems()[i];
				if (id > 0)
					// messageBiz.deleteById(id);
					messageBiz.hideById(id);
			}
			inf.setMessage("删除帖子成功!");
			inf.setForwardPage("/message/messagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除帖子失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setMessageBiz(MessageBiz messageBiz) {
		this.messageBiz = messageBiz;
	}

}
