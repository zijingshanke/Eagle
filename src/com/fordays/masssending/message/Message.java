package com.fordays.masssending.message;

import com.fordays.masssending.message._entity._Message;

public class Message extends _Message {
	private static final long serialVersionUID = 1L;

	private String shortTitle = "";

	private String statusInfo = "";

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

	public String getShortTitle() {
		if (title.length() > 10) {
			shortTitle = title.substring(0, 9) + "...";
		} else {
			shortTitle = title;
		}
		return shortTitle;
	}
}
