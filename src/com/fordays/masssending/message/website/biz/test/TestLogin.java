package com.fordays.masssending.message.website.biz.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;

public class TestLogin {
	public static void main(String[] args) {
		testLoginBaidu();
	}

	public static void testLoginTianYa() {
		try {
			String LOGIN_SITE = "passport.baidu.com";
			int LOGIN_PORT = 80;
			String CHARSET = "gbk";
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
			HttpClient client = clientInfo.getHttpclient();
			clientInfo.setLOGIN_NAME("zijingshanke");
			clientInfo.setLOGIN_PASSWORD("598719");
			// 模拟登录
			PostMethod post = new PostMethod("http://passport.baidu.com/?login");
			post.setRequestHeader("Content-Type", "charset=gbk");
			NameValuePair tpl_ok = new NameValuePair("tpl_ok", "");
			NameValuePair next_target = new NameValuePair("next_target", "");
			NameValuePair tpl = new NameValuePair("tpl", "");
			NameValuePair skip_ok = new NameValuePair("skip_ok", "");
			NameValuePair aid = new NameValuePair("aid", "");
			NameValuePair need_pay = new NameValuePair("need_pay", "");
			NameValuePair need_coin = new NameValuePair("need_coin", "");
			NameValuePair pay_method = new NameValuePair("pay_method", "");
			NameValuePair u = new NameValuePair("u", "./");
			NameValuePair return_method = new NameValuePair("return_method",
					"get");
			NameValuePair more_param = new NameValuePair("more_param", "");
			NameValuePair return_type = new NameValuePair("return_type", "");
			NameValuePair psp_tt = new NameValuePair("psp_tt", "0");
			NameValuePair password = new NameValuePair("password", clientInfo
					.getLOGIN_PASSWORD());
			NameValuePair safeflg = new NameValuePair("safeflg", "");
			NameValuePair username = new NameValuePair("username", clientInfo
					.getLOGIN_NAME());
			post.setRequestBody(new NameValuePair[] { tpl_ok, next_target, tpl,
					skip_ok, aid, need_pay, need_coin, pay_method, u,
					return_method, more_param, return_type, psp_tt, password,
					safeflg, username });
			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;
			// post.setDoAuthentication(true);
			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);
			HttpClientUtil.printResponseHtml(post, true, false);

			// GetMethod redirect = new GetMethod("http://passport.baidu.com/");
			// statusCode = client.executeMethod(redirect);
			// System.out.println("after execute:" + statusCode);
			// System.out.println(redirect.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testLoginBaidu() {
		try {
			String LOGIN_SITE = "passport.baidu.com";
			int LOGIN_PORT = 80;
			String CHARSET = "gbk";
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
			HttpClient client = clientInfo.getHttpclient();
			clientInfo.setLOGIN_NAME("zijingshanke");
			clientInfo.setLOGIN_PASSWORD("598719");
			// 模拟登录
			PostMethod post = new PostMethod("http://passport.baidu.com/?login");
			post.setRequestHeader("Content-Type", "charset=gbk");
			NameValuePair tpl_ok = new NameValuePair("tpl_ok", "");
			NameValuePair next_target = new NameValuePair("next_target", "");
			NameValuePair tpl = new NameValuePair("tpl", "");
			NameValuePair skip_ok = new NameValuePair("skip_ok", "");
			NameValuePair aid = new NameValuePair("aid", "");
			NameValuePair need_pay = new NameValuePair("need_pay", "");
			NameValuePair need_coin = new NameValuePair("need_coin", "");
			NameValuePair pay_method = new NameValuePair("pay_method", "");
			NameValuePair u = new NameValuePair("u", "./");
			NameValuePair return_method = new NameValuePair("return_method",
					"get");
			NameValuePair more_param = new NameValuePair("more_param", "");
			NameValuePair return_type = new NameValuePair("return_type", "");
			NameValuePair psp_tt = new NameValuePair("psp_tt", "0");
			NameValuePair password = new NameValuePair("password", clientInfo
					.getLOGIN_PASSWORD());
			NameValuePair safeflg = new NameValuePair("safeflg", "");
			NameValuePair username = new NameValuePair("username", clientInfo
					.getLOGIN_NAME());
			post.setRequestBody(new NameValuePair[] { tpl_ok, next_target, tpl,
					skip_ok, aid, need_pay, need_coin, pay_method, u,
					return_method, more_param, return_type, psp_tt, password,
					safeflg, username });
			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;
			// post.setDoAuthentication(true);
			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);
			HttpClientUtil.printResponseHtml(post, true, false);

			GetMethod redirect = new GetMethod("http://passport.baidu.com/");
			statusCode = client.executeMethod(redirect);
			System.out.println("after execute:" + statusCode);
			System.out.println(redirect.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
