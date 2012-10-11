package com.fordays.masssending.message.website.biz.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.fordays.masssending.base.util.StringUtil;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageUtil;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

public class TesTianYa {
	public static void main(String[] args) {
		try {
			// testTianYa();
			// getIdWriterKeyByUrl("");
			// getStrItem();
			// getIdItem();
			// testChoosePublishMethod();
			testTianYaReply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testTianYaReply() throws Exception {
		String LOGIN_SITE = "login.tianya.cn";
		int LOGIN_PORT = 80;
		String CHARSET = "gbk";

		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

		clientInfo.setLOGIN_NAME("zijingshanke");
		clientInfo.setLOGIN_PASSWORD("598719");

		HttpClient client = clientInfo.getHttpclient();

		String redirectUrl = "http://www.tianya.cn/techforum/content/398/14593.shtml";// 网店
		// redirectUrl =
		// "http://www.tianya.cn/publicforum/content/travel/1/264847.shtml";//
		// 薄饼

		GetMethod get = new GetMethod(redirectUrl);

		int statusCode = client.executeMethod(get);
		System.out.println("statusCode:" + statusCode);

		// Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		String htmlcontent = HttpClientUtil.printResponseHtml(get, "gb2312",
				true, false);
		clientInfo.setResponseBodyAsString(htmlcontent);

		clientInfo = setHiddenInputValueAsReply(clientInfo);

		replyTopic(clientInfo);

	}

	// 回帖
	public static void replyTopic(HttpClientInfo clientInfo) throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		String idItemStr = hiddenValue.get("idItem");
		String strItemStr = hiddenValue.get("strItem");
		String chrAuthorStr = hiddenValue.get("chrAuthor");
		String idArticleStr = hiddenValue.get("idArticle");
		String strTitleStr = hiddenValue.get("strTitle");
		String apnStr = hiddenValue.get("apn");
		String idwriterStr = hiddenValue.get("idwriter");
		if ("".equals(idwriterStr) || idwriterStr == null) {
			idwriterStr = "0";
		}
		String keyStr = hiddenValue.get("key");
		if ("".equals(keyStr) || keyStr == null) {
			keyStr = "0";
		}
		String ttitemStr = hiddenValue.get("ttitem");
		String pageAllStr = hiddenValue.get("pageAll");

		String loginName = clientInfo.getLOGIN_NAME();
		String loginPassword = clientInfo.getLOGIN_PASSWORD();
		String messageContent = "我也刚开了个网店，向楼主学习。请问你卖的东西能";

		String action = "http://post.tianya.cn/new/techforum/content_hotelsubmit.asp?idwriter="
				+ idwriterStr + "&key=" + keyStr + "&ttitem=" + ttitemStr;

		System.out.println("============================");
		System.out.println("idItemStr:" + idItemStr);
		System.out.println("strItemStr:" + strItemStr);
		System.out.println("chrAuthorStr:" + chrAuthorStr);
		System.out.println("idArticleStr:" + idArticleStr);
		System.out.println("strTitleStr:" + strTitleStr);
		System.out.println("apnStr:" + apnStr);
		System.out.println("idwriterStr:" + idwriterStr);
		System.out.println("ttitemStr:" + ttitemStr);
		System.out.println("pageAllStr:" + pageAllStr);
		System.out.println("loginName:" + loginName);
		System.out.println("loginPassword:" + loginPassword);
		System.out.println("key:" + keyStr);
		System.out.println("action:" + action);
		System.out.println("============================");

		PostMethod postMethod = new PostMethod(action);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=GBK");
		NameValuePair idItem = new NameValuePair("idItem", idItemStr);
		NameValuePair strItem = new NameValuePair("strItem", strItemStr);
		NameValuePair chrAuthor = new NameValuePair("chrAuthor", chrAuthorStr);
		NameValuePair idArticle = new NameValuePair("idArticle", idArticleStr);
		NameValuePair strTitle = new NameValuePair("strTitle", strTitleStr);
		NameValuePair idSign = new NameValuePair("idSign", "1");// 页面（简单起见未从页面取）
		NameValuePair pID = new NameValuePair("pID", "1");// 页面
		NameValuePair apn = new NameValuePair("apn", apnStr);
		NameValuePair idwriter = new NameValuePair("idwriter", idwriterStr);
		NameValuePair key = new NameValuePair("key", "0");
		NameValuePair ttitem = new NameValuePair("ttitem", ttitemStr);
		NameValuePair pageAll = new NameValuePair("pageAll", pageAllStr);
		NameValuePair strWriter = new NameValuePair("strWriter", loginName);
		NameValuePair strPassword = new NameValuePair("strPassword",
				loginPassword);
		NameValuePair Submit = new NameValuePair("Submit", "Response");
		NameValuePair strContent = new NameValuePair("strContent",
				messageContent);
		NameValuePair PicDesc = new NameValuePair("PicDesc", "");
		NameValuePair strAlbumPicURL = new NameValuePair("strAlbumPicURL",
				"http://");

		postMethod.setRequestBody(new NameValuePair[] { idItem, strItem,
				chrAuthor, idArticle, strTitle, idSign, pID, apn, idwriter,
				key, ttitem, pageAll, strWriter, strPassword, Submit,
				strContent, PicDesc, strAlbumPicURL });

		clientInfo.setPostMethod(postMethod);// 保存PostMethod

		int statusCode = client.executeMethod(postMethod);

		System.out.println("after reply statusCode:" + statusCode);
		HttpClientUtil.printResponseHtml(postMethod, true, false);

	}

	private static HttpClientInfo setHiddenInputValueAsReply(
			HttpClientInfo clientInfo) {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		String htmlcontent = clientInfo.getResponseBodyAsString();
		try {
			Parser parser = Parser.createParser(htmlcontent, "gbk");
			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);

			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			for (int i = 0; i < nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof InputTag) {
					InputTag inputTag = (InputTag) nodeList.elementAt(i);
					String name = inputTag.getAttribute("name");
					String value = inputTag.getAttribute("value");

					if ("idItem".equals(name)) {
						hiddenValue.put("idItem", value);
					}
					if ("strItem".equals(name)) {
						hiddenValue.put("strItem", value);
					}
					if ("chrAuthor".equals(name)) {
						hiddenValue.put("chrAuthor", value);
					}
					if ("idArticle".equals(name)) {
						hiddenValue.put("idArticle", value);
					}
					if ("strTitle".equals(name)) {
						hiddenValue.put("strTitle", value);
					}
					if ("apn".equals(name)) {
						hiddenValue.put("apn", value);
					}
					if ("idwriter".equals(name)) {
						hiddenValue.put("idwriter", value);
					}
					if ("ttitem".equals(name)) {
						hiddenValue.put("ttitem", value);
					}
					if ("pageAll".equals(name)) {
						hiddenValue.put("pageAll", value);
					}
					if ("apn".equals(name)) {
						hiddenValue.put("apn", value);
					}
					if ("key".equals(name)) {
						hiddenValue.put("key", value);
					}
				}
			}
			clientInfo.setHiddenHtmlValue(hiddenValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public static void testTianYa() throws Exception {
		String LOGIN_SITE = "login.tianya.cn";
		int LOGIN_PORT = 80;
		String CHARSET = "gbk";

		HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
				LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

		clientInfo = testLoginTianYa(clientInfo);// 登录

		String redirectNewUrl = "http://www.tianya.cn/new/publicforum/Compose.asp?strItem=travel&strSubItem";
		String topicType = "吃游八方";
		clientInfo.setRedirectNewUrl(redirectNewUrl);
		clientInfo.setTopicType(topicType);
		clientInfo.setTopicValueMethod("1");

		// 定向到发帖页面，获取相关信息
		clientInfo = MessageUtil.redirectAndSaveInfo(clientInfo);

		publishTopic(clientInfo);
	}

	// 发帖
	public static void publishTopic(HttpClientInfo clientInfo) throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		String idwriter = clientInfo.getHiddenHtmlValue().get("idWriter");
		String Key = clientInfo.getHiddenHtmlValue().get("Key");
		String topicValue = clientInfo.getTopicTypeValue();

		System.out.println("topicTypeValue:" + topicValue);

		String posturl = "http://post.tianya.cn/new/publicforum/compose_submit.asp?idWriter="
				+ idwriter + "&Key=" + Key + "&ttitem=travel";

		System.out.println(posturl);

		PostMethod post = new PostMethod(posturl);
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=GBK");

		NameValuePair strItem = new NameValuePair("strItem", "travel");
		NameValuePair comeby = new NameValuePair("comeby", "");
		NameValuePair strItem_hidden = new NameValuePair("strItem_hidden",
				"travel");
		NameValuePair Submit = new NameValuePair("Submit", "Response");
		NameValuePair strTitle = new NameValuePair("strTitle", "湾仔澳门街");
		NameValuePair strContent = new NameValuePair("strContent", StringUtil
				.readStrFromTxt("F:\\1.txt"));
		NameValuePair strSubItem = new NameValuePair("strSubItem", topicValue);
		NameValuePair PicDesc = new NameValuePair("PicDesc", "");
		NameValuePair strAlbumPicURL = new NameValuePair("strAlbumPicURL", "");
		NameValuePair strMusicURL = new NameValuePair("strMusicURL", "");
		NameValuePair strFlashURL = new NameValuePair("strFlashURL", "");
		NameValuePair IntFlashWidth = new NameValuePair("IntFlashWidth", "400");
		NameValuePair IntFlashHeight = new NameValuePair("IntFlashHeight",
				"300");
		NameValuePair strKind = new NameValuePair("strKind", "(原创)");// (转载)

		post.setRequestBody(new NameValuePair[] { strItem, comeby,
				strItem_hidden, Submit, strTitle, strContent, strSubItem,
				PicDesc, strAlbumPicURL, strMusicURL, strFlashURL,
				IntFlashWidth, IntFlashHeight, strKind });
		int statusCode = client.executeMethod(post);

		System.out.println("after publish statusCode:" + statusCode);
		System.out.println(post.getResponseBodyAsString());
	}

	public static HttpClientInfo testLoginTianYa(HttpClientInfo clientInfo)
			throws Exception {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		HttpClient client = clientInfo.getHttpclient();

		// 模拟登录
		PostMethod postMethod = new PostMethod("/user/v3_loginsubmit.asp");
		NameValuePair action = new NameValuePair("action", "login_action");
		NameValuePair vwriter = new NameValuePair("vwriter", "qmpaytest");
		NameValuePair vpassword = new NameValuePair("vpassword", "123456");
		NameValuePair rmflag = new NameValuePair("rmflag", "1");
		postMethod.setRequestBody(new NameValuePair[] { action, vwriter,
				vpassword, rmflag });

		clientInfo.setPostMethod(postMethod);// 保存PostMethod

		int statusCode = client.executeMethod(postMethod);
		System.out.println("login status:" + statusCode);
		System.out.println("======login success====" + "\n");
		System.out.println(postMethod.getResponseBodyAsString());
		System.out.println("======end login success===" + "\n");

		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				System.out.println("redirected to:" + "\n" + location);

				hiddenValue = getIdWriterKeyByUrl(location);
				clientInfo.setHiddenHtmlValue(hiddenValue);

				// 获取并保存登录Cookie
				Cookie[] cookies = HttpClientUtil.getCurrentCookie(clientInfo
						.getLOGIN_SITE(), clientInfo.getLOGIN_PORT(), client);
				clientInfo.setCookies(cookies);

				System.out.println("---before GetMethod()---");
				GetMethod redirect = new GetMethod(location);
				redirect.setRequestHeader("Cookie", cookies.toString());

				statusCode = client.executeMethod(redirect);
				System.out.println("--after GetMethod " + statusCode);
				// System.out.println(redirect.getResponseBodyAsString());
			}
		}
		postMethod.releaseConnection();
		return clientInfo;
	}

	public static void getStrItem() {
		String location = "http://www.tianya.cn/new/publicforum/Compose.asp?strItem=travel&strSubItem";

		String beforeStr = "strItem=";
		String endStr = "&";
		Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
		Matcher m = p.matcher(location);// 开始编译
		while (m.find()) {
			String findStr = m.group(1);// 获取被匹配的部分
			System.out.println(findStr);
		}
	}

	public static void getIdItem() {
		String location = "http://www.tianya.cn/new/techforum/Compose.asp?iditem=49";

		String beforeStr = "iditem=";
		String endStr = " ";
		Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
		Matcher m = p.matcher(location);// 开始编译
		while (m.find()) {
			String findStr = m.group(1);// 获取被匹配的部分
			System.out.println(findStr);
		}
	}

	/**
	 * 从自动跳转的Location url 中提取Key
	 */
	public static Map<String, String> getIdWriterKeyByUrl(String location) {
		// location =
		// "http://login.hainan.net/hainan/user/v3_loginsubmit_2.asp?strTianyaLoginReturnURL=&strTianyaLoginFowardURL=&submitedpage=&idWriter=33728720&Key=858596924&strWriter=qmpaytest&sys_grade=&ManagerCategory=False&rmCode=78AEEBD810D590278303A88FB1CB4930&pageKey_trans_login=409CEC988C79EDF5E3915291C28F6405";

		Map<String, String> hiddenValue = new HashMap<String, String>();

		int beginIndex = location.indexOf("submitedpage=&");
		int endIndex = location.indexOf("&strWriter");

		location = location.substring(beginIndex + 14, endIndex);
		System.out.println(location);

		Vector<String> keyValues = StringUtil.getVectorString(location, "&");

		for (int i = 0; i < keyValues.size(); i++) {
			String ele = keyValues.get(i);
			String value = "";

			int keyindex = ele.lastIndexOf("Key");
			if (keyindex == 0) {
				value = ele.substring(4);
				System.out.println("Key:" + value);
				hiddenValue.put("Key", value);
			}

			int idwriterIndex = ele.lastIndexOf("idWriter");
			if (idwriterIndex == 0) {
				value = ele.substring(9);
				System.out.println("idWriter:" + value);
				hiddenValue.put("idWriter", value);
			}
		}
		return hiddenValue;
	}

	public static void testChoosePublishMethod() {
		String location = "http://www.tianya.cn/new/publicforum/Compose.asp?strItem=travel&strSubItem";
		location = "http://www.tianya.cn/new/techforum/Compose.asp?iditem=131";
		location = "ddddd";
		location = null;
		try {
			choosePublishMethod(location);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	public static void choosePublishMethod(String newTopicUrl)
			throws AppException {
		if (newTopicUrl != null) {

			int strItemIndex = newTopicUrl.lastIndexOf("strItem=");

			System.out.println("strItemIndex:" + strItemIndex);

			if (strItemIndex == -1) {
				System.out.println("no strItem...");

				int iditemIndex = newTopicUrl.lastIndexOf("iditem=");

				System.out.println("iditemIndex:" + iditemIndex);

				if (iditemIndex == -1) {
					System.out.println("no iditem");
					System.out.println("false");
				} else {
					System.out.println("iditem type");
					System.out.println("true");
				}
			} else {
				System.out.println("strItem type");
				System.out.println("true");
			}
		} else {
			System.out.println("invalid url");
		}
	}
}
