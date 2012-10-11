package com.fordays.masssending.message.website.biz.test;

import java.io.FileReader;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.fordays.masssending.base.util.StringUtil;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageUtil;

public class Test {
	public static void main(String[] args) {
		try {
			testJavaeye();
			// testZhuhai();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testZhuhai() throws Exception {
		String LOGIN_SITE = "member.zhuhai.gd.cn";
		int LOGIN_PORT = 80;
		String CHARSET = "UTF-8";

		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

		clientInfo = testLoginZhuhai(clientInfo);// 登录

		// String redirectNewUrl = "/forums/39/topics/new";
		// String topicType = "Spring";
		// clientInfo.setRedirectNewUrl(redirectNewUrl);
		// clientInfo.setTopicType(topicType);
		//
		// // 定向到发帖页面，获取相关信息
		// clientInfo = HttpClientUtil.redirectAndSaveInfo(clientInfo);
	}

	public static HttpClientInfo testLoginZhuhai(HttpClientInfo clientInfo)
			throws Exception {
		HttpClient client = clientInfo.getHttpclient();

		HttpClientUtil.getValidateCode(client,
				"http://member.zhuhai.gd.cn/ValidateImage.aspx");

		// 模拟登录
		// PostMethod post = new PostMethod("/login.aspx");
		// NameValuePair VIEWSTATE = new NameValuePair(
		// "_VIEWSTATE",
		// "dDwxNDEwNDg2NTcwO3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8Mj47aTwxMj47aTwxMz47aTwxNj47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47PjtsPHQ8QDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLztodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vOz47Oz47dDw7bDxpPDE+Oz47bDx0PHA8bDxfIUl0ZW1Db3VudDs+O2w8aTwyPjs+PjtsPGk8MD47aTwxPjtpPDI+O2k8Mz47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAPFw8U0NSSVBUIExBTkdVQUdFPSdKYXZhU2NyaXB0J1w+Oz47Oz47Pj47dDw7bDxpPDA+Oz47bDx0PEA8MDtodHRwOi8vbWouemh1aGFpLmdkLmNuLzvnj6Dmtbfop4bnqpflrrblsYXpopHpgZPlhajmlrDmlLnniYjkuIrnur/vvIE7Pjs7Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8QDwxO2h0dHA6Ly93d3cuemh1aGFpLmdkLmNuL2hvbWUvYWEvd2xhbi8754+g5rW35peg57q/5a695bimV2lGaeeDreeCuea4heWNlTs+Ozs+Oz4+O3Q8O2w8aTwwPjs+O2w8dDxAPFw8L1NDUklQVFw+Oz47Oz47Pj47Pj47Pj47dDxAPGh0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLzs+Ozs+Oz4+O3Q8QDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87Pjs7Pjt0PHA8cDxsPEltYWdlVXJsOz47bDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi9pbWFnZXMvbG9naW5fNi5naWY7Pj47Pjs7Pjt0PEA8aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vOz47Oz47dDw7bDxpPDA+Oz47bDx0PEA8aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLztodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLzs+Ozs+Oz4+Oz4+Oz4+O2w8YnRuX0xvZ2luOz4+");
		// NameValuePair txt_Account = new NameValuePair("txt_Account",
		// "598719");
		// NameValuePair txt_Password = new NameValuePair("txt_Password", "1");
		// NameValuePair txt_ValidateCode = new
		// NameValuePair("txt_ValidateCode",
		// "1");
		//
		// post.setRequestBody(new NameValuePair[] { VIEWSTATE, txt_Account,
		// txt_Password, txt_ValidateCode });
		//
		// clientInfo.setPostMethod(post);// 保存PostMethod
		//
		// int status = client.executeMethod(post);
		// System.out.println("login status:" + status);
		// System.out.println("======login success====" + "\n");
		// System.out.println(post.getResponseBodyAsString());
		// System.out.println("======end login success===" + "\n");
		//
		// // 获取并保存登录Cookie
		// Cookie[] cookies = HttpClientUtil.getCurrentCookie(clientInfo
		// .getLOGIN_SITE(), clientInfo.getLOGIN_PORT(), client);
		// clientInfo.setCookies(cookies);
		//
		// post.releaseConnection();
		return clientInfo;
	}

	// ------------------------------------------------------------------------
	public static void testJavaeye() throws Exception {
		String LOGIN_SITE = "www.javaeye.com";
		int LOGIN_PORT = 80;
		String CHARSET = "UTF-8";
		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

		clientInfo = testLoginJavaEye(clientInfo);// 登录

		// 论坛
		// String redirectNewUrl = "/forums/39/topics/new";
		// String topicType = "Spring";
		// clientInfo.setRedirectNewUrl(redirectNewUrl);
		// clientInfo.setTopicType(topicType);
		// clientInfo.setTopicValueMethod("2");

		// 问答频道
		String redirectNewUrl = "/problems/new";
		clientInfo.setRedirectNewUrl(redirectNewUrl);
		clientInfo.setTopicValueMethod("0");

		// 新手讨论
		redirectNewUrl = "/forums/46/topics/new";
		String topicType = "入门技术";
		clientInfo.setRedirectNewUrl(redirectNewUrl);
		clientInfo.setTopicType(topicType);
		clientInfo.setRedirectNewUrl(redirectNewUrl);
		clientInfo.setTopicValueMethod("0");

		// 定向到发帖页面，获取相关信息
		clientInfo = MessageUtil.redirectAndSaveInfo(clientInfo);

		// publishQuestion(clientInfo);

		publishMessage2(clientInfo);
	}

	public static HttpClientInfo testLoginJavaEye(HttpClientInfo clientInfo)
			throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		// 模拟登录
		PostMethod post = new PostMethod("/login");
		NameValuePair name = new NameValuePair("name", "qmpaytest");
		NameValuePair pass = new NameValuePair("password", "123abc");
		NameValuePair remember_me = new NameValuePair("remember_me", "1");
		post.setRequestBody(new NameValuePair[] { name, pass, remember_me });

		clientInfo.setPostMethod(post);// 保存PostMethod

		int status = client.executeMethod(post);
		System.out.println("login status:" + status);
		System.out.println("======login success====" + "\n");
		System.out.println(post.getResponseBodyAsString());
		System.out.println("======end login success===" + "\n");

		// 获取并保存登录Cookie
		Cookie[] cookies = HttpClientUtil.getCurrentCookie(clientInfo
				.getLOGIN_SITE(), clientInfo.getLOGIN_PORT(), client);
		clientInfo.setCookies(cookies);

		post.releaseConnection();
		return clientInfo;
	}

	// javaeye问答频道发帖
	public static void publishQuestion(HttpClientInfo clientInfo)
			throws Exception {

		PostMethod postMethod = new PostMethod("/problems/auto_check");

		NameValuePair title = new NameValuePair("problem_title",
				"�tititit提个简单的问题");
		NameValuePair body = new NameValuePair("problem[body]",
				"hiberate中多多关系生成的第三张表里�怎样加一个字�?");
		NameValuePair auto_save_id = new NameValuePair("auto_save_id", "");// hidden
		NameValuePair bbcode = new NameValuePair("problem[bbcode]", "");// hidden
		NameValuePair attachments_counter = new NameValuePair(
				"attachments_counter", "");// hidden

		postMethod.setRequestBody(new NameValuePair[] { title, body,
				auto_save_id, bbcode, attachments_counter });

		int statusCode = clientInfo.getHttpclient().executeMethod(postMethod);

		clientInfo.setPostMethod(postMethod);
		HttpClientUtil.autoRedirect(statusCode, clientInfo);

		System.out.println("======publish Message====");
		System.out.println(postMethod.getResponseBodyAsString());
		System.out.println("======end publish Message===" + "\n");

		postMethod.releaseConnection();
	}

	// javaeye论坛发帖
	public static void publishMessage(HttpClientInfo clientInfo)
			throws Exception {
		PostMethod post = new PostMethod("/forums/39/topics");

		NameValuePair sys_tag_id = new NameValuePair("topic[sys_tag_id]", "1");// 如何实现用户填写名称，自动转换成数字？？�?
		NameValuePair title = new NameValuePair("topic[title]", "�?个简单的问题");
		NameValuePair body = new NameValuePair("topic[body]",
				"hiberate中多多关系生成的第三张表里�?�样加一个字�?");
		NameValuePair auto_save_id = new NameValuePair("auto_save_id", "");// hidden
		NameValuePair bbcode = new NameValuePair("topic[bbcode]", "");// hidden
		NameValuePair attachments_counter = new NameValuePair(
				"attachments_counter", "");// hidden
		NameValuePair add_to_blog = new NameValuePair("add_to_blog", "true");// hidden

		post.setRequestBody(new NameValuePair[] { sys_tag_id, title, body,
				auto_save_id, bbcode, attachments_counter, add_to_blog });

		int status = clientInfo.getHttpclient().executeMethod(post);

		System.out.println("======publish Message====");
		System.out.println(post.getResponseBodyAsString());
		System.out.println("======end publish Message===" + "\n");

		post.releaseConnection();
	}

	// javaeye论坛发帖（新手讨论）
	public static void publishMessage2(HttpClientInfo clientInfo)
			throws Exception {
		PostMethod post = new PostMethod("/forums/46/topics");

		NameValuePair sys_tag_id = new NameValuePair("topic[sys_tag_id]", "128");// 如何实现用户填写名称，自动转换成数字？？�?
		NameValuePair title = new NameValuePair("topic[title]",
				"struts标签下拉框联动 ");
		NameValuePair body = new NameValuePair("topic[body]", StringUtil
				.readStrFromTxt("F:/test.txt"));
		NameValuePair auto_save_id = new NameValuePair("auto_save_id", "");// hidden
		NameValuePair bbcode = new NameValuePair("topic[bbcode]", "");// hidden
		NameValuePair attachments_counter = new NameValuePair(
				"attachments_counter", "");// hidden
		NameValuePair add_to_blog = new NameValuePair("add_to_blog", "true");// hidden

		post.setRequestBody(new NameValuePair[] { sys_tag_id, title, body,
				auto_save_id, bbcode, attachments_counter, add_to_blog });

		int status = clientInfo.getHttpclient().executeMethod(post);

		System.out.println("======publish Message====");
		System.out.println(status + "\n" + post.getResponseBodyAsString());

		System.out.println("======end publish Message===" + "\n");

		post.releaseConnection();
	}
}
