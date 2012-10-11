package com.fordays.masssending.forum;

import com.fordays.masssending.forum._entity._Account;

public class Account extends _Account {
	private static final long serialVersionUID = 1L;

	private long forumId = new Long(0);

	private String statusInfo = "";

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "有效";
		} else if (status == 2) {
			statusInfo = "无效";
		} else if (status == 88) {
			statusInfo = "隐藏数据";
		} else {
			statusInfo = "异常状态";
		}
		return statusInfo;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public long getForumId() {
		return forumId;
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
