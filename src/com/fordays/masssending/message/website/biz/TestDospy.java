package com.fordays.masssending.message.website.biz;

import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.neza.exception.AppException;

public class TestDospy {
	public static void main(String[] args) {
		// testLogin();
		testLogin2();
	}

	public static void testLogin2() {
		try {
			HttpClient client = new HttpClient();
			client.getHostConfiguration().setHost("bbs.dospy.com", 80);
			client.getParams().setParameter(HttpMethodParams.USER_AGENT,
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			PostMethod postMethod = new PostMethod(
					"http://bbs.dospy.com/logging.php");

			postMethod.setRequestHeader("referer", "http://bbs.dospy.com");

			NameValuePair action = new NameValuePair("action", "login");
			NameValuePair formhash = new NameValuePair("formhash", "5dcb0bfd");
			NameValuePair referer = new NameValuePair("referer", "http://bbs.dospy.com");
			NameValuePair loginfield = new NameValuePair("loginfield",
					"username");
			NameValuePair username = new NameValuePair("username",
					"zijingshanke");
			NameValuePair password = new NameValuePair("password", "123456");
			NameValuePair questionid = new NameValuePair("questionid", "0");
			NameValuePair answer = new NameValuePair("answer", "");
			NameValuePair cookietime = new NameValuePair("cookietime",
					"2592000");
			NameValuePair loginmode = new NameValuePair("loginmode", "normal");

			NameValuePair styleid = new NameValuePair("styleid", "1");

		
			
			postMethod.setRequestBody(new NameValuePair[] { action, formhash,
					referer, loginfield, username, password, questionid,
					answer, cookietime, loginmode, styleid });

			int statusCode = client.executeMethod(postMethod);
			System.out.println("after login statusCode:" + statusCode);

			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);

			HttpClientUtil.printResponseHtml(postMethod, "gbk", true, false);
			postMethod.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testLogin() {
		try {
			String LOGIN_SITE = "bbs.dospy.com";
			int LOGIN_PORT = 80;
			String CHARSET = "gbk";

			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

			clientInfo.setLOGIN_NAME("zijingshanke");
			clientInfo.setLOGIN_PASSWORD("123456");

			clientInfo = getLoginHtmlInfo(clientInfo);

			loginWebSite(clientInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			String loginName = clientInfo.getLOGIN_NAME();
			String loginPassword = clientInfo.getLOGIN_PASSWORD();

			String formhashStr = hiddenValue.get("formhash");

			// 模拟登录
			PostMethod postMethod = new PostMethod(
					"http://bbs.dospy.com/logging.php");
			// logging.php?action=login&amp;
			// http://bbs.dospy.com/

			// postMethod.setRequestHeader("Content-Type",
			// "application/x-www-form-urlencoded;charset=GBK");

			NameValuePair action = new NameValuePair("action", "login");
			NameValuePair formhash = new NameValuePair("formhash", formhashStr);
			NameValuePair referer = new NameValuePair("referer",
					"http://bbs.dospy.com");
			NameValuePair loginfield = new NameValuePair("loginfield",
					"username");
			NameValuePair username = new NameValuePair("username", loginName);
			NameValuePair password = new NameValuePair("password",
					loginPassword);
			NameValuePair questionid = new NameValuePair("questionid", "0");
			NameValuePair answer = new NameValuePair("answer", "");
			NameValuePair cookietime = new NameValuePair("cookietime",
					"2592000");
			NameValuePair loginmode = new NameValuePair("loginmode", " ");

			NameValuePair styleid = new NameValuePair("styleid", "");

			postMethod.setRequestBody(new NameValuePair[] { action, formhash,
					referer, loginfield, username, password, questionid,
					answer, cookietime, loginmode, styleid });

			int statusCode = client.executeMethod(postMethod);
			System.out.println("after login statusCode:" + statusCode);

			// client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);

			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);
			clientInfo.setCookies(cookies);

			cookies = HttpClientUtil.getCurrentCookie(clientInfo);
			clientInfo.setSuccess(true);

			HttpClientUtil.printResponseHtml(postMethod, "GBK", false, true);

			postMethod.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	public static HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		try {

			GetMethod redirect = new GetMethod(
					"http://bbs.dospy.com/logging.php?action=login&amp;");

			redirect.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
			System.out.println("getLoginHtmlInfo statusCode:" + statusCode);

			String htmlcontent = redirect.getResponseBodyAsString();
			Parser parser = Parser.createParser(htmlcontent, "gbk");
			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);

			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			for (int i = 0; i < nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof InputTag) {
					InputTag inputTag = (InputTag) nodeList.elementAt(i);
					String name = inputTag.getAttribute("name");
					String value = inputTag.getAttribute("value");

					if ("formhash".equals(name)) {
						hiddenValue.put("formhash", value);
					}
				}
			}
			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
			redirect.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面隐藏域失败<br>" + e.getMessage());
		}
		return clientInfo;
	}
}
