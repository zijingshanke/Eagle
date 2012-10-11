package com.fordays.masssending.message;

import com.fordays.masssending.forum.Section;
import com.fordays.masssending.forum.Topic;
import com.fordays.masssending.message._entity._MessageSection;

public class MessageSection extends _MessageSection {
	private static final long serialVersionUID = 1L;

	private long forumId = new Long(0);
	private long sectionId = new Long(0);
	private long topicId = new Long(0);
	private long messageId = new Long(0);
	private String statusInfo = "";

	private String summary = "";

	public String getSummary() {
		summary = section.getForum().getName() + "--" + section.getName();

		if (topic == null) {
			summary += "--" + message.getTitle();
		} else {
			summary += "--" + topic.getName() + "--" + message.getTitle();
		}
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "有效";
		} else if (status == 2) {
			statusInfo = "无效";
		} else if (status == 88) {
			statusInfo = "隐藏数据";
		}else {
			statusInfo = "异常状态";
		}
		return statusInfo;
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

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public com.fordays.masssending.forum.Section getSection() {
		if (section == null) {
			return new Section();
		}
		return this.section;
	}

	public com.fordays.masssending.message.Message getMessage() {
		if (message == null) {
			return new Message();
		}
		return this.message;
	}

	public com.fordays.masssending.forum.Topic getTopic() {
		if (topic == null) {
			return new Topic();
		}
		return this.topic;
	}

	public void setSection(com.fordays.masssending.forum.Section section) {
		this.section = section;
	}

	public void setMessage(com.fordays.masssending.message.Message message) {
		this.message = message;
	}

	public void setTopic(com.fordays.masssending.forum.Topic topic) {
		this.topic = topic;
	}

}
