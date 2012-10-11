package com.fordays.masssending.message;

import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Parser;

import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.neza.exception.AppException;

/**
 * 发帖业务工具类
 */
public class MessageUtil extends HttpClientUtil {

	public static MessageInfo setErrorInfo(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		messageInfo.setValidated(false);
		messageInfo.setRemark(clientInfo.getClientRemark());
		return messageInfo;
	}

	/**
	 * 业务方法：根据TopicValueMethod定向到发帖页面
	 * 
	 */
	public static HttpClientInfo redirectAndSaveInfo(HttpClientInfo clientInfo)
			throws Exception {

		clientInfo = getMethodRedirect(clientInfo);

		// System.out.println("------页面内容-----" + "\n"
		// + clientInfo.getResponseBodyAsString());

		String method = clientInfo.getTopicValueMethod();

		if ("0".equals(method)) {
			return clientInfo;
		} else if ("1".equals(method)) {
			clientInfo.setTopicTypeValue(clientInfo.getTopicType());
		} else if ("2".equals(method)) {
			clientInfo = setHtmlValue(clientInfo);
		} else {
			System.out.println("redirectAndSaveInfo(),topicValueMethod错误,"
					+ method);
		}
		return clientInfo;
	}

	/**
	 * 业务方法（Type 2） 获取selects option value and form action value
	 */
	public static HttpClientInfo setHtmlValue(HttpClientInfo clientInfo)
			throws Exception {
		String htmlcontent = clientInfo.getResponseBodyAsString();
		String charset = clientInfo.getCharset();
		String optionText = clientInfo.getTopicType();

		Parser parser = Parser.createParser(htmlcontent, charset);

		String optionValue = getOptionValueByText(parser, htmlcontent, charset,
				optionText);

		clientInfo.setTopicTypeValue(optionValue);

		return clientInfo;
	}

	/**
	 * 业务方法：初始化HttpClientInfo
	 * 
	 * @param MessageInfo
	 *            待发帖子信息
	 * @return HttpClientInfo
	 */
	public static HttpClientInfo initHttpClientInfo(MessageInfo messageInfo)
			throws Exception {
		HttpClientInfo clientInfo = null;
		try {
			String LOGIN_SITE = messageInfo.getLoginSite();
			int LOGIN_PORT = messageInfo.getLoginPort();
			String CHARSET = messageInfo.getCharset();

			clientInfo = initHttpClientInfo(LOGIN_SITE, LOGIN_PORT, CHARSET);

			clientInfo = setLoginUser(clientInfo, messageInfo);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("初始化HttpClientInfo异常");
		}
		return clientInfo;
	}

	/**
	 * 业务方法：定向到指定页面
	 */
	public static HttpClientInfo getMethodRedirect(HttpClientInfo clientInfo)
			throws Exception {
		String redirectUrl = clientInfo.getRedirectNewUrl();

		System.out.println("redirectUrl:" + "\n" + redirectUrl);

		GetMethod redirect = new GetMethod(redirectUrl);
		redirect.setRequestHeader("Cookie", clientInfo.getCookies().toString());
		clientInfo.getHttpclient().executeMethod(redirect);

		int statusCode = redirect.getStatusCode();

		System.out.println("getMethodRedirect statusCode:" + statusCode);
		clientInfo.setStatusCode(statusCode);

		clientInfo.setResponseBodyAsString(redirect.getResponseBodyAsString());
		redirect.releaseConnection();
		return clientInfo;
	}

	/**
	 * 业务方法：set login user info from messageInfo to clientInfo
	 * 
	 * @param MessageInfo
	 *            待发帖子信息
	 * @return HttpClientInfo
	 */
	public static HttpClientInfo setLoginUser(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		try {
			clientInfo.setLOGIN_NAME(messageInfo.getLoginName());
			clientInfo.setLOGIN_PASSWORD(messageInfo.getLoginPassword());
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("设置登录帐号用户名、密码异常");
		}
		return clientInfo;
	}
}
