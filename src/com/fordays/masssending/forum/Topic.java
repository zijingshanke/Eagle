package com.fordays.masssending.forum;

import com.fordays.masssending.forum._entity._Topic;

public class Topic extends _Topic {
	private static final long serialVersionUID = 1L;

	private long forumId = new Long(0);
	private long sectionId = new Long(0);
	private String statusInfo = "";

	// 部分论坛可以没有主题
	public String getName() {
		if (name == null || "".equals(name.trim())) {
			return "-无-";
		}
		return this.name;
	}

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "有效";
		} else if (status == 2) {
			statusInfo = "无效";
		}else if (status == 88) {
			statusInfo = "隐藏数据";
		} else {
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

	public com.fordays.masssending.forum.Section getSection() {
		if (section == null) {
			return new Section();
		}
		return this.section;
	}

	public void setSection(com.fordays.masssending.forum.Section section) {
		this.section = section;
	}

}
