package com.fordays.masssending.message.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.biz.MessageSectionBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class MessageSectionAction extends BaseAction {
	private MessageSectionBiz messageSectionBiz;

	/**
	 * 更新帖子版块
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSection messageSection = (MessageSection) form;
		Inform inf = new Inform();
		try {
			MessageSection tempMessageSection = messageSectionBiz
					.setTempForm(messageSection);

			messageSectionBiz.updateAsMessageSection(tempMessageSection);

			request.setAttribute("section", tempMessageSection);

			inf.setMessage("你已成功更新了发帖目标");
			inf.setForwardPage("/message/messageSectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新发帖目标出错！错误信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 保存帖子版块
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSection messageSection = (MessageSection) form;
		Inform inf = new Inform();

		try {
			MessageSection tempMessageSection = messageSectionBiz
					.setTempForm(messageSection);

			messageSectionBiz.saveAsMessageSection(tempMessageSection);

			request.setAttribute("section", tempMessageSection);
			inf.setMessage("你已成功增加了发帖目标");
			inf.setForwardPage("/message/messageSectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加发帖目标失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量保存发帖目标
	 */
	public ActionForward insertBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSection messageSection = (MessageSection) form;
		Inform inf = new Inform();

		try {
			List<MessageSection> tempMSlist = messageSectionBiz
					.getMessageSectionByBatchRequest(request, messageSection);

			for (int i = 0; i < tempMSlist.size(); i++) {
				MessageSection tempMessageSection = tempMSlist.get(i);			
				messageSectionBiz.saveAsMessageSection(tempMessageSection);
			}

			inf.setMessage("你已成功批量增加了发帖目标");
			inf.setForwardPage("/message/messageSectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加发帖目标失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setMessageSectionBiz(MessageSectionBiz messageSectionBiz) {
		this.messageSectionBiz = messageSectionBiz;
	}
}
