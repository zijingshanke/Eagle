package com.fordays.masssending.message.website.biz.test;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;

public class TestXieCheng {
	public static void main(String[] args) {
		try {
			testXieCheng();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testXieCheng() throws Exception {
		String LOGIN_SITE = "www.ctrip.com";
		int LOGIN_PORT = 80;
		String CHARSET = "gbk";

		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

		clientInfo = testLoginXieCheng(clientInfo);// 登录
	}

	public static HttpClientInfo testLoginXieCheng(HttpClientInfo clientInfo)
			throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		// 模拟登录
		PostMethod postMethod = new PostMethod("/member/login.asp");

		NameValuePair signin_logintype = new NameValuePair("signin_logintype",
				"");
		NameValuePair done = new NameValuePair("done", "/member/login.asp");
		NameValuePair signin_uid = new NameValuePair("signin_uid",
				"zijingshanke@yahoo.com.cn");
		NameValuePair signin_pwd = new NameValuePair("signin_pwd", "598719");

		postMethod.setRequestBody(new NameValuePair[] { signin_logintype, done,
				signin_uid, signin_pwd });

		clientInfo.setPostMethod(postMethod);// 保存PostMethod

		int statusCode = client.executeMethod(postMethod);
		System.out.println("login status:" + statusCode);
		System.out.println("======login success====" + "\n");
		System.out.println(postMethod.getResponseBodyAsString());
		System.out.println("======end login success===" + "\n");

		// 获取并保存登录Cookie
		Cookie[] cookies = HttpClientUtil.getCurrentCookie(clientInfo
				.getLOGIN_SITE(), clientInfo.getLOGIN_PORT(), client);
		clientInfo.setCookies(cookies);

		postMethod.releaseConnection();
		return clientInfo;
	}
}
