package com.fordays.masssending.forum;

import com.fordays.masssending.forum._entity._Section;

public class Section extends _Section {
	private static final long serialVersionUID = 1L;
	private long forumId = new Long(0);
	private String forumName = "";

	private String statusInfo = "";

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "启用";
		} else if (status == 2) {
			statusInfo = "停用";
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

	public String getForumName() {
		if ("".equals(forumName)) {
			forumName = getForum().getName();
		}
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public com.fordays.masssending.forum.Forum getForum() {
		if (this.forum == null) {
			return new Forum();
		}
		return this.forum;
	}

	public void setForum(com.fordays.masssending.forum.Forum forum) {
		this.forum = forum;
	}
}
