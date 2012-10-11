package com.fordays.masssending.message;

import com.fordays.masssending.message._entity._Assignment;

public class Assignment extends _Assignment {
	private static final long serialVersionUID = 1L;

	private String statusInfo = "";

	private String shortContent = "";

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

	public String getShortContent() {
		if (content != null) {
			if (content.length() > 20) {
				shortContent = content.substring(0, 19) + "...";
			} else {
				shortContent = content;
			}
		}
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

}
