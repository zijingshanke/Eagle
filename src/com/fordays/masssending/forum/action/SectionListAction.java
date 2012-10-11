package com.fordays.masssending.forum.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.SectionListForm;
import com.fordays.masssending.forum.TopicListForm;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.fordays.masssending.forum.biz.SectionBiz;
import com.fordays.masssending.forum.biz.TopicBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class SectionListAction extends BaseAction {
	private SectionBiz sectionBiz;
	private ForumBiz forumBiz;
	private TopicBiz topicBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		SectionListForm slf = (SectionListForm) form;

		int id = 0;
		if (slf.getSelectedItems().length > 0) {
			id = slf.getSelectedItems()[0];
		} else
			id = slf.getId();
		if (id > 0) {
			Section section = (Section) sectionBiz.getSectionById(id);

			request.setAttribute("selectedForumId", section.getForum().getId());// 选中的论坛
			request.setAttribute("selectedSectionId", section.getId());// 选中的版块

			request.setAttribute("forumlist", forumBiz.getForums());// 论坛列表
			request.setAttribute("sectionlist", sectionBiz.getSections());// 版块列表

			section.setThisAction("update");
			request.setAttribute("section", section);
		} else
			request.setAttribute("section", new Section());
		forwardPage = "editsection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		SectionListForm slf = (SectionListForm) form;
		int id = slf.getId();
		if (id > 0) {
			Section section = (Section) sectionBiz.getSectionById(id);
			section.setThisAction("view");
			request.setAttribute("section", section);

			TopicListForm tlf = new TopicListForm();
			tlf.setSectionId(section.getId());
			List sectionlist = topicBiz.getTopics(tlf);
			tlf.setList(sectionlist);
			request.setAttribute("tlf", tlf);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewsection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Section section = new Section();
		section.setThisAction("insert");
		section.setStatus(new Long(1));

		request.setAttribute("forumlist", forumBiz
				.getForumsByStatus(new Long(1)));// 论坛列表
		request.setAttribute("sectionlist", sectionBiz
				.getSectionsByStatus(new Long(1)));// 版块列表

		request.setAttribute("section", section);
		String forwardPage = "editsection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		SectionListForm slf = (SectionListForm) form;
		if (slf == null)
			slf = new SectionListForm();
		slf.setList(sectionBiz.getSections(slf));

		request.setAttribute("slf", slf);
		forwardPage = "listsection";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward ajaxList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			sectionBiz.printSectionsHtmlByAjaxList(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		SectionListForm slf = (SectionListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < slf.getSelectedItems().length; i++) {
				id = slf.getSelectedItems()[i];
				if (id > 0)
					// sectionBiz.deleteById(id);
					sectionBiz.hideById(id);
			}
			inf.setMessage("删除论坛成功!");
			inf.setForwardPage("/forum/sectionlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除论坛失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}

	public void setSectionBiz(SectionBiz sectionBiz) {
		this.sectionBiz = sectionBiz;
	}

	public void setTopicBiz(TopicBiz topicBiz) {
		this.topicBiz = topicBiz;
	}
}
