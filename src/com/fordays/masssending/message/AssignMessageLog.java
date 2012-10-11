package com.fordays.masssending.message;

import com.fordays.masssending.message._entity._AssignMessageLog;

public class AssignMessageLog extends _AssignMessageLog {
	private static final long serialVersionUID = 1L;
	private String statusInfo = "";

	public String getStatusInfo() {
		if (status == 0) {
			statusInfo = "";
		} else if (status == 1) {
			statusInfo = "等待发送";
		} else if (status == 2) {
			statusInfo = "发表成功";
		} else if (status == 3) {
			statusInfo = "发表失败";
		} else if (status == 88) {
			statusInfo = "隐藏数据";
		}else if (status == 4) {
			statusInfo = "已取消";
		} else {
			statusInfo = "异常状态";
		}
		return statusInfo;
	}

	private String logTypeMemo = "";

	public String getLogTypeMemo() {
		if (logType != "") {
			if ("0".equals(logType)) {
				logTypeMemo = "创建";
			} else if ("1".equals(logType)) {
				logTypeMemo = "操作";
			} else {
				logTypeMemo = "未定义";
			}
		} else {
			logTypeMemo = "异常类型,请联系管理员";
		}
		return logTypeMemo;
	}

	public String getContent() {
		if (content != null) {
			if (content.lastIndexOf("alert") > 0) {
				content = content.replaceAll(
						"<script language=\"Javascript\">", "");
				content = content.replaceAll("</script>", "");
				content = content.replaceAll("alert", "");
				// System.out.println(content);
				content = content.replaceAll(
						"window.location=\'javascript:history.go(-1)\'", "");
			}
		}
		return content;
	}

	public com.fordays.masssending.message.AssignMessage getAssignMessage() {
		if (assignMessage == null) {
			assignMessage = new AssignMessage();
		}
		return this.assignMessage;
	}

	public void setAssignMessage(
			com.fordays.masssending.message.AssignMessage assignMessage) {
		this.assignMessage = assignMessage;
	}
}
