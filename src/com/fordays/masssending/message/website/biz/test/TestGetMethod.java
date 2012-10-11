package com.fordays.masssending.message.website.biz.test;

import org.apache.commons.httpclient.methods.GetMethod;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;

/**
 * 测试抓取页面资源
 */

public class TestGetMethod {

	public static void testGetHtmlContent() {
		String LOGIN_SITE = "www.gzuc.net";
		int LOGIN_PORT = 80;
		String CHARSET = "gbk";
		try {
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

			GetMethod redirect = new GetMethod("http://www.gzuc.net/login.php");

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
			System.out.println("statusCode:" + statusCode);

			String htmlcontent = HttpClientUtil.printResponseHtml(redirect,
					true, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
