package com.fordays.masssending.forum.dao;

import java.util.List;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.TopicListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface TopicDAO extends BaseDAO {
	public List list() throws AppException;

	public List listByStatus(long status) throws AppException;

	public List list(TopicListForm tlf) throws AppException;

	public List getTopicsBySectionId(long topicId) throws AppException;

	public List getTopicsBySectionIdAndStatus(long topicId, long status)
			throws AppException;

	public long save(Topic topic) throws AppException;

	public long merge(Topic topic) throws AppException;

	public long update(Topic topic) throws AppException;

	public Topic getTopicById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
