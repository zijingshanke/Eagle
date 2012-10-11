package com.fordays.masssending.message.biz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.dao.AccountDAO;
import com.fordays.masssending.forum.dao.ForumDAO;
import com.fordays.masssending.forum.dao.SectionDAO;
import com.fordays.masssending.forum.dao.TopicDAO;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.MessageSectionListForm;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.dao.MessageSectionDAO;
import com.fordays.masssending.message.dao.MessageDAO;
import com.neza.exception.AppException;

/**
 * 帖子版块业务实现类
 */
public class MessageSectionBizImp implements MessageSectionBiz {
	private MessageSectionDAO messageSectionDAO;
	private ForumDAO forumDAO;
	private AccountDAO accountDAO;
	private SectionDAO sectionDAO;
	private TopicDAO topicDAO;
	private MessageDAO messageDAO;

	/**
	 * 将MessageSectionList、TopicList、AccountList放入Attribute
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return HttpServletRequest requests
	 */
	public HttpServletRequest putAttributeAsMessageSection(
			HttpServletRequest request) throws AppException {
		List messageSectionlist = getMessageSections();
		request.setAttribute("messageSectionlist", messageSectionlist);// 帖子版块列表

		MessageSection messageSection = (MessageSection) messageSectionlist
				.get(0);

		Section section = messageSection.getSection();
		long forumId = section.getForum().getId();
		long sectionId = section.getId();

		List topiclist = topicDAO.getTopicsBySectionId(sectionId);
		request.setAttribute("topiclist", topiclist);// 主题列表(根据版块)

		List accountlist = accountDAO.getAccountByForumId(forumId);
		request.setAttribute("accountlist", accountlist);// 帐号列表(根据帖子版块)

		return request;
	}

	public HttpServletRequest putAttributeAsMessageSectionByStatus(
			HttpServletRequest request, long status) throws AppException {
		List messageSectionlist = getMessageSectionsByStatus(status);
		request.setAttribute("messageSectionlist", messageSectionlist);// 帖子版块列表

		MessageSection messageSection = (MessageSection) messageSectionlist
				.get(0);

		Section section = messageSection.getSection();
		long forumId = section.getForum().getId();
		long sectionId = section.getId();

		List topiclist = topicDAO.getTopicsBySectionIdAndStatus(sectionId,
				new Long(1));
		request.setAttribute("topiclist", topiclist);// 主题列表(根据版块)

		List accountlist = accountDAO.getAccountByForumIdAndStatus(forumId,
				new Long(1));
		request.setAttribute("accountlist", accountlist);// 帐号列表(根据帖子版块)

		return request;
	}

	/**
	 * 编辑操作调用,设置选中项
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param MessageSection
	 *            messageSection
	 */
	public HttpServletRequest pubSelectedAttributeAsMessageSection(
			HttpServletRequest request, MessageSection messageSection)
			throws AppException {
		Section section = messageSection.getSection();
		Forum forum = section.getForum();
		Topic topic = messageSection.getTopic();
		Message message = messageSection.getMessage();

		long selectedForumId = forum.getId();
		long selectedSectionId = section.getId();
		long selectedTopicId = topic.getId();
		long selectedMessageId = message.getId();

		request.setAttribute("selectedForumId", selectedForumId);
		request.setAttribute("selectedSectionId", selectedSectionId);
		request.setAttribute("selectedTopicId", selectedTopicId);
		request.setAttribute("selectedMessageId", selectedMessageId);

		request.setAttribute("forumlist", forumDAO.list());
		request.setAttribute("sectionlist", sectionDAO
				.getSectionsByForumId(selectedForumId));
		request.setAttribute("topiclist", topicDAO
				.getTopicsBySectionId(selectedSectionId));
		request.setAttribute("messagelist", messageDAO.list());

		return request;
	}

	public void deleteById(long id) throws AppException {
		messageSectionDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		messageSectionDAO.hideById(id);
	}

	public MessageSection getMessageSectionById(long id) throws AppException {
		return messageSectionDAO.getMessageSectionById(id);
	}

	public List getMessageSectionBySectionId(MessageSectionListForm amlf)
			throws AppException {
		return messageSectionDAO.getMessageSectionBySectionId(amlf);
	}

	public List list(MessageSectionListForm messageSectionlist)
			throws AppException {
		return messageSectionDAO.list(messageSectionlist);
	}

	public long merge(MessageSection messageSection) throws AppException {
		return messageSectionDAO.merge(messageSection);
	}

	public long save(MessageSection messageSection) throws AppException {
		return messageSectionDAO.save(messageSection);
	}

	public long saveAsMessageSection(MessageSection messageSection)
			throws AppException {
		messageSection = getTempMessageSection(messageSection);
		return save(messageSection);
	}

	public long update(MessageSection messageSection) throws AppException {
		return messageSectionDAO.update(messageSection);
	}

	public long updateAsMessageSection(MessageSection messageSection)
			throws AppException {
		messageSection = getTempMessageSection(messageSection);
		return update(messageSection);
	}

	/**
	 * 根据sectionId和messageId重新组装MessageSection
	 */
	private MessageSection getTempMessageSection(MessageSection messageSection)
			throws AppException {
		long sectionId = messageSection.getSectionId();
		long topicId = messageSection.getTopicId();
		long messageId = messageSection.getMessageId();

		Section section = sectionDAO.getSectionById(sectionId);
		Topic topic = topicDAO.getTopicById(topicId);
		Message message = messageDAO.getMessageById(messageId);

		messageSection.setSection(section);
		messageSection.setTopic(topic);
		messageSection.setMessage(message);

		// messageSection.getAssignMessages().clear();
		messageSection.setAssignMessages(new HashSet<AssignMessage>());
		// AssignMessage assignMessage = new AssignMessage();
		// assignMessage.setAssignment(new Assignment());
		// assignMessage.setMessageSection(messageSection);
		// messageSection.getAssignMessages().add(assignMessage);
		return messageSection;
	}

	public List getMessageSections() throws AppException {
		return messageSectionDAO.list();
	}

	public List getMessageSectionsByStatus(long status) throws AppException {
		return messageSectionDAO.listByStatus(status);
	}

	public List getMessageSections(MessageSectionListForm ulf)
			throws AppException {
		return messageSectionDAO.list(ulf);
	}

	public MessageSection setTempForm(MessageSection form) throws AppException {
		MessageSection tempMessageSection = (MessageSection) getMessageSectionById(form
				.getId());

		tempMessageSection.setStatus(form.getStatus());

		tempMessageSection.setSectionId(form.getSectionId());
		tempMessageSection.setTopicId(form.getTopicId());
		tempMessageSection.setMessageId(form.getMessageId());

		return tempMessageSection;
	}

	/**
	 * 
	 */
	public List<MessageSection> getMessageSectionByBatchRequest(
			HttpServletRequest request, MessageSection messageSection)
			throws AppException {
		String[] forumIds = request.getParameterValues("forumId");
		String[] sectionIds = request.getParameterValues("sectionId");
		String[] topicIds = request.getParameterValues("topicId");
		String[] messageIds = request.getParameterValues("messageId");

		int arrayLen = forumIds.length;
		System.out.println("arrayLength:" + arrayLen);

		List<MessageSection> mslist = new ArrayList<MessageSection>();

		for (int i = 0; i < arrayLen; i++) {
			String forumId = forumIds[i];
			String sectionId = sectionIds[i];
			String topicId = topicIds[i];
			String messageId = messageIds[i];
			//
			System.out.println(i + "-forumId:" + forumId);
			System.out.println(i + "-sectionId:" + sectionId);
			System.out.println(i + "-topicId:" + topicId);
			System.out.println(i + "-messageId:" + messageId);

			MessageSection tempMessageSection = new MessageSection();
			long status = messageSection.getStatus();
			tempMessageSection.setStatus(status);
			tempMessageSection.setSectionId(Long.parseLong(sectionId.trim()));
			tempMessageSection.setTopicId(Long.parseLong(topicId.trim()));
			tempMessageSection.setMessageId(Long.parseLong(messageId.trim()));
			mslist.add(tempMessageSection);
		}
		return mslist;
	}

	public void setMessageSectionDAO(MessageSectionDAO messageSectionDAO) {
		this.messageSectionDAO = messageSectionDAO;
	}

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	public void setSectionDAO(SectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

}
