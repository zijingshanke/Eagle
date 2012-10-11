package com.fordays.masssending.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.biz.TopicBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class TopicAction extends BaseAction {
	private TopicBiz topicBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Topic topic = (Topic) form;
		Inform inf = new Inform();
		try {
			Topic tempTopic = topicBiz.setTempForm(topic);

			topicBiz.updateAsSection(tempTopic);

			request.setAttribute("topic", tempTopic);
			inf.setMessage("你已成功更新了主题");
			inf.setForwardPage("/forum/topiclist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新主题出错！错误信息是:" + ex.getMessage());
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
		Topic topic = (Topic) form;
		Inform inf = new Inform();

		try {
			Topic tempTopic = topicBiz.setTempForm(topic);

			topicBiz.saveAsSection(tempTopic);
			request.setAttribute("Topic", tempTopic);
			inf.setMessage("你已成功增加了主题");
			inf.setForwardPage("/forum/topiclist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加主题失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insertBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		Topic topic = (Topic) form;

		String[] names = request.getParameterValues("name");
		String[] values = request.getParameterValues("value");

		try {
			if (names != null && values != null) {
				int arrayLen = names.length;
				System.out.println("arrayLength:" + arrayLen);

				for (int i = 0; i < arrayLen; i++) {
					String name = names[i];
					String value = values[i];

					topic.setName(name);
					topic.setValue(value);
					Topic tempTopic = topicBiz.setTempForm(topic);
					topicBiz.saveAsSection(tempTopic);
					request.setAttribute("Topic", tempTopic);
				}
				inf.setMessage("你已成功增加了主题");
				inf.setForwardPage("/forum/sectionlist.do?thisAction=view&id="
						+ topic.getSectionId());
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("获取主题信息失败");
				inf.setBack(true);
			}
		} catch (Exception ex) {
			inf.setMessage("增加主题失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setTopicBiz(TopicBiz topicBiz) {
		this.topicBiz = topicBiz;
	}
}
