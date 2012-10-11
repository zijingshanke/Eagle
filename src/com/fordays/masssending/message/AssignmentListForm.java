package com.fordays.masssending.message;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.neza.base.ListActionForm;

/**
 * 任务列表
 */
public class AssignmentListForm extends ListActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String content;
	private String founder;
	private String createTime;
	private String beginTime;
	private String finishTime;
	private Long status;
	private Message message = new Message();

	private boolean isShowHidden = false;

	public String getCreateTime() {
		return createTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}
}
