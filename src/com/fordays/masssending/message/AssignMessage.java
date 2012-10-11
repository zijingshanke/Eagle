package com.fordays.masssending.message;

import com.fordays.masssending.forum.Account;
import com.fordays.masssending.message._entity._AssignMessage;
import com.fordays.masssending.user.SysUser;

public class AssignMessage extends _AssignMessage {

	private static final long serialVersionUID = 1L;

	private long assignmentId = new Long(0);
	private long accountId = new Long(0);
	private long messageSectionId = new Long(0);
	private long userId = new Long(0);

	private String statusInfo = "";

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "等待发送";
		} else if (status == 2) {
			statusInfo = "发表成功";
		} else if (status == 3) {
			statusInfo = "发表失败";
		}else if (status == 88) {
			statusInfo = "隐藏数据";
		} else if (status == 4) {
			statusInfo = "已取消";
		} else {
			statusInfo = "异常状态";
		}
		return statusInfo;
	}

	private String resume = "";

	public String getResume() {
		if (assignment != null) {
			resume = assignment.getName();
		} else {
			resume = "任务为空";
		}

		if (messageSection != null) {
			resume += messageSection.getSummary();
		} else {
			resume += "发帖目标为空";
		}

		return resume;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public long getMessageSectionId() {
		return messageSectionId;
	}

	public void setMessageSectionId(long messageSectionId) {
		this.messageSectionId = messageSectionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public com.fordays.masssending.message.Assignment getAssignment() {
		if (assignment == null) {
			return new Assignment();
		}
		return this.assignment;
	}

	public com.fordays.masssending.message.MessageSection getMessageSection() {
		if (messageSection == null) {
			return new MessageSection();
		}
		return this.messageSection;
	}

	public com.fordays.masssending.user.SysUser getSysUser() {
		if (sysUser == null) {
			return new SysUser();
		}
		return this.sysUser;
	}

	public com.fordays.masssending.forum.Account getAccount() {
		if (account == null) {
			return new Account();
		}
		return this.account;
	}

	public void setAssignment(
			com.fordays.masssending.message.Assignment assignment) {
		this.assignment = assignment;
	}

	public void setMessageSection(
			com.fordays.masssending.message.MessageSection messageSection) {
		this.messageSection = messageSection;
	}

	public void setSysUser(com.fordays.masssending.user.SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public void setAccount(com.fordays.masssending.forum.Account account) {
		this.account = account;
	}
}
