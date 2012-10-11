package com.fordays.masssending.message.dao;

import java.util.List;

import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface MessageDAO extends BaseDAO {
	public List list() throws AppException;
	public List listByStatus(long status) throws AppException;
	public List list(MessageListForm forumlist) throws AppException;

	public long save(Message message) throws AppException;

	public long merge(Message message) throws AppException;

	public long update(Message message) throws AppException;

	public Message getMessageById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;
}
