package com.fordays.masssending.message.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.MessageSectionListForm;
import com.neza.exception.AppException;

/**
 * 帖子版块业务接口
 */
public interface MessageSectionBiz {

	public HttpServletRequest putAttributeAsMessageSection(
			HttpServletRequest request) throws AppException;

	public HttpServletRequest putAttributeAsMessageSectionByStatus(
			HttpServletRequest request, long status) throws AppException;

	public HttpServletRequest pubSelectedAttributeAsMessageSection(
			HttpServletRequest request, MessageSection messageSection)
			throws AppException;

	public List getMessageSections() throws AppException;

	public List getMessageSectionsByStatus(long status) throws AppException;

	public List getMessageSections(MessageSectionListForm assignmentlist)
			throws AppException;

	public long save(MessageSection messageSection) throws AppException;

	public long saveAsMessageSection(MessageSection messageSection)
			throws AppException;

	public long merge(MessageSection messageSection) throws AppException;

	public long update(MessageSection messageSection) throws AppException;

	public long updateAsMessageSection(MessageSection messageSection)
			throws AppException;

	public MessageSection getMessageSectionById(long id) throws AppException;

	public List getMessageSectionBySectionId(MessageSectionListForm amlf)
			throws AppException;

	public List<MessageSection> getMessageSectionByBatchRequest(
			HttpServletRequest request, MessageSection messageSection)
			throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

	public MessageSection setTempForm(MessageSection messageSection)
			throws AppException;
}
