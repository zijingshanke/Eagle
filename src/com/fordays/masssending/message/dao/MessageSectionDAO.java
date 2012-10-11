package com.fordays.masssending.message.dao;

import java.util.List;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.MessageSectionListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface MessageSectionDAO extends BaseDAO {
	public List list() throws AppException;
	public List listByStatus(long status) throws AppException;
	public List list(MessageSectionListForm amlf) throws AppException;

	public List getMessageSectionBySectionId(MessageSectionListForm mslf)
			throws AppException;

	public long save(MessageSection messageSection) throws AppException;

	public long merge(MessageSection messageSection) throws AppException;

	public long update(MessageSection messageSection) throws AppException;

	public MessageSection getMessageSectionById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

}
