package com.fordays.masssending.forum;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.neza.base.ListActionForm;

/**
 * 主题列表
 */
public class TopicListForm extends ListActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String value;
	private Long status;
	private Section section = new Section();
	private long sectionId;

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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Section getSection() {
		return section;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}
}
