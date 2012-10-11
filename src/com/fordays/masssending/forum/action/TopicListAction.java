package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.TopicListForm;
import com.fordays.masssending.forum.biz.ForumBiz;
import com.fordays.masssending.forum.biz.TopicBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class TopicListAction extends BaseAction {
	private TopicBiz topicBiz;
	private ForumBiz forumBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		TopicListForm tlf = (TopicListForm) form;

		int id = 0;
		if (tlf.getSelectedItems().length > 0) {
			id = tlf.getSelectedItems()[0];
		} else
			id = tlf.getId();
		if (id > 0) {
			Topic topic = (Topic) topicBiz.getTopicById(id);

			request = forumBiz.putForumSectionListAttribute(request);// 论坛、版块列表

			topic.setThisAction("update");
			request.setAttribute("topic", topic);

		} else
			request.setAttribute("topic", new Topic());
		forwardPage = "edittopic";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		TopicListForm tlf = (TopicListForm) form;
		int id = tlf.getId();
		if (id > 0) {
			Topic topic = (Topic) topicBiz.getTopicById(id);
			topic.setThisAction("view");
			request.setAttribute("topic", topic);
		} else {
			forwardPage = "login";
		}
		forwardPage = "viewtopic";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Topic topic = new Topic();
		topic.setThisAction("insert");
		topic.setStatus(new Long(1));

		request = forumBiz.putForumSectionListAttributeByStatus(request,
				new Long(1));// 论坛、版块列表

		request.setAttribute("topic", topic);
		String forwardPage = "edittopic";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 批量添加主题
	 */
	public ActionForward addBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Topic topic = new Topic();
		topic.setThisAction("insertBatch");
		topic.setStatus(new Long(1));

		request = forumBiz.putForumSectionListAttributeByStatus(request,
				new Long(1));// 论坛、版块列表

		request.setAttribute("topic", topic);
		String forwardPage = "edittopicBatch";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		TopicListForm tlf = (TopicListForm) form;
		if (tlf == null)
			tlf = new TopicListForm();
		tlf.setList(topicBiz.getTopics(tlf));

		request.setAttribute("tlf", tlf);
		forwardPage = "listtopic";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward ajaxList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		try {
			topicBiz.printTopicsHtmlByAjaxList(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		TopicListForm tlf = (TopicListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < tlf.getSelectedItems().length; i++) {
				id = tlf.getSelectedItems()[i];
				if (id > 0)
					// topicBiz.deleteById(id);
					topicBiz.hideById(id);
			}
			inf.setMessage("删除主题成功!");
			inf.setForwardPage("/forum/topiclist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除主题失败，提示信息：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setTopicBiz(TopicBiz topicBiz) {
		this.topicBiz = topicBiz;
	}

	public void setForumBiz(ForumBiz forumBiz) {
		this.forumBiz = forumBiz;
	}
}
