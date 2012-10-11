package com.fordays.masssending.forum.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.SectionListForm;
import com.neza.exception.AppException;

/**
 * 版块管理业务接口
 */
public interface SectionBiz {
	public List getSections() throws AppException;

	public List getSectionsByStatus(long status) throws AppException;

	public List getSections(SectionListForm sectionlist) throws AppException;

	public void printSectionsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception;

	public List getSectionsByForumId(long forumId) throws AppException;

	public long save(Section Section) throws AppException;

	public long saveAsForum(Section section) throws AppException;

	public long merge(Section section) throws AppException;

	public long update(Section section) throws AppException;

	public long updateAsForum(Section section) throws AppException;

	public long updateAsForumName(Section section) throws AppException;

	public Section getSectionById(long id) throws AppException;

	public void deleteById(long id) throws AppException;
	public void hideById(long id) throws AppException;

	public Section setTempForm(Section section) throws AppException;
}
