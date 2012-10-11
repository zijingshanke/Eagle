package com.fordays.masssending.forum.biz;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.SectionListForm;
import com.fordays.masssending.forum.dao.ForumDAO;
import com.fordays.masssending.forum.dao.SectionDAO;
import com.neza.exception.AppException;

/**
 * 版块管理业务实现类
 */
public class SectionBizImp implements SectionBiz {
	private SectionDAO sectionDAO;
	private ForumDAO forumDAO;

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void deleteById(long id) throws AppException {
		sectionDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		sectionDAO.hideById(id);
	}

	public Section getSectionById(long id) throws AppException {
		return sectionDAO.getSectionById(id);
	}

	public List list(SectionListForm sectionlist) throws AppException {
		return sectionDAO.list(sectionlist);
	}

	public long merge(Section section) throws AppException {
		return sectionDAO.merge(section);
	}

	public long save(Section section) throws AppException {
		return sectionDAO.save(section);
	}

	public long saveAsForum(Section section) throws AppException {
		section = getTempSectionAsForumId(section);
		return save(section);
	}

	public long update(Section section) throws AppException {
		return sectionDAO.update(section);
	}

	public long updateAsForum(Section section) throws AppException {
		section = getTempSectionAsForumId(section);
		return update(section);
	}

	public long updateAsForumName(Section section) throws AppException {
		section = getTempSectionAsForumName(section);
		return update(section);
	}

	/**
	 * 根据forumId重新组装Section
	 */
	private Section getTempSectionAsForumId(Section section)
			throws AppException {
		long forumId = section.getForumId();
		Forum forum = forumDAO.getForumById(forumId);

		section.setForum(forum);
		return section;
	}

	/**
	 * 根据forumName重新组装Section
	 */
	private Section getTempSectionAsForumName(Section section)
			throws AppException {
		String forumName = section.getForumName();
		Forum forum = forumDAO.getForumByName(forumName);

		section.setForum(forum);
		return section;
	}

	public List getSections() throws AppException {
		return sectionDAO.list();
	}

	public List getSectionsByStatus(long status) throws AppException {
		return sectionDAO.listByStatus(status);
	}

	public List getSections(SectionListForm ulf) throws AppException {
		return sectionDAO.list(ulf);
	}

	public List getSectionsByForumId(long forumId) throws AppException {
		return sectionDAO.getSectionsByForumId(forumId);
	}

	public List getSectionsByForumIdAndStatus(long forumId, long status)
			throws AppException {
		return sectionDAO.getSectionsByForumIdAndStatus(forumId, status);
	}

	public Section setTempForm(Section section) throws AppException {
		Section tempSection = (Section) getSectionById(section.getId());
		tempSection.setSectionUrl(section.getSectionUrl());
		tempSection.setStatus(section.getStatus());
		tempSection.setForumId(section.getForumId());
		if ("insert".equals(section.getThisAction())) {
			tempSection.setName(section.getName());
		}
		return tempSection;
	}

	public void printSectionsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String forumIdStr = request.getParameter("forumId").trim();
		String batchFlagStr = request.getParameter("batchFlag");

		String select_xml_start = "<select name=\"sectionId\" onchange=\"getTopicBySection()\" class=\"lef\" >";

		if (batchFlagStr != null && "".equals(batchFlagStr) == false) {
			batchFlagStr = batchFlagStr.trim();
			select_xml_start = "<select name=\"sectionId\" id=\"sectionId"
					+ batchFlagStr + "\" onchange=\"getTopicBySectionByFlag("
					+ batchFlagStr + ")\" class=\"lef\" >";
		}

		String select_xml_end = "</select>";
		String xml = "";
		String last_xml = "";

		Long forumId = Long.parseLong(forumIdStr);
		List selectList = getSectionsByForumIdAndStatus(forumId, new Long(1));

		if (selectList.size() == 0) {
			// -----
		} else {
			Iterator it = selectList.iterator();
			while (it.hasNext()) {
				Section section = (Section) it.next();
				xml += "<option value=" + "\"" + section.getId() + "\"" + ">";
				xml += section.getName() + "</option>";
			}
			last_xml = select_xml_start + xml + select_xml_end;
		}
		out.print(last_xml);
	}

	public void setSectionDAO(SectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

}
