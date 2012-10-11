package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.forum.ForumListForm;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.fordays.masssending.forum.biz.SectionBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class SectionAction extends BaseAction {
	private SectionBiz sectionBiz;
	private ForumBiz forumBiz;

	// 选择论坛
	public ActionForward selectForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ForumListForm forumlist = new ForumListForm();
		forumlist.setStatus(new Long(1));

		forumlist.setList(forumBiz.getForums(forumlist));
		request.setAttribute("flf", forumlist);
		forwardPage = "selectforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Section section = (Section) form;
		Inform inf = new Inform();
		try {
			Section tempSection = sectionBiz.setTempForm(section);

			sectionBiz.updateAsForum(tempSection);

			request.setAttribute("section", tempSection);
			inf.setMessage("你已成功更新了版块");
			inf.setForwardPage("/forum/sectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新版块出错！错误信息是:" + ex.getMessage());
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
		Section section = (Section) form;
		Inform inf = new Inform();

		try {
			Section tempSection = sectionBiz.setTempForm(section);

			sectionBiz.saveAsForum(tempSection);
			request.setAttribute("Section", tempSection);
			inf.setMessage("你已成功增加了版块");
			inf.setForwardPage("/forum/sectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加版块失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setSectionBiz(SectionBiz sectionBiz) {
		this.sectionBiz = sectionBiz;
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}

}
