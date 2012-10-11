package com.fordays.masssending.message.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.MessageSectionListForm;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.biz.MessageSectionBiz;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.fordays.masssending.forum.biz.SectionBiz;
import com.fordays.masssending.message.biz.MessageBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class MessageSectionListAction extends BaseAction {
	private MessageSectionBiz messageSectionBiz;
	private ForumBiz forumBiz;
	private SectionBiz sectionBiz;
	private MessageBiz messageBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSectionListForm mlf = (MessageSectionListForm) form;

		int id = 0;
		if (mlf.getSelectedItems().length > 0) {
			id = mlf.getSelectedItems()[0];
		} else
			id = mlf.getId();
		if (id > 0) {
			MessageSection messageSection = (MessageSection) messageSectionBiz
					.getMessageSectionById(id);

			messageSection.setThisAction("update");
			request.setAttribute("messageSection", messageSection);

			request = messageSectionBiz.pubSelectedAttributeAsMessageSection(
					request, messageSection);// 相关编辑选项选中
		} else
			request.setAttribute("messageSection", new Message());
		forwardPage = "editmessageSection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSectionListForm mlf = (MessageSectionListForm) form;
		int id = mlf.getId();
		if (id > 0) {
			MessageSection messageSection = (MessageSection) messageSectionBiz
					.getMessageSectionById(id);
			messageSection.setThisAction("view");
			request.setAttribute("messageSection", messageSection);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewmessageSection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		MessageSection messageSection = new MessageSection();
		messageSection.setThisAction("insert");
		messageSection.setStatus(new Long(1));

		request.setAttribute("messageSection", messageSection);

		request = forumBiz.putForumSectionListAttributeByStatus(request,
				new Long(1));// 论坛、版块、主题列表(全部)

		request.setAttribute("messagelist", messageBiz
				.getMessagesByStatus(new Long(1)));// 帖子列表(全部)

		String forwardPage = "editmessageSection";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量添加发帖目标
	 */
	public ActionForward addBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		MessageSection messageSection = new MessageSection();
		messageSection.setThisAction("insertBatch");
		messageSection.setStatus(new Long(1));

		request.setAttribute("messageSection", messageSection);

		request = forumBiz.putForumSectionListAttributeByStatus(request,
				new Long(1));// 论坛、版块、主题列表(全部)

		request.setAttribute("messagelist", messageBiz
				.getMessagesByStatus(new Long(1)));// 帖子列表(全部)

		String forwardPage = "editmessageSectionBatch";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		MessageSectionListForm mslf = (MessageSectionListForm) form;
		if (mslf == null)
			mslf = new MessageSectionListForm();

		mslf.setDescById(true);// 按ID倒序显示

		mslf.setList(messageSectionBiz.getMessageSections(mslf));

		request.setAttribute("mslf", mslf);
		forwardPage = "listmessageSection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		MessageSectionListForm mlf = (MessageSectionListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < mlf.getSelectedItems().length; i++) {
				id = mlf.getSelectedItems()[i];
				if (id > 0)
					// messageSectionBiz.deleteById(id);
					messageSectionBiz.hideById(id);
			}
			inf.setMessage("删除发帖目标成功!");
			inf.setForwardPage("/message/messageSectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除发帖目标失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}

	public void setMessageSectionBiz(MessageSectionBiz messageSectionBiz) {
		this.messageSectionBiz = messageSectionBiz;
	}

	public void setSectionBiz(SectionBiz sectionBiz) {
		this.sectionBiz = sectionBiz;
	}

	public void setMessageBiz(MessageBiz messageBiz) {
		this.messageBiz = messageBiz;
	}
}
