package com.fordays.masssending.message.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageListForm;
import com.fordays.masssending.message.MessageInfo;
import com.neza.exception.AppException;

/**
 * 发帖任务管理业务接口
 */
public interface AssignMessageBiz {

	public MessageInfo checkMessageInfo(MessageInfo messageInfo)
			throws AppException;

	public void executeSendMessage(AssignMessageListForm amlf)
			throws AppException;

	public void executeSendMessageASpace(AssignMessageListForm amlf)
			throws AppException;

	public void executeReplyMessage(AssignMessageListForm amlf)
			throws AppException;

	public MessageInfo executeSendMessage(MessageInfo messageInfo)
			throws AppException;

	public MessageInfo getMessageInfoByAssignMessage(AssignMessage assignMessage)
			throws AppException;

	public MessageInfo getMessageInfoByAssignMessageId(long assignMessageId)
			throws AppException;

	public void updateAssignMessageASMessageInfo(MessageInfo messageInfo)
			throws AppException;

	public List getAssignMessages() throws AppException;

	public List getAssignMessagesByStatus(long status) throws AppException;

	public List getAssignMessages(AssignMessageListForm assignmentlist)
			throws AppException;

	public List assignMessageCounter(AssignMessageListForm assignmentlist)
			throws AppException;

	public long save(AssignMessage assignmessage) throws AppException;

	public long saveAsAssignMessage(AssignMessage assignMessage)
			throws AppException;

	public long saveAssignMessageLog(AssignMessage assignMessage, String logType)
			throws AppException;

	public long merge(AssignMessage assignmessage) throws AppException;

	public long update(AssignMessage assignmessage) throws AppException;

	public long updateAsAssignMessage(AssignMessage assignMessage)
			throws AppException;

	public AssignMessage getAssignMessageById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public void hideById(long id) throws AppException;

	public AssignMessage getTempAssignMessage(AssignMessage assignmessage)
			throws AppException;

	public HttpServletRequest putAttributeAsAssignMessage(
			HttpServletRequest request) throws AppException;

	public HttpServletRequest putAttributeAsAssignMessageByStatus(
			HttpServletRequest request, long status) throws AppException;

	public List<AssignMessage> getassignMessageByBatchRequest(
			HttpServletRequest request, AssignMessage assignMessage)
			throws AppException;
}
