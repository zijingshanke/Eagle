package com.fordays.masssending.forum;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.message.Message;
import com.neza.base.ListActionForm;

/**
 * 版块列表
 */
public class SectionListForm extends ListActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String sectionUrl;
	private Long status;
	private com.fordays.masssending.forum.Forum forum = new Forum();
	private long forumId;
	private String forumName = "";

	private Topic topic = new Topic();
	private com.fordays.masssending.message.Message message = new Message();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSectionUrl() {
		return sectionUrl;
	}

	public void setSectionUrl(String sectionUrl) {
		this.sectionUrl = sectionUrl;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public com.fordays.masssending.forum.Forum getForum() {
		return forum;
	}

	public void setForum(com.fordays.masssending.forum.Forum forum) {
		this.forum = forum;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public com.fordays.masssending.message.Message getMessage() {
		return message;
	}

	public void setMessage(com.fordays.masssending.message.Message message) {
		this.message = message;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}
}
