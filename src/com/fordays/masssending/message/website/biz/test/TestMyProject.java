package com.fordays.masssending.message.website.biz.test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.FileUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCRUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCResult;

public class TestMyProject {
	public static void main(String[] args) {
		try {
			testLocalhost();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testLocalhost() throws Exception {
		String LOGIN_SITE = "192.168.1.91";
		int LOGIN_PORT = 80;
		String CHARSET = "UTF-8";
		String IMG_FORMAT = "jpg";

		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
		HttpClient client = clientInfo.getHttpclient();

		String gethtmlUrl = "/mass-sending/login.jsp";

		GetMethod getMethod = new GetMethod(gethtmlUrl);
		int statusCode = client.executeMethod(getMethod);
		System.out.println("get login html statusCode:" + statusCode);

		String loginHtml = HttpClientUtil.printResponseHtml(getMethod, true,
				false);

		String beforeStr = "<img src=\"";
		String endStr = "\"";
		Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
		Matcher m = p.matcher(loginHtml);// 开始编译
		while (m.find()) {
			String findStr = m.group(1);// 获取被匹配的部分
			System.out.println("imgurl:" + findStr);
			gethtmlUrl = findStr;
		}

		statusCode = client.executeMethod(getMethod);
		System.out.println("get validate image statusCode:" + statusCode);

		File imgFile = FileUtil.saveImageFile(client, gethtmlUrl, IMG_FORMAT);
		String imgFilePath = imgFile.getAbsolutePath();
		System.out.println(imgFilePath);

		OCResult ocresult = OCRUtil.recognizeValidation(imgFile);
		
		if (ocresult.isValidated()) {
			clientInfo.setValidateCode(ocresult.getResultString());
		} else {
			System.out.println("验证码识别失败");
			return;
		}
		clientInfo = testLoginMyProject(clientInfo);// 登录
	}

	public static HttpClientInfo testLoginMyProject(HttpClientInfo clientInfo)
			throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		// 模拟登录
		PostMethod postMethod = new PostMethod(
				"/mass-sending/user/user.do?thisAction=login");
		NameValuePair userNo = new NameValuePair("userNo", "admin");
		NameValuePair userPassword = new NameValuePair("userPassword", "111111");
		NameValuePair rand = new NameValuePair("rand", clientInfo
				.getValidateCode());

		postMethod.setRequestBody(new NameValuePair[] { userNo, userPassword,
				rand });

		int statusCode = client.executeMethod(postMethod);
		System.out.println("after login statusCode:" + statusCode);
		HttpClientUtil.printResponseHtml(postMethod, true, false);

		// 获取并保存登录Cookie
		Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);
		clientInfo.setCookies(cookies);

		postMethod.releaseConnection();
		return clientInfo;
	}
}