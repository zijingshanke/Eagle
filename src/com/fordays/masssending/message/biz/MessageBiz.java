package com.fordays.masssending.message.biz;

import java.util.List;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageListForm;
import com.neza.exception.AppException;

/**
 * 发帖系统业务接口
 */
public interface MessageBiz {
	
	public MessageInfo getMessageInfoAsMessageListForm(MessageListForm mlf)throws AppException;	
	
	public List getMessages() throws AppException;
	public List getMessagesByStatus(long status) throws AppException;

	public List getMessages(MessageListForm messagelist) throws AppException;

	public long save(Message message) throws AppException;

	public long merge(Message message) throws AppException;

	public long update(Message message) throws AppException;

	public Message getMessageById(long id) throws AppException;

	public void deleteById(long id) throws AppException;
	public void hideById(long id) throws AppException;
	public Message setTempForm(Message message) throws AppException;
}
