package com.fordays.masssending.message.biz;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageListForm;
import com.fordays.masssending.message.dao.MessageDAO;
import com.neza.exception.AppException;

/**
 * 帖子管理业务实现类
 */
public class MessageBizImp implements MessageBiz {
	private MessageDAO messageDAO;

	public MessageInfo getMessageInfoAsMessageListForm(MessageListForm mlf)
			throws AppException {
		MessageInfo messageInfo = new MessageInfo();
		return messageInfo;
	}

	public void deleteById(long id) throws AppException {
		messageDAO.deleteById(id);
	}

	public void hideById(long id) throws AppException {
		messageDAO.hideById(id);
	}

	public Message getMessageById(long id) throws AppException {
		return messageDAO.getMessageById(id);
	}

	public List list(MessageListForm messagelist) throws AppException {
		return messageDAO.list(messagelist);
	}

	public long merge(Message message) throws AppException {
		return messageDAO.merge(message);
	}

	public long save(Message message) throws AppException {
		return messageDAO.save(message);
	}

	public long update(Message message) throws AppException {
		return messageDAO.update(message);
	}

	public List getMessages() throws AppException {
		return messageDAO.list();
	}

	public List getMessagesByStatus(long status) throws AppException {
		return messageDAO.listByStatus(status);
	}

	public List getMessages(MessageListForm ulf) throws AppException {
		return messageDAO.list(ulf);
	}

	public Message setTempForm(Message form) throws AppException {
		Message tempMessage = (Message) getMessageById(form.getId());

		tempMessage.setTitle(form.getTitle());
		tempMessage.setTopic(form.getTopic());
		tempMessage.setContent(form.getContent());
		tempMessage.setStatus(form.getStatus());
		return tempMessage;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

}
