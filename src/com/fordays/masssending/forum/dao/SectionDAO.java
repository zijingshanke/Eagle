package com.fordays.masssending.forum.dao;

import java.util.List;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.SectionListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface SectionDAO extends BaseDAO {
	public List list() throws AppException;

	public List listByStatus(long status) throws AppException;

	public List list(SectionListForm forumlist) throws AppException;

	public List getSectionsByForumId(long forumId) throws AppException;

	public List getSectionsByForumIdAndStatus(long forumId,long status) throws AppException;

	public long save(Section Section) throws AppException;

	public long merge(Section Section) throws AppException;

	public long update(Section Section) throws AppException;

	public Section getSectionById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
