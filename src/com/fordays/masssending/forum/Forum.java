package com.fordays.masssending.forum;

import com.fordays.masssending.forum._entity._Forum;

public class Forum extends _Forum {
	private static final long serialVersionUID = 1L;

	private String statusInfo = "";

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "启用";
		} else if (status == 2) {
			statusInfo = "停用";
		} else if (status == 88) {
			statusInfo = "隐藏数据";
		} else {
			statusInfo = "异常状态";
		}
		return statusInfo;
	}
}
