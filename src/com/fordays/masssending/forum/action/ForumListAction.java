package com.fordays.masssending.forum.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.ForumListForm;
import com.fordays.masssending.forum.SectionListForm;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.fordays.masssending.forum.biz.SectionBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ForumListAction extends BaseAction {
	private ForumBiz forumBiz;
	private SectionBiz sectionBiz;

	// 选择论坛
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ForumListForm flf = (ForumListForm) form;
		flf.setStatus(new Long(1));
		flf.setList(forumBiz.getForums(flf));
		request.setAttribute("flf", flf);
		forwardPage = "selectforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ForumListForm flf = (ForumListForm) form;

		int id = 0;
		if (flf.getSelectedItems().length > 0) {
			id = flf.getSelectedItems()[0];
		} else
			id = flf.getId();
		if (id > 0) {
			Forum forum = (Forum) forumBiz.getForumById(id);

			forum.setThisAction("update");
			request.setAttribute("forum", forum);
		} else {
			request.setAttribute("forum", new Forum());
		}
		forwardPage = "editforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ForumListForm flf = (ForumListForm) form;
		int id = flf.getId();
		if (id > 0) {
			Forum forum = (Forum) forumBiz.getForumById(id);

			forum.setThisAction("view");
			request.setAttribute("forum", forum);

			SectionListForm slf = new SectionListForm();
			slf.setForumId(forum.getId());
			List sectionlist = sectionBiz.getSections(slf);
			slf.setList(sectionlist);
			request.setAttribute("slf", slf);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Forum forum = new Forum();
		forum.setThisAction("insert");
		forum.setStatus(new Long(1));

		request.setAttribute("forum", forum);
		String forwardPage = "editforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ForumListForm flf = (ForumListForm) form;
		if (flf == null)
			flf = new ForumListForm();
		List list = forumBiz.getForums(flf);

		flf.setList(list);

		request.setAttribute("flf", flf);
		forwardPage = "listforum";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		ForumListForm flf = (ForumListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < flf.getSelectedItems().length; i++) {
				id = flf.getSelectedItems()[i];
				if (id > 0)
					// forumBiz.deleteById(id);
					forumBiz.hideById(id);
			}

			inf.setMessage("删除论坛成功!");
			inf.setForwardPage("/forum/forumlist.do");
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

}
