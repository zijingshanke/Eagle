package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ForumAction extends BaseAction {
	private ForumBiz forumBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Forum forum = (Forum) form;
		Inform inf = new Inform();
		try {
			Forum tempForum = forumBiz.setTempForm(forum);

			forumBiz.update(tempForum);

			request.setAttribute("forum", tempForum);
			inf.setMessage("你已成功更新了论坛");
			inf.setForwardPage("/forum/forumlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新论坛出错！错误信息是:" + ex.getMessage());
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
		Forum forum = (Forum) form;
		Inform inf = new Inform();

		try {
			Forum tempForum = forumBiz.setTempForm(forum);

			forumBiz.save(tempForum);
			request.setAttribute("Forum", tempForum);
			inf.setMessage("你已成功增加了论坛");
			inf.setForwardPage("/forum/forumlist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加论坛失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}

}
