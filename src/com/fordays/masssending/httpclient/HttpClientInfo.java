package com.fordays.masssending.httpclient;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 客户端信息
 */

public class HttpClientInfo {
	private String LOGIN_SITE = "";
	private int LOGIN_PORT = 0;
	private String LOGIN_NAME = "";
	private String LOGIN_PASSWORD = "";
	private int statusCode;

	private HttpClient httpclient = null;
	private String validateCode = "";

	private String topicType = "";
	private String topicTypeValue = "";

	private String topicValueMethod = "";
	// 0:直接填写中文
	// 1:填写中文，到页面的options中提取value

	private PostMethod postMethod = null;
	private Cookie[] cookies = null;
	private String redirectNewUrl = "";
	private String charset = "UTF-8";
	private String responseBodyAsString = "";
	private InputStream responseBodyAsStream = null;

	private Map<String, String> hiddenHtmlValue = new HashMap<String, String>();// 抓取页面的信息
	
	private File imgFile;

	private boolean success = false;
	private String clientRemark = "";

	public String getLOGIN_NAME() {
		return LOGIN_NAME;
	}

	public void setLOGIN_NAME(String login_name) {
		LOGIN_NAME = login_name;
	}

	public String getLOGIN_PASSWORD() {
		return LOGIN_PASSWORD;
	}

	public void setLOGIN_PASSWORD(String login_password) {
		LOGIN_PASSWORD = login_password;
	}

	public HttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public String getTopicTypeValue() {
		return topicTypeValue;
	}

	public void setTopicTypeValue(String topicTypeValue) {
		this.topicTypeValue = topicTypeValue;
	}

	public PostMethod getPostMethod() {
		return postMethod;
	}

	public void setPostMethod(PostMethod postMethod) {
		this.postMethod = postMethod;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}

	public String getRedirectNewUrl() {
		return redirectNewUrl;
	}

	public void setRedirectNewUrl(String redirectNewUrl) {
		this.redirectNewUrl = redirectNewUrl;
	}

	public String getLOGIN_SITE() {
		return LOGIN_SITE;
	}

	public void setLOGIN_SITE(String login_site) {
		LOGIN_SITE = login_site;
	}

	public int getLOGIN_PORT() {
		return LOGIN_PORT;
	}

	public void setLOGIN_PORT(int login_port) {
		LOGIN_PORT = login_port;
	}

	public String getResponseBodyAsString() {
		return responseBodyAsString;
	}

	public void setResponseBodyAsString(String responseBodyAsString) {
		this.responseBodyAsString = responseBodyAsString;
	}

	public InputStream getResponseBodyAsStream() {
		return responseBodyAsStream;
	}

	public void setResponseBodyAsStream(InputStream responseBodyAsStream) {
		this.responseBodyAsStream = responseBodyAsStream;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getTopicValueMethod() {
		return topicValueMethod;
	}

	public void setTopicValueMethod(String topicValueMethod) {
		this.topicValueMethod = topicValueMethod;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getHiddenHtmlValue() {
		return hiddenHtmlValue;
	}

	public void setHiddenHtmlValue(Map<String, String> hiddenHtmlValue) {
		this.hiddenHtmlValue = hiddenHtmlValue;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getClientRemark() {
		return clientRemark;
	}

	public void setClientRemark(String clientRemark) {
		this.clientRemark = clientRemark;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
}
