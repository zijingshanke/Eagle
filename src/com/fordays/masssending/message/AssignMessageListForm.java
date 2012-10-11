package com.fordays.masssending.message;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.forum.Account;
import com.fordays.masssending.user.SysUser;
import com.neza.base.ListActionForm;

/**
 * 发帖任务列表
 */
public class AssignMessageListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String sendedtime;
	private Long status;
	private Assignment assignment = new Assignment();
	private MessageSection messageSection = new MessageSection();
	private SysUser sysUser = new SysUser();
	private Account account = new Account();

	// ------
	private Long assignmentId;
	private String assignmentName;
	private Long messageId;
	private String messageTitle;
	private String userName;
	private String forumName;
	private String sectionName;

	// 快速发帖，临时设置
	private String messageUrl;
	private String messageContent;
	private String loginName;
	private String loginPassword;
	private String loginSite;

	// --
	private String beginDate = "";
	private String endDate = "";
	// --
	private int spaceTime;// 执行发帖任务间隔时间，单位(min)
	private int totalTime;

	private boolean isShowHidden = false;

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {

		thisAction = "";
		this.setIntPage(1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSendedtime() {
		return sendedtime;
	}

	public void setSendedtime(String sendedtime) {
		this.sendedtime = sendedtime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public MessageSection getMessageSection() {
		return messageSection;
	}

	public void setMessageSection(MessageSection messageSection) {
		this.messageSection = messageSection;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginSite() {
		return loginSite;
	}

	public void setLoginSite(String loginSite) {
		this.loginSite = loginSite;
	}

	public int getSpaceTime() {
		return spaceTime;
	}

	public void setSpaceTime(int spaceTime) {
		this.spaceTime = spaceTime;
	}

	public int getTotalTime() {

		totalTime = spaceTime * (getSelectedItems().length);

		System.out.println("totalTime:" + totalTime);
		
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}

}
