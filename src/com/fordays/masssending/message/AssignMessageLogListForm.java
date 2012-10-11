package com.fordays.masssending.message;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.neza.base.ListActionForm;

/**
 * 发帖任务日志列表
 */
public class AssignMessageLogListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private java.sql.Timestamp logTime;
	private String logType;
	private String content;
	private Long status;
	private AssignMessage assignMessage = new AssignMessage();

	private long assignMessageId;

	private String beginDate = "";
	private String endDate = "";

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

	public java.sql.Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(java.sql.Timestamp logTime) {
		this.logTime = logTime;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public AssignMessage getAssignMessage() {
		return assignMessage;
	}

	public void setAssignMessage(AssignMessage assignMessage) {
		this.assignMessage = assignMessage;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getAssignMessageId() {
		return assignMessageId;
	}

	public void setAssignMessageId(long assignMessageId) {
		this.assignMessageId = assignMessageId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isShowHidden() {
		return isShowHidden;
	}

	public void setShowHidden(boolean isShowHidden) {
		this.isShowHidden = isShowHidden;
	}
}
