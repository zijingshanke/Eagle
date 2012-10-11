package com.fordays.masssending.forum.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.ForumListForm;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.dao.ForumDAO;
import com.fordays.masssending.forum.dao.SectionDAO;
import com.fordays.masssending.forum.dao.TopicDAO;
import com.neza.exception.AppException;

/**
 * 论坛管理业务实现类
 */
public class ForumBizImp implements ForumBiz {
	private ForumDAO forumDAO;
	private SectionDAO sectionDAO;
	private TopicDAO topicDAO;

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void deleteById(long id) throws AppException {
		forumDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		forumDAO.hideById(id);
	}

	public Forum getForumById(long id) throws AppException {
		return forumDAO.getForumById(id);
	}

	public List list(ForumListForm forumlist) throws AppException {
		return forumDAO.list(forumlist);
	}

	public long merge(Forum forum) throws AppException {
		return forumDAO.merge(forum);
	}

	public long save(Forum forum) throws AppException {
		return forumDAO.save(forum);
	}

	public long update(Forum forum) throws AppException {
		return forumDAO.update(forum);
	}

	public List getForums() throws AppException {
		return forumDAO.list();
	}

	public List getForumsByStatus(long status) throws AppException {
		return forumDAO.listByStatus(status);
	}

	public List getForums(ForumListForm ulf) throws AppException {
		return forumDAO.list(ulf);
	}

	public Forum setTempForm(Forum forum) throws AppException {
		Forum tempForum = (Forum) getForumById(forum.getId());
		tempForum.setName(forum.getName());
		tempForum.setLoginSite(forum.getLoginSite());
		tempForum.setLoginPort(forum.getLoginPort());
		tempForum.setStatus(forum.getStatus());
		return tempForum;
	}

	/**
	 * @新增操作调用
	 * @将论坛、版块、主题列表保存进Attribute
	 * 
	 */
	public HttpServletRequest putForumSectionListAttributeByStatus(
			HttpServletRequest request, long status) throws AppException {
		List forumList = getForumsByStatus(status);
		Forum firstForum = (Forum) forumList.get(0);

		List sectionList = sectionDAO.getSectionsByForumIdAndStatus(firstForum
				.getId(), new Long(1));
		Section firstSection = (Section) sectionList.get(0);

		List topicList = topicDAO.getTopicsBySectionIdAndStatus(firstSection
				.getId(), new Long(1));

		request.setAttribute("forumlist", forumList);// 论坛列表(全部)
		request.setAttribute("sectionlist", sectionList);// 版块列表(根据论坛)
		request.setAttribute("topiclist", topicList);// 主题列表(根据版块)

		return request;
	}

	/**
	 * @编辑操作调用
	 * @将论坛、版块、主题列表保存进Attribute
	 * 
	 */
	public HttpServletRequest putForumSectionListAttribute(
			HttpServletRequest request) throws AppException {
		List forumList = getForums();
		Forum firstForum = (Forum) forumList.get(0);

		List sectionList = sectionDAO.getSectionsByForumId(firstForum.getId());
		Section firstSection = (Section) sectionList.get(0);

		List topicList = topicDAO.getTopicsBySectionId(firstSection.getId());

		request.setAttribute("forumlist", forumList);// 论坛列表(全部)
		request.setAttribute("sectionlist", sectionList);// 版块列表(根据论坛)
		request.setAttribute("topiclist", topicList);// 主题列表(根据版块)

		return request;
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	public void setSectionDAO(SectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

}
