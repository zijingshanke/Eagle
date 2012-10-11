package com.fordays.masssending.forum.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.TopicListForm;
import com.neza.exception.AppException;

/**
 * 主题管理业务接口
 */
public interface TopicBiz {
	public List getTopics() throws AppException;
	public List getTopicsByStatus(long status) throws AppException;

	public List getTopics(TopicListForm Topiclist) throws AppException;

	public List getTopicsBySectionId(long sectionId) throws AppException;

	public void printTopicsHtmlByAjaxList(HttpServletRequest request,
			HttpServletResponse response) throws AppException, Exception;

	public long save(Topic topic) throws AppException;

	public long saveAsSection(Topic topic) throws AppException;

	public long merge(Topic topic) throws AppException;

	public long update(Topic topic) throws AppException;

	public long updateAsSection(Topic topic) throws AppException;

	public Topic getTopicById(long id) throws AppException;

	public void deleteById(long id) throws AppException;
	public void hideById(long id) throws AppException;

	public Topic setTempForm(Topic topic) throws AppException;
}
