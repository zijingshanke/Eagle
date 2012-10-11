package com.fordays.masssending.message.biz;

import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.message.MessageInfo;
import com.neza.exception.AppException;

/**
 * 发帖工具接口
 */
public interface MessageUtilBiz {
	/**
	 * 抓取登陆页信息
	 * 
	 */
	public HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException;

	/**
	 * 登录
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException;

	/**
	 * 跳转到发帖页面
	 */
	public HttpClientInfo redirectPostHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException;

	/**
	 * 发帖
	 */
	public MessageInfo publishMessage(MessageInfo messageInfo)
			throws AppException;

	/**
	 * 发帖
	 */
	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException;

}
