package com.fordays.masssending.message;

import com.fordays.masssending.forum.Forum;
import com.fordays.masssending.forum.Section;

/**
 * 待发帖子信息
 */
public class MessageInfo {
	private String sendType = "";
	private String sendTypeMemo = "";

	// assignment
	private long assignMessageId = new Long(0);
	// web site info
	private long forumId = new Long(0);
	private Forum forum;

	private long sectionId = new Long(0);
	private Section section;
	private String newTopicUrl = "";

	public String getNewTopicUrl() {
		if (section != null) {
			newTopicUrl = section.getSectionUrl();
		}
		return newTopicUrl;
	}

	// login info
	private String loginSite = "";
	private int loginPort = 0;
	private String charset = "utf-8";
	private String loginName = "";
	private String loginPassword = "";
	// message info
	private String title = "";
	private String topic = "";
	private String content = "";
	private String remark = "";

	// check messageInfo
	private boolean isValidated = false;
	private String validateString = "";

	// send info
	private long sendedStatus = new Long(0);

	private static long SENDED_WAIT = new Long(0);
	private static long SENDED_SEND = new Long(1);
	private static long SENDED_SUCCESS = new Long(2);
	private static long SENDED_FAILED = new Long(3);

	public static String SENDTYPE_MAIN = "1";
	public static String SENDTYPE_REPLY = "2";

	private String sendInfo = "";

	// --------------------------------------------------
	public String getSendTypeMemo() {
		if (SENDTYPE_MAIN.equals(sendType)) {
			sendTypeMemo = "主帖";
		} else if (SENDTYPE_REPLY.equals(sendType)) {
			sendTypeMemo = "回帖";
		} else {
			sendTypeMemo = "未定义的发帖类型";
		}
		return sendTypeMemo;
	}

	public String getSendInfo() {
		if (SENDED_WAIT == sendedStatus) {
			sendInfo = "待发布";
		} else if (SENDED_SEND == sendedStatus) {
			sendInfo = "已发出";
		} else if (SENDED_SUCCESS == sendedStatus) {
			sendInfo = "发布成功";
		} else if (SENDED_FAILED == sendedStatus) {
			sendInfo = "发布失败";
		} else {
			sendInfo = "没有设置sendStatus";
		}
		return sendInfo;
	}

	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public String getLoginSite() {
		return loginSite;
	}

	public void setLoginSite(String loginSite) {
		this.loginSite = loginSite;
	}

	public int getLoginPort() {
		return loginPort;
	}

	public void setLoginPort(int loginPort) {
		this.loginPort = loginPort;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getSendedStatus() {
		return sendedStatus;
	}

	public void setSendedStatus(long sendedStatus) {
		this.sendedStatus = sendedStatus;
	}

	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public long getAssignMessageId() {
		return assignMessageId;
	}

	public void setAssignMessageId(long assignMessageId) {
		this.assignMessageId = assignMessageId;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public String getValidateString() {
		return validateString;
	}

	public void setValidateString(String validateString) {
		this.validateString = validateString;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setNewTopicUrl(String newTopicUrl) {
		this.newTopicUrl = newTopicUrl;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public void setSendTypeMemo(String sendTypeMemo) {
		this.sendTypeMemo = sendTypeMemo;
	}

}
