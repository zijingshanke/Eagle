package com.fordays.masssending.message;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.fordays.masssending.forum.Section;
import com.neza.base.ListActionForm;

/**
 * 发帖目标（帖子版块）列表
 */
public class MessageSectionListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private Long status;
	private Section section = new Section();
	private Message message = new Message();

	// ------
	private Long sectionId;
	private Long topicId;
	private Long messageId;

	// ---
	private String forumName;
	private String sectionName;
	private String messageTitle;

	private boolean descById = false;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public boolean isDescById() {
		return descById;
	}

	public void setDescById(boolean descById) {
		this.descById = descById;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}

}
