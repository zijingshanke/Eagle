package com.fordays.masssending.forum.dao;

import java.util.List;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.ForumListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ForumDAO extends BaseDAO {
	public List list() throws AppException;

	public List listByStatus(long status) throws AppException;

	public List list(ForumListForm forumlist) throws AppException;

	public long save(Forum forum) throws AppException;

	public long merge(Forum forum) throws AppException;

	public long update(Forum forum) throws AppException;

	public Forum getForumById(long id) throws AppException;

	public Forum getForumByName(String name) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
