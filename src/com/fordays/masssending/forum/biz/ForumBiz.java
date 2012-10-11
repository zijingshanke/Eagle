package com.fordays.masssending.forum.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.ForumListForm;
import com.neza.exception.AppException;

/**
 * 论坛管理业务接口
 */
public interface ForumBiz {
	public List getForums() throws AppException;

	public List getForumsByStatus(long status) throws AppException;

	public List getForums(ForumListForm forumlist) throws AppException;

	public long save(Forum forum) throws AppException;

	public long merge(Forum forum) throws AppException;

	public long update(Forum forum) throws AppException;

	public Forum getForumById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

	public Forum setTempForm(Forum forum) throws AppException;

	public HttpServletRequest putForumSectionListAttribute(
			HttpServletRequest request) throws AppException;

	public HttpServletRequest putForumSectionListAttributeByStatus(
			HttpServletRequest request, long status) throws AppException;
}
