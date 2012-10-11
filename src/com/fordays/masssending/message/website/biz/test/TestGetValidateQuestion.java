package com.fordays.masssending.message.website.biz.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;

/**
 * 测试获取页面的验证码（回答问题）
 */
public class TestGetValidateQuestion {
	public static void main(String[] args) {
		try {
			String LOGIN_SITE = "bbs.jnustu.net";
			int LOGIN_PORT = 80;
			String CHARSET = "utf-8";
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
			HttpClient client = clientInfo.getHttpclient();

			String location = "http://bbs.jnustu.net/joinfs0905.php?referer=http%3A//bbs.jnustu.net/index.php";
			GetMethod redirect = new GetMethod(location);
			redirect.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			int statusCode = client.executeMethod(redirect);

			System.out.println("自动跳转后,statusCode:" + statusCode);

			// http://www.gdccbbs.com
			GetMethod redirect2 = new GetMethod("/ajax.php?action=updatesecqaa");
			redirect2.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			int statusCode2 = client.executeMethod(redirect2);

			System.out.println("statusCode2:" + statusCode2);

			// System.out.println(redirect2.getResponseBodyAsString());
			String htmlcontent = HttpClientUtil.printResponseHtml(redirect2,
					true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
