package com.fordays.masssending.message.website.biz;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.fordays.masssending.base.util.StringUtil;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.FileUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCRUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCResult;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.sun.crypto.provider.HmacMD5;

/**
 * 上海热线 http://www.online.sh.cn 发帖接口实现类
 * 
 * 
 * 
 */
public class SHOnlineMessageBizImp extends MessageUtil implements
		MessageUtilBiz {

	/**
	 * 论坛发帖
	 */
	public MessageInfo publishMessage(MessageInfo messageInfo)
			throws AppException {
		try {
			// 初始化HttpClientInfo
			HttpClientInfo clientInfo = MessageUtil
					.initHttpClientInfo(messageInfo);

			if (clientInfo.isSuccess()) {
				clientInfo = loginWebSite(clientInfo);

				if (clientInfo.isSuccess()) {
					// clientInfo = redirectPostHtml(clientInfo, messageInfo);
					if (clientInfo.isSuccess()) {
						// messageInfo = publishMessage(clientInfo,
						// messageInfo);
						// return messageInfo;
					}
				}
			}
			messageInfo = setErrorInfo(clientInfo, messageInfo);
			// messageInfo = null;
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常");
		}
		return messageInfo;
	}

	/**
	 * 登录论坛
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {
		try {
			clientInfo = getLoginHtmlInfo(clientInfo);// 抓取登录页面信息
			if (clientInfo.isSuccess()) {
				HttpClient client = clientInfo.getHttpclient();
				Map<String, String> hiddenValue = clientInfo
						.getHiddenHtmlValue();

				clientInfo = login1(clientInfo);

				clientInfo = login2(clientInfo);

				clientInfo = login3(clientInfo);

				clientInfo = getCookiesElement(clientInfo);

				clientInfo = login3(clientInfo);

				clientInfo = login4(clientInfo);

				// --------------------------------------------------------------

				// GetMethod redirect = new GetMethod(
				// "http://bbs.online.sh.cn/forum/post.php?action=newthread&fid=1130");
				// redirect.setRequestHeader("Cookie", clientInfo.getCookies()
				// .toString());
				// int statusCode = client.executeMethod(redirect);
				// System.out.println("after redirect index.php statusCode:"
				// + statusCode);
				//
				// HttpClientUtil.printResponseHtml(redirect, "gbk", true,
				// false);
			} else {
				return clientInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	public HttpClientInfo login4(HttpClientInfo clientInfo) throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			String htmlcontent = clientInfo.getResponseBodyAsString();

			String posturl = "";

			String beforeStr = "action=\"";
			String endStr = "\">";
			Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
			Matcher m = p.matcher(htmlcontent);// 开始编译
			while (m.find()) {
				posturl = m.group(1);// 获取被匹配的部分
			}
			System.out.println("action:" + posturl);

			String actionPath = posturl;
			// actionPath = getActionPath(clientInfo, posturl);

			PostMethod postMethod4 = new PostMethod(actionPath);
			postMethod4.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;");// charset=GBK
			// postMethod4.setRequestHeader("Cookie", clientInfo.getCookies()
			// .toString());

			// NameValuePair returnNvp = new NameValuePair("return", returnStr);
			// NameValuePair sign = new NameValuePair("sign", signStr);

			NameValuePair returnparameter = new NameValuePair(
					"returnparameter", "");
			NameValuePair siteid = new NameValuePair("siteid", "100420");
			NameValuePair sparevalue = new NameValuePair("sparevalue", "");
			postMethod4.setRequestBody(new NameValuePair[] { returnparameter,
					siteid, sparevalue });
			int statusCode = client.executeMethod(postMethod4);
			System.out.println("====after login 4,statusCode:" + statusCode);

			HttpClientUtil.printResponseHtml(postMethod4, "gbk", true, false);

			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
			HttpClientUtil.getCurrentCookieASNoMatch(client);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public HttpClientInfo getActionPath(HttpClientInfo clientInfo,
			String posturl) {
		try {
			clientInfo = decodeReturnParams(clientInfo, posturl);
		} catch (AppException e) {
			e.printStackTrace();
		}
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		String actionPath = hiddenValue.get("actionPath") + "?";
		String returnStr = URLEncoder.encode(hiddenValue.get("return"));
		String signStr = URLEncoder.encode(hiddenValue.get("sign"));
		System.out.println("action:" + actionPath);
		System.out.println("return:" + returnStr);
		System.out.println("sign:" + signStr);
		actionPath += "return=" + returnStr + "&amp;sign=" + signStr;
		System.out.println("posturl:" + actionPath);
		return clientInfo;
	}

	public HttpClientInfo decodeReturnParams(HttpClientInfo clientInfo,
			String url) throws AppException {
		try {
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
			// String url =
			// "http://bbs.online.sh.cn/sso3/index.php?return=Dr0kfq1TBk7vi5m3sOiOftVAxE67bQ%2FPYrGWjRkxosgfqykZiaZVHaXXefEA36yow5LMZ%2BEZAp4%3D&amp;sign=1NBEGgQfknogADgeRknzBP2D3Uw%3D";

			url = URLDecoder.decode(url, "gbk");
			System.out.println(url);
			url = url.replaceAll("&amp;", "&");
			System.out.println(url);

			String actionPath = url.substring(0, url.lastIndexOf("?"));
			System.out.println("actionPath=" + actionPath);

			url = url.substring(url.lastIndexOf("?") + 1, url.length());
			System.out.println(url);
			String returnStr = url.substring(url.lastIndexOf("return=") + 7,
					url.lastIndexOf("&"));
			System.out.println("return=" + returnStr);

			String signStr = url.substring(url.lastIndexOf("sign=") + 5, url
					.length());
			System.out.println("sign=" + signStr);

			hiddenValue.put("return", returnStr);
			hiddenValue.put("sign", signStr);
			hiddenValue.put("actionPath", actionPath);
			clientInfo.setHiddenHtmlValue(hiddenValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public HttpClientInfo getCookiesElement(HttpClientInfo clientInfo) {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		Cookie[] cookies = clientInfo.getCookies();

		if (cookies != null) {
			if (cookies.length == 0) {
				System.out.println("========Cookie is None===========");
			} else {
				for (int i = 0; i < cookies.length; i++) {
					String cookieStr = cookies[i].toString();
					int length = cookieStr.length();

					int timeIndex = cookieStr.indexOf("sso_timestamp");
					int signIdex = cookieStr.indexOf("sso_sign");
					int tokenIdex = cookieStr.indexOf("sso_token");
					int usernameIdex = cookieStr.indexOf("sso_username");
					int uuidIdex = cookieStr.indexOf("sso_uuid");

					if (timeIndex == 0) {
						String value = cookieStr.substring(timeIndex + 14,
								length);
						hiddenValue.put("sso_timestamp", value);
					}
					if (signIdex == 0) {
						String value = cookieStr.substring(timeIndex + 9,
								length);
						hiddenValue.put("sso_sign", value);
					}
					if (tokenIdex == 0) {
						String value = cookieStr.substring(timeIndex + 14,
								length);
						hiddenValue.put("sso_token", value);
					}
					if (usernameIdex == 0) {
						String value = cookieStr.substring(timeIndex + 14,
								length);
						hiddenValue.put("sso_username", value);
					}
					if (uuidIdex == 0) {
						String value = cookieStr.substring(timeIndex + 14,
								length);
						hiddenValue.put("sso_uuid", value);
					}
				}
				clientInfo.setHiddenHtmlValue(hiddenValue);
			}
		} else {
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("Cookies is null");
		}
		return clientInfo;
	}

	public HttpClientInfo login1(HttpClientInfo clientInfo) throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			String loginName = clientInfo.getLOGIN_NAME();
			String passwordStr = clientInfo.getLOGIN_PASSWORD();
			String formhashStr = hiddenValue.get("formhash");
			String comfirmCodeStr = hiddenValue.get("comfirmCode");
			String cookietimeStr = hiddenValue.get("cookietime");
			// 模拟登录
			String posturl = "http://sso.online.sh.cn/passport/action/login2";
			PostMethod postMethod = new PostMethod(posturl);
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			NameValuePair formhash = new NameValuePair("formhash", formhashStr);
			NameValuePair username = new NameValuePair("username", loginName);
			NameValuePair password = new NameValuePair("password", passwordStr);
			NameValuePair cryptProv = new NameValuePair("cryptProv", "DESede");
			NameValuePair comfirmCode = new NameValuePair("comfirmCode",
					comfirmCodeStr);
			NameValuePair siteid = new NameValuePair("siteid", "100420");
			NameValuePair cookietime = new NameValuePair("cookietime",
					cookietimeStr);
			postMethod.setRequestBody(new NameValuePair[] { formhash, username,
					password, cryptProv, comfirmCode, siteid, cookietime });

			int statusCode = client.executeMethod(postMethod);
			System.out.println("===1====after login statusCode:" + statusCode);
			HttpClientUtil.printResponseHtml(postMethod, true, false);

			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);

			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);
			clientInfo.setCookies(cookies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public HttpClientInfo login2(HttpClientInfo clientInfo) throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			String loginName = clientInfo.getLOGIN_NAME();
			String passwordStr = clientInfo.getLOGIN_PASSWORD();
			String comfirmCodeStr = hiddenValue.get("comfirmCode");
			// -------------------------------------------
			// 模拟用户验证
			String posturl = "http://sso.online.sh.cn/userauth/oldauth/appauth.do";
			PostMethod postMethod2 = new PostMethod(posturl);
			postMethod2.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			NameValuePair siteid = new NameValuePair("siteid", "100420");
			NameValuePair returnparameter = new NameValuePair(
					"returnparameter", "");
			NameValuePair comfirmCode = new NameValuePair("comfirmCode",
					comfirmCodeStr);
			NameValuePair username = new NameValuePair("username", loginName);
			NameValuePair password = new NameValuePair("password", passwordStr);
			NameValuePair entrance = new NameValuePair("entrance", "");
			NameValuePair cryptType = new NameValuePair("cryptType", "DESede");
			postMethod2.setRequestBody(new NameValuePair[] { siteid,
					returnparameter, comfirmCode, username, password, entrance,
					cryptType });

			int statusCode = client.executeMethod(postMethod2);

			System.out.println("==2===after post appauth statusCode:"
					+ statusCode);
			HttpClientUtil.printResponseHtml(postMethod2, true, false);
			// -------------------------------------------
			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);
			clientInfo.setCookies(cookies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public HttpClientInfo login3(HttpClientInfo clientInfo) throws AppException {

		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			String loginName = clientInfo.getLOGIN_NAME();
			String passwordStr = clientInfo.getLOGIN_PASSWORD();
			String comfirmCodeStr = hiddenValue.get("comfirmCode");
			String sso_signStr = hiddenValue.get("sso_sign");
			String sso_timestampStr = hiddenValue.get("sso_timestamp");
			if (sso_signStr == null) {
				sso_signStr = "";
			}
			if (sso_timestampStr == null) {
				sso_timestampStr = "";
			}

			// -------------------------------------------
			// 模拟平台验证
			String posturl = "http://sso.online.sh.cn/userauth/olduniauth/checkcookie.do";
			PostMethod postMethod3 = new PostMethod(posturl);
			postMethod3.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			NameValuePair type = new NameValuePair("type", "app");
			NameValuePair ssoUserName = new NameValuePair("ssoUserName",
					loginName);
			NameValuePair sso_sign = new NameValuePair("sso_sign", sso_signStr);
			NameValuePair sso_timestamp = new NameValuePair("sso_timestamp",
					sso_timestampStr);
			NameValuePair siteid = new NameValuePair("siteid", "100420");
			NameValuePair cryptType = new NameValuePair("cryptType", "DESede");
			NameValuePair returnparameter = new NameValuePair(
					"returnparameter", "");
			NameValuePair entrance = new NameValuePair("entrance", "");
			NameValuePair version = new NameValuePair("version", "");
			NameValuePair sppare = new NameValuePair("sppare", "");
			NameValuePair username = new NameValuePair("username", loginName);
			NameValuePair password = new NameValuePair("password", passwordStr);
			NameValuePair comfirmCode = new NameValuePair("comfirmCode",
					comfirmCodeStr);

			postMethod3.setRequestBody(new NameValuePair[] { type, ssoUserName,
					sso_sign, sso_timestamp, siteid, cryptType,
					returnparameter, entrance, version, sppare, username,
					password, comfirmCode });

			int statusCode = client.executeMethod(postMethod3);

			System.out.println("==3===after checkcookie statusCode:"
					+ statusCode);
			String htmlcontent = HttpClientUtil.printResponseHtml(postMethod3,
					true, false);
			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);

			clientInfo.setCookies(cookies);
			clientInfo.setResponseBodyAsString(htmlcontent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	/**
	 * 
	 */
	public HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			GetMethod redirect = new GetMethod(
					"http://bbs.online.sh.cn/forum/logging.php?action=login");
			int statusCode = client.executeMethod(redirect);
			System.out.println("getLoginHtml statusCode:" + statusCode);

			String htmlcontent = HttpClientUtil.printResponseHtml(redirect,
					false, false);
			hiddenValue = setHiddenInputValue(htmlcontent, hiddenValue);

			String rand = String.valueOf(Math.floor(Math.random() * 1000000));
			rand = rand.substring(0, rand.length() - 2);
			// System.out.println("rand:" + rand);
			String url = "http://sso.online.sh.cn/passport/action/IdentifyCode?f="
					+ rand;

			String IMG_FORMAT = "jpg";
			// File originalFile = FileUtil.saveImageFile(client, url,
			// IMG_FORMAT);
			File originalFile = FileUtil.saveImageFile(clientInfo, client, url,
					IMG_FORMAT).getImgFile();

			OCResult ocresult = OCRUtil.recognizeValidation(originalFile);

			if (ocresult.isValidated()) {
				String comfirmCode = ocresult.getResultString();
				System.out.println("comfirmCode:" + comfirmCode);
				hiddenValue.put("comfirmCode", comfirmCode);
				clientInfo.setHiddenHtmlValue(hiddenValue);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("验证码识别失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面<br>" + e.getMessage());
		}
		return clientInfo;
	}

	private static Map<String, String> setHiddenInputValue(String htmlcontent,
			Map<String, String> hiddenValue) {
		try {
			Parser parser = Parser.createParser(htmlcontent, "gbk");
			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);

			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			String nodeHtml = nodeList.toHtml();
			// System.out.println("--nodeHtml--:" + "\n" + nodeHtml);

			nodeHtml = nodeHtml.replaceAll("<INPUT " + "(.*?)" + ">", "");// 去除多余的标签
			nodeHtml = nodeHtml.replaceAll("disabled", " ");
			nodeHtml = nodeHtml.replaceAll("\"" + ">", "\" />");// 加上结束符，以符合xml规范
			nodeHtml = nodeHtml.replaceAll("=\" \"", "");// 去除多余符号

			String inputHtml = "<form>" + nodeHtml + "</form>";

			// System.out.println("----------inputHtml:" + "\n" + inputHtml);

			XmlUtil xmlutil = new XmlUtil();
			Document doc = xmlutil.readResult(new StringBuffer(inputHtml));
			List nodes = doc.selectNodes("//form/input");
			Iterator it = nodes.iterator();
			while (it.hasNext()) {
				Element elm = (Element) it.next();
				if (elm.attribute("type") != null) {
					String typeValue = elm.attribute("type").getValue();
					if ("button".equals(typeValue)) {
						continue;
					}

					if (elm.attribute("name") != null) {
						String nameValue = elm.attribute("name").getValue();
						if ("formhash".equals(nameValue)) {
							String value = elm.attribute("value").getValue();
							hiddenValue.put("formhash", value);
						}

						if ("cookietime".equals(nameValue)) {
							String value = elm.attribute("value").getValue();
							hiddenValue.put("cookietime", value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hiddenValue;
	}

	/**
	 * 登录论坛 (www.online.bbs.cn首页登录)
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite_index(HttpClientInfo clientInfo)
			throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();

			Map<String, String> hiddenValue = getLoginHtmlInfo(clientInfo)
					.getHiddenHtmlValue();

			String loginName = clientInfo.getLOGIN_NAME();
			String passwordStr = clientInfo.getLOGIN_PASSWORD();
			String validateCode = hiddenValue.get("validateCode");

			System.out.println("validateCode:" + validateCode);

			// 模拟登录
			PostMethod postMethod = new PostMethod(
					"http://sso.online.sh.cn/user/userstaticlogin.do");
			NameValuePair activetype = new NameValuePair("activetype", "0");
			NameValuePair issend = new NameValuePair("issend", "0");
			NameValuePair serialNumber = new NameValuePair("serialNumber", "");
			NameValuePair clientSignature = new NameValuePair(
					"clientSignature", "");
			NameValuePair userCert = new NameValuePair("userCert", "");
			NameValuePair authtype = new NameValuePair("authtype", "2");
			NameValuePair cryptProv = new NameValuePair("cryptProv",
					"ccit_esy_client_p12");
			NameValuePair useraccount = new NameValuePair("useraccount",
					loginName);
			NameValuePair password = new NameValuePair("password", passwordStr);
			NameValuePair verifyCode = new NameValuePair("verifyCode",
					validateCode);
			postMethod.setRequestBody(new NameValuePair[] { activetype, issend,
					serialNumber, clientSignature, userCert, authtype,
					cryptProv, useraccount, password, verifyCode, });

			clientInfo.setPostMethod(postMethod);// 保存PostMethod

			int statusCode = client.executeMethod(postMethod);
			System.out.println("after login statusCode:" + statusCode);
			HttpClientUtil.printResponseHtml(postMethod, true, false);

			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);

			GetMethod redirect = new GetMethod(
					"http://sso.online.sh.cn/jsp/main.jsp");
			redirect.setRequestHeader("Cookie", cookies.toString());
			statusCode = client.executeMethod(redirect);
			System.out.println("after redirect index.php statusCode:"
					+ statusCode);

			HttpClientUtil.printResponseHtml(redirect, "gbk", true, false);

			postMethod.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常");
		}
		return clientInfo;
	}

	/**
	 * 
	 */
	public HttpClientInfo getLoginHtmlInfo_index(HttpClientInfo clientInfo)
			throws AppException {
		try {
			HttpClient client = clientInfo.getHttpclient();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			GetMethod redirect = new GetMethod(
					"http://sso.online.sh.cn/user/loginssovalidate.do");

			int statusCode = client.executeMethod(redirect);
			System.out.println("statusCode:" + statusCode);

			// HttpClientUtil.printResponseHtml(redirect, false, false);

			String url = "http://sso.online.sh.cn/validatecodeimg";
			String IMG_FORMAT = "jpg";
			File originalFile = FileUtil.saveImageFile(client, url, IMG_FORMAT);
			System.out.println("originalFile:" + originalFile);

			OCResult ocresult = OCRUtil.recognizeValidation(originalFile);

			if (ocresult.isValidated()) {
				String validateCode = ocresult.getResultString();
				System.out.println("validateCode:" + validateCode);
				hiddenValue.put("validateCode", validateCode);
				clientInfo.setHiddenHtmlValue(hiddenValue);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("验证码识别失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面");
		}
		return clientInfo;
	}

	/**
	 * 论坛发帖
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 * @param MessageInfo
	 *            messageInfo
	 */
	public static MessageInfo publishMessage(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws Exception {
		try {
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			PostMethod post = null;

			clientInfo = publishMessage_strItem(clientInfo, messageInfo);

			if (clientInfo.isSuccess()) {
				post = clientInfo.getPostMethod();
				int statusCode = clientInfo.getHttpclient().executeMethod(post);

				System.out.println("after publishMessage:" + statusCode);

				if (302 == statusCode) {
					messageInfo.setSendedStatus(new Long(2));
				} else {
					messageInfo.setSendedStatus(new Long(3));

					String remark = "执行发帖后,statusCode:" + statusCode + ",发帖失败"
							+ "\n";
					remark += HttpClientUtil.printResponseHtml(post, "GBK",
							true, false);

					messageInfo.setRemark(remark);
				}
				post.releaseConnection();
			} else {
				return setErrorInfo(clientInfo, messageInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setSendedStatus(new Long(3));
			messageInfo.setRemark("发帖异常");
		}
		return messageInfo;
	}

	/**
	 * 发帖方法strItem
	 */
	public static HttpClientInfo publishMessage_strItem(
			HttpClientInfo clientInfo, MessageInfo messageInfo)
			throws Exception {
		try {
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
			String strItemValue = hiddenValue.get("strItem");
			String idwriter = hiddenValue.get("idWriter");
			String Key = hiddenValue.get("Key");
			String topicValue = messageInfo.getTopic();

			System.out.println("topicValue:" + topicValue);

			String posturl = "http://post.tianya.cn/new/publicforum/compose_submit.asp?idWriter="
					+ idwriter + "&Key=" + Key + "&ttitem=" + strItemValue;

			System.out.println("posturl" + posturl);

			PostMethod post = new PostMethod(posturl);
			post.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");

			NameValuePair strItem = new NameValuePair("strItem", strItemValue);
			NameValuePair comeby = new NameValuePair("comeby", "");
			NameValuePair strItem_hidden = new NameValuePair("strItem_hidden",
					strItemValue);
			NameValuePair Submit = new NameValuePair("Submit", "Response");
			NameValuePair strTitle = new NameValuePair("strTitle", messageInfo
					.getTitle());
			NameValuePair strContent = new NameValuePair("strContent",
					messageInfo.getContent());
			NameValuePair strSubItem = new NameValuePair("strSubItem",
					topicValue);
			NameValuePair PicDesc = new NameValuePair("PicDesc", "");
			NameValuePair strAlbumPicURL = new NameValuePair("strAlbumPicURL",
					"");
			NameValuePair strMusicURL = new NameValuePair("strMusicURL", "");
			NameValuePair strFlashURL = new NameValuePair("strFlashURL", "");
			NameValuePair IntFlashWidth = new NameValuePair("IntFlashWidth",
					"400");
			NameValuePair IntFlashHeight = new NameValuePair("IntFlashHeight",
					"300");
			NameValuePair strKind = new NameValuePair("strKind", "(原创)");// (转载)

			post.setRequestBody(new NameValuePair[] { strItem, comeby,
					strItem_hidden, Submit, strTitle, strContent, strSubItem,
					PicDesc, strAlbumPicURL, strMusicURL, strFlashURL,
					IntFlashWidth, IntFlashHeight, strKind });
			clientInfo.setPostMethod(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	/**
	 * 重定向到发帖页面
	 */
	public HttpClientInfo redirectPostHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {

		return clientInfo;
	}

	/**
	 * 从发帖页面url取版块value(strItem)
	 */
	private static HttpClientInfo getStrItemByUrl(String location,
			HttpClientInfo clientInfo) {
		// String location =
		// "http://www.tianya.cn/new/publicforum/Compose.asp?strItem=travel&strSubItem";

		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		try {
			String beforeStr = "strItem=";
			String endStr = "&";
			Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
			Matcher m = p.matcher(location);// 开始编译
			while (m.find()) {
				String findStr = m.group(1);// 获取被匹配的部分
				System.out.println("strItem:" + findStr);
				hiddenValue.put("strItem", findStr);
			}
			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("发帖页面URL取strItem异常");
		}
		return clientInfo;
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
