package com.fordays.masssending.message.biz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.masssending.forum.Account;
import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.forum.dao.AccountDAO;
import com.fordays.masssending.message.AssignMessage;
import com.fordays.masssending.message.AssignMessageListForm;
import com.fordays.masssending.message.AssignMessageLog;
import com.fordays.masssending.message.Assignment;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageSection;
import com.fordays.masssending.message.dao.AssignMessageDAO;
import com.fordays.masssending.message.dao.AssignMessageLogDAO;
import com.fordays.masssending.message.dao.AssignmentDAO;
import com.fordays.masssending.message.dao.MessageSectionDAO;
import com.fordays.masssending.message.website.biz.BaiduMessageBizImp;
import com.fordays.masssending.message.website.biz.DospyMessageBizImp;
import com.fordays.masssending.message.website.biz.GdinNetMessageBizImp;
import com.fordays.masssending.message.website.biz.JavaEyeMessageBizImp;
import com.fordays.masssending.message.website.biz.JiNanBBSMessageBizImp;
import com.fordays.masssending.message.website.biz.JinRongjieMessageBizImp;
import com.fordays.masssending.message.website.biz.RayliMessageBizImp;
import com.fordays.masssending.message.website.biz.SHOnlineMessageBizImp;
import com.fordays.masssending.message.website.biz.SinaMessageBizImp;
import com.fordays.masssending.message.website.biz.TTMopMessageBizImp;
import com.fordays.masssending.message.website.biz.TianYaMessageBizImp;
import com.fordays.masssending.message.website.biz.WangyiMessageBizImp;
import com.fordays.masssending.message.website.biz.XinHuaNetMessageBizImp;
import com.fordays.masssending.message.website.biz.ZhuHaiShiChuangMessageBizImp;
import com.fordays.masssending.user.SysUser;
import com.fordays.masssending.user.dao.UserDAO;
import com.neza.exception.AppException;

/**
 * 发帖任务业务实现类
 * 
 */
public class AssignMessageBizImp implements AssignMessageBiz {
	private AssignMessageDAO assignMessageDAO;
	private AssignMessageLogDAO assignMessageLogDAO;
	private MessageSectionDAO messageSectionDAO;
	private AssignmentDAO assignmentDAO;
	private AccountDAO accountDAO;
	private UserDAO userDAO;

	/**
	 * 执行回帖任务
	 * 
	 * @param AssignMessageListForm
	 *            amlf 发帖任务列表
	 */
	public void executeReplyMessage(AssignMessageListForm assignMessagelist)
			throws AppException {

		MessageInfo messageInfo = getMessageInfoByQuickReply(assignMessagelist);
		messageInfo = executeSendMessage(messageInfo);

		if (messageInfo != null) {
			if (messageInfo.isValidated()) {
				// messageInfo.setSendedStatus(new Long(3));
				System.out.println("回帖成功");
			} else {
				System.out.println("回帖失败");
			}
		}

		// int[] selectedItems = assignMessagelist.getSelectedItems();
		// int amlfLength = selectedItems.length;
		//
		// for (int i = 0; i < amlfLength; i++) {
		// int assignMessageId = selectedItems[i];
		//
		// MessageInfo messageInfo =
		// getMessageInfoByAssignMessageId(assignMessageId);
		//
		// messageInfo = checkMessageInfo(messageInfo);
		//
		// if (messageInfo.isValidated()) {
		// messageInfo = executeSendMessage(messageInfo);
		// if (messageInfo != null) {
		// if (messageInfo.isValidated() == false) {
		// messageInfo.setSendedStatus(new Long(3));
		// }
		// updateAssignMessageASMessageInfo(messageInfo);
		// }
		// } else {
		// System.out.println("messageInfo 格式校验错误");
		// }
		// }
	}

	/**
	 * 执行发帖任务
	 * 
	 * @param AssignMessageListForm
	 *            amlf 发帖任务列表
	 */
	public void executeSendMessageASpace(AssignMessageListForm assignMessagelist)
			throws AppException {
		int[] selectedItems = assignMessagelist.getSelectedItems();
		int amlfLength = selectedItems.length;

		for (int i = 0; i < amlfLength; i++) {
			int assignMessageId = selectedItems[i];

			MessageInfo messageInfo = getMessageInfoByAssignMessageId(assignMessageId);

			messageInfo = checkMessageInfo(messageInfo);

			if (messageInfo.isValidated()) {
				messageInfo = executeSendMessage(messageInfo);
				if (messageInfo != null) {
					if (messageInfo.isValidated() == false) {
						messageInfo.setSendedStatus(new Long(3));
					}
					updateAssignMessageASMessageInfo(messageInfo);
				}
			} else {
				System.out.println("messageInfo 格式校验错误");
			}
		}
	}

	/**
	 * 执行发帖任务
	 * 
	 * @param AssignMessageListForm
	 *            amlf 发帖任务列表
	 */
	public void executeSendMessage(AssignMessageListForm assignMessagelist)
			throws AppException {
		int[] selectedItems = assignMessagelist.getSelectedItems();
		int amlfLength = selectedItems.length;

		for (int i = 0; i < amlfLength; i++) {
			int assignMessageId = selectedItems[i];

			MessageInfo messageInfo = getMessageInfoByAssignMessageId(assignMessageId);

			messageInfo = checkMessageInfo(messageInfo);

			if (messageInfo.isValidated()) {
				messageInfo = executeSendMessage(messageInfo);
				if (messageInfo != null) {
					if (messageInfo.isValidated() == false) {
						messageInfo.setSendedStatus(new Long(3));
					}
					updateAssignMessageASMessageInfo(messageInfo);
				}
			} else {
				System.out.println("messageInfo 格式校验错误");
			}
		}
	}

	/**
	 * 根据帖子发送信息更新发帖任务
	 * 
	 * @param MessageInfo
	 *            messageInfo
	 * 
	 */
	public void updateAssignMessageASMessageInfo(MessageInfo messageInfo)
			throws AppException {
		long assignMessageId = messageInfo.getAssignMessageId();
		boolean isValidated = messageInfo.isValidated();// messageInfo校验结果

		AssignMessage assignmessage = assignMessageDAO
				.getAssignMessageById(assignMessageId);
		if (assignmessage != null) {
			if (isValidated) {// 格式校验
				long sendedStatus = messageInfo.getSendedStatus();
				if (sendedStatus == 2) {
					assignmessage.setStatus(sendedStatus);
					assignmessage.setMemo("发表成功");
				} else {
					assignmessage.setStatus(new Long(3));
					assignmessage.setMemo(messageInfo.getRemark());
				}
			} else {
				assignmessage.setStatus(new Long(3));
				assignmessage.setMemo(messageInfo.getValidateString());
			}

			assignmessage.setSendedtime(new Timestamp(System
					.currentTimeMillis()));

			update(assignmessage);
			saveAssignMessageLog(assignmessage, "1");
		}
	}

	/**
	 * 发帖，分发到各个论坛发帖接口
	 * 
	 * @param MessageInfo
	 *            messageInfo 待发帖子信息
	 * @return MessageInfo messageInfo
	 */
	public MessageInfo executeSendMessage(MessageInfo messageInfo)
			throws AppException {
		String loginSite = messageInfo.getLoginSite();
		String sendType = messageInfo.getSendType();

		MessageUtilBiz messageUtil = null;
		if ("www.javaeye.com".equals(loginSite)) {
			messageUtil = new JavaEyeMessageBizImp();// 论坛接口走通
		} else if ("www.gdin.net".equals(loginSite)) {
			messageUtil = new GdinNetMessageBizImp();
		} else if ("login.tianya.cn".equals(loginSite)) {
			messageUtil = new TianYaMessageBizImp();// ---
		} else if ("bbs.online.sh.cn".equals(loginSite)) {
			messageUtil = new SHOnlineMessageBizImp();// ---
		} else if ("member.zhuhai.gd.cn".equals(loginSite)) {
			messageUtil = new ZhuHaiShiChuangMessageBizImp();
			return null;
		} else if ("bbs.jnustu.net".equals(loginSite)) {
			messageUtil = new JiNanBBSMessageBizImp();// ---
		} else if ("tt.mop.com".equals(loginSite)) {
			messageUtil = new TTMopMessageBizImp();
		} else if ("www.xinhuanet.com".equals(loginSite)) {
			messageUtil = new XinHuaNetMessageBizImp();
		} else if ("bbs.163.com".equals(loginSite)) {
			messageUtil = new WangyiMessageBizImp();
		} else if ("bbs.jrj.com.cn".equals(loginSite)) {
			messageUtil = new JinRongjieMessageBizImp();
		} else if ("bbs.rayli.com.cn".equals(loginSite)) {
			messageUtil = new RayliMessageBizImp();// ---
		} else if ("bbs.sina.com.cn".equals(loginSite)) {
			messageUtil = new SinaMessageBizImp();
		} else if ("tieba.baidu.com".equals(loginSite)) {
			messageUtil = new BaiduMessageBizImp();
		} else if ("bbs.dospy.com".equals(loginSite)) {
			messageUtil = new DospyMessageBizImp();
		} else {
			return null;
		}

		if (MessageInfo.SENDTYPE_MAIN.equals(sendType)) {
			messageInfo = messageUtil.publishMessage(messageInfo);
		} else {
			messageInfo = messageUtil.replyMessage(messageInfo);
		}

		return messageInfo;
	}

	/**
	 * 根据发帖任务ID获得待发帖子信息
	 * 
	 * @param long
	 *            assignMessageId 发帖任务ID
	 * @return MessageInfo 待发帖子信息
	 */
	public MessageInfo getMessageInfoByAssignMessageId(long assignMessageId)
			throws AppException {
		AssignMessage assignMessage = (AssignMessage) getAssignMessageById(assignMessageId);

		MessageInfo messageInfo = getMessageInfoByAssignMessage(assignMessage);
		return messageInfo;
	}

	/**
	 * 检查待发帖子信息格式
	 * 
	 * @param MessageInfo
	 *            messageInfo
	 * @return MessageInfo
	 */
	public MessageInfo checkMessageInfo(MessageInfo messageInfo)
			throws AppException {
		// --------------
		messageInfo.setValidated(true);

		return messageInfo;
	}

	/**
	 * 将发帖任务转换为待发帖子信息
	 * 
	 * @param AssignMessage
	 *            assignMessage 发帖任务
	 * @return {@link MessageInfo} messageInfo 待发帖子信息
	 */
	public MessageInfo getMessageInfoByAssignMessage(AssignMessage assignMessage)
			throws AppException {
		MessageSection messageSection = assignMessage.getMessageSection();
		Message message = messageSection.getMessage();
		Section section = messageSection.getSection();
		Topic topic = messageSection.getTopic();

		Forum forum = section.getForum();
		Account account = assignMessage.getAccount();
		SysUser user = assignMessage.getSysUser();

		MessageInfo messageInfo = new MessageInfo();

		// assignment
		messageInfo.setAssignMessageId(assignMessage.getId());

		// loginInfo
		messageInfo.setLoginSite(forum.getLoginSite());
		messageInfo.setLoginPort(forum.getLoginPort().intValue());
		messageInfo.setLoginName(account.getLoginName());
		messageInfo.setLoginPassword(account.getLoginPassword());

		System.out.println("---3----login info------");
		System.out.println(messageInfo.getLoginName());
		System.out.println(messageInfo.getLoginPassword());

		// -----(以后要在Forum表添加字段)
		messageInfo.setCharset("UTF-8");

		// messageInfo
		messageInfo.setForum(forum);
		messageInfo.setSection(section);

		// ----
		messageInfo.setTitle(message.getTitle());
		messageInfo.setContent(message.getContent());
		messageInfo.setTopic(topic.getName());// ---------
		messageInfo.setRemark("");
		messageInfo.setSendedStatus(new Long(0));// 发帖状态：待发布
		messageInfo.setSendType(MessageInfo.SENDTYPE_MAIN);// 发帖类型：主帖(增s加数据库字段后读取)

		return messageInfo;
	}

	/**
	 * 将快速回帖任务转换为待发帖子信息
	 * 
	 * @param AssignMessage
	 *            assignMessage 快速回帖任务
	 * @return {@link MessageInfo} messageInfo 待发帖子信息
	 */
	public MessageInfo getMessageInfoByQuickReply(AssignMessageListForm amlf)
			throws AppException {

		MessageInfo messageInfo = new MessageInfo();

		// loginInfo
		messageInfo.setLoginSite(amlf.getLoginSite());
		messageInfo.setLoginPort(80);
		messageInfo.setLoginName(amlf.getLoginName());
		messageInfo.setLoginPassword(amlf.getLoginPassword());

		// -----(以后要在Forum表添加字段)
		messageInfo.setCharset("UTF-8");

		messageInfo.setNewTopicUrl(amlf.getMessageUrl());
		messageInfo.setContent(amlf.getMessageContent());
		messageInfo.setRemark("");
		messageInfo.setSendedStatus(new Long(0));// 发帖状态：待发布
		messageInfo.setSendType(MessageInfo.SENDTYPE_REPLY);// 发帖类型：主帖(增s加数据库字段后读取)

		return messageInfo;
	}

	/**
	 * 发帖任务统计
	 * 
	 * @param AssignMessageListForm
	 *            amlf 发帖任务列表
	 * @return List 发帖任务统计结果
	 */
	public List assignMessageCounter(AssignMessageListForm assignMessagelist)
			throws AppException {

		List assignMessageCounterList = getAssignMessages(assignMessagelist);

		return assignMessageCounterList;
	}

	/**
	 * 保存发帖任务操作日志
	 * 
	 * @AssignMessage assignMessage 发帖任务
	 * @String logType 日志类型 0：创建 1：发帖操作
	 */
	public long saveAssignMessageLog(AssignMessage assignMessage, String logType)
			throws AppException {
		AssignMessageLog log = new AssignMessageLog();

		Timestamp logTime = assignMessage.getSendedtime();

		if (logTime == null) {
			logTime = new Timestamp(System.currentTimeMillis());
		}

		log.setAssignMessage(assignMessage);
		log.setLogType(logType);
		log.setLogTime(logTime);
		log.setContent(assignMessage.getMemo());
		log.setStatus(assignMessage.getStatus());

		return assignMessageLogDAO.save(log);
	}

	public void deleteById(long id) throws AppException {
		assignMessageDAO.deleteById(id);
	}

	public AssignMessage getAssignMessageById(long id) throws AppException {
		return assignMessageDAO.getAssignMessageById(id);
	}

	public List getAssignMessageByAssignId(
			AssignMessageListForm assignMessagelist) throws AppException {
		return assignMessageDAO.getAssignMessageByAssignId(assignMessagelist);
	}

	public List list(AssignMessageListForm assignMessagelist)
			throws AppException {
		return assignMessageDAO.list(assignMessagelist);
	}

	public long merge(AssignMessage assignMessage) throws AppException {
		return assignMessageDAO.merge(assignMessage);
	}

	public long save(AssignMessage assignMessage) throws AppException {
		return assignMessageDAO.save(assignMessage);
	}

	public long saveAsAssignMessage(AssignMessage assignMessage)
			throws AppException {
		assignMessage = getTempAssignMessage(assignMessage);
		return save(assignMessage);
	}

	public long update(AssignMessage assignMessage) throws AppException {
		return assignMessageDAO.update(assignMessage);
	}

	public long updateAsAssignMessage(AssignMessage assignMessage)
			throws AppException {
		assignMessage = getTempAssignMessage(assignMessage);
		return update(assignMessage);
	}

	public HttpServletRequest putAttributeAsAssignMessage(
			HttpServletRequest request) throws AppException {
		request.setAttribute("assignmentlist", assignmentDAO.list());// 任务列表
		request.setAttribute("sysuserlist", userDAO.list());// 用户列表
		return request;
	}

	public HttpServletRequest putAttributeAsAssignMessageByStatus(
			HttpServletRequest request, long status) throws AppException {
		request.setAttribute("assignmentlist", assignmentDAO
				.listByStatus(status));// 任务列表
		request.setAttribute("sysuserlist", userDAO.listByStatus(status));// 用户列表
		return request;
	}

	/**
	 * 根据assignmentId、messageSectionId、userId重新组装AssignMessage
	 */
	public AssignMessage getTempAssignMessage(AssignMessage assignMessage)
			throws AppException {
		long assignmentId = assignMessage.getAssignmentId();
		long messageSectionId = assignMessage.getMessageSectionId();
		long accountId = assignMessage.getAccountId();
		long userId = assignMessage.getUserId();

		// System.out.println("===============getTempAssignMessage============");
		// System.out.println("-assignmentId:" + assignmentId);
		// System.out.println("-messageSectionId:" + messageSectionId);
		// System.out.println("-accountId:" + accountId);
		// System.out.println("-userId:" + userId);

		Assignment assignment = assignmentDAO.getAssignmentById(assignmentId);
		MessageSection messageSection = messageSectionDAO
				.getMessageSectionById(messageSectionId);
		Account account = accountDAO.getAccountById(accountId);
		SysUser user = userDAO.getUserById(userId);

		assignMessage.setAssignment(assignment);
		assignMessage.setMessageSection(messageSection);
		assignMessage.setAccount(account);
		assignMessage.setSysUser(user);
		assignMessage.setAssignMessageLogs(new HashSet<AssignMessageLog>());

		return assignMessage;
	}

	/**
	 * 
	 */
	public List<AssignMessage> getassignMessageByBatchRequest(
			HttpServletRequest request, AssignMessage assignMessage)
			throws AppException {
		String[] assignmentIds = request.getParameterValues("assignmentId");
		String[] messageSectionIds = request
				.getParameterValues("messageSectionId");
		String[] accountIds = request.getParameterValues("accountId");
		String[] userIds = request.getParameterValues("userId");

		int arrayLen = messageSectionIds.length;
		System.out.println("arrayLength:" + arrayLen);

		List<AssignMessage> amlist = new ArrayList<AssignMessage>();

		for (int i = 0; i < arrayLen; i++) {
			long assignmentId = Long.parseLong(assignmentIds[i].trim());
			long messageSectionId = Long.parseLong(messageSectionIds[i].trim());
			long accountId = Long.parseLong(accountIds[i].trim());
			long userId = Long.parseLong(userIds[i].trim());
			//
			// System.out.println(i + "-assignmentId:" + assignmentId);
			// System.out.println(i + "-messageSectionId:" + messageSectionId);
			// System.out.println(i + "-accountId:" + accountId);
			// System.out.println(i + "-userId:" + userId);

			AssignMessage tempAssignMessage = new AssignMessage();
			long status = assignMessage.getStatus();
			tempAssignMessage.setStatus(status);
			tempAssignMessage.setSendedtime(new Timestamp(System
					.currentTimeMillis()));
			tempAssignMessage.setMemo("创建发帖任务");

			tempAssignMessage.setAssignmentId(assignmentId);
			tempAssignMessage.setMessageSectionId(messageSectionId);
			tempAssignMessage.setAccountId(accountId);
			tempAssignMessage.setUserId(userId);

			tempAssignMessage
					.setAssignMessageLogs(new HashSet<AssignMessageLog>());

			amlist.add(tempAssignMessage);
		}

		return amlist;
	}

	public List getAssignMessages() throws AppException {
		return assignMessageDAO.list();
	}

	public List getAssignMessagesByStatus(long status) throws AppException {
		return assignMessageDAO.listByStatus(status);
	}

	public List getAssignMessages(AssignMessageListForm assignMessagelist)
			throws AppException {
		return assignMessageDAO.list(assignMessagelist);
	}

	public void hideById(long id) throws AppException {
		assignMessageDAO.hideById(id);
	}

	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void setAssignMessageDAO(AssignMessageDAO assignMessageDAO) {
		this.assignMessageDAO = assignMessageDAO;
	}

	public void setAssignMessageLogDAO(AssignMessageLogDAO assignMessageLogDAO) {
		this.assignMessageLogDAO = assignMessageLogDAO;
	}

	public void setMessageSectionDAO(MessageSectionDAO messageSectionDAO) {
		this.messageSectionDAO = messageSectionDAO;
	}

	public void setAssignmentDAO(AssignmentDAO assignmentDAO) {
		this.assignmentDAO = assignmentDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
