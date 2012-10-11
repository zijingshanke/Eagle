package com.fordays.masssending.forum.biz;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.TopicListForm;
import com.fordays.masssending.forum.dao.SectionDAO;
import com.fordays.masssending.forum.dao.TopicDAO;
import com.neza.exception.AppException;

/**
 * 主题管理业务实现类
 */
public class TopicBizImp implements TopicBiz {
	private TopicDAO topicDAO;
	private SectionDAO sectionDAO;

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void deleteById(long id) throws AppException {
		topicDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		topicDAO.hideById(id);
	}

	public Topic getTopicById(long id) throws AppException {
		return topicDAO.getTopicById(id);
	}

	public List list(TopicListForm topiclist) throws AppException {
		return topicDAO.list(topiclist);
	}

	public long merge(Topic topic) throws AppException {
		return topicDAO.merge(topic);
	}

	public long save(Topic topic) throws AppException {
		return topicDAO.save(topic);
	}

	public long saveAsSection(Topic topic) throws AppException {
		topic = getTempTopic(topic);
		return save(topic);
	}

	public long update(Topic topic) throws AppException {
		return topicDAO.update(topic);
	}

	public long updateAsSection(Topic topic) throws AppException {
		topic = getTempTopic(topic);
		return update(topic);
	}

	/**
	 * 根据sectiond重新组装Topic
	 */
	private Topic getTempTopic(Topic topic) throws AppException {
		long forumId = topic.getSectionId();
		Section section = sectionDAO.getSectionById(forumId);

		topic.setSection(section);
		return topic;
	}

	public List getTopics() throws AppException {
		return topicDAO.list();
	}

	public List getTopicsByStatus(long status) throws AppException {
		return topicDAO.listByStatus(status);
	}

	public List getTopics(TopicListForm ulf) throws AppException {
		return topicDAO.list(ulf);
	}

	public List getTopicsBySectionId(long forumId) throws AppException {
		return topicDAO.getTopicsBySectionId(forumId);
	}

	public List getTopicsBySectionIdAndStatus(long forumId, long status)
			throws AppException {
		return topicDAO.getTopicsBySectionIdAndStatus(forumId, status);
	}

	public Topic setTempForm(Topic topic) throws AppException {
		Topic tempTopic = (Topic) getTopicById(topic.getId());
		tempTopic.setName(topic.getName());
		tempTopic.setValue(topic.getValue());
		tempTopic.setStatus(topic.getStatus());
		tempTopic.setSectionId(topic.getSectionId());
		return tempTopic;
	}

	public TopicDAO getTopicDAO() {
		return topicDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	public SectionDAO getSectionDAO() {
		return sectionDAO;
	}

	public void setSectionDAO(SectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}

	public void printTopicsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String sectionIdStr = request.getParameter("sectionId");
		String batchFlagStr = request.getParameter("batchFlag");

		String select_xml_start = "<select name=\"topicId\" class=\"lef\" >";

		if (batchFlagStr != null && "".equals(batchFlagStr) == false) {
			batchFlagStr = batchFlagStr.trim();
			select_xml_start = "<select name=\"topicId\" id=\"topicId"
					+ batchFlagStr + "\"  class=\"lef\" >";
		}

		String select_xml_end = "</select>";
		String xml = "";
		String last_xml = "";

		Long sectionId = Long.parseLong(sectionIdStr);
		List topicList = getTopicsBySectionIdAndStatus(sectionId, new Long(1));

		if (topicList.size() == 0) {
			// -----
		} else {
			Iterator it = topicList.iterator();
			while (it.hasNext()) {
				Topic section = (Topic) it.next();
				xml += "<option value=" + "\"" + section.getId() + "\"" + ">";
				xml += section.getName() + "</option>";
			}
			last_xml = select_xml_start + xml + select_xml_end;
		}
		out.print(last_xml);
	}

}
