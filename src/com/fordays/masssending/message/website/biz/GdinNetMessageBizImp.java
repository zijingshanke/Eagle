package com.fordays.masssending.message.website.biz;

import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.exception.AppException;

/**
 * 广东技术师范学院 http://www.gdin.net/ 发帖接口实现类
 */
public class GdinNetMessageBizImp extends MessageUtil implements MessageUtilBiz {

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
					clientInfo = redirectPostHtml(clientInfo, messageInfo);
					if (clientInfo.isSuccess()) {
						messageInfo = publishMessage(clientInfo, messageInfo);
						return messageInfo;
					}
				}
			}
			messageInfo = setErrorInfo(clientInfo, messageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常<br>" + e.getMessage());
		}
		return messageInfo;
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
			String postMethodUrl = messageInfo.getNewTopicUrl();
			String titleValue = messageInfo.getTitle();
			String contentValue = messageInfo.getContent();
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
			String formhashStr = hiddenValue.get("formhash").toString();
			String posttimeStr = hiddenValue.get("posttime").toString();

			PostMethod post = new PostMethod(postMethodUrl);
			/**
			 * @特别说明：
			 * @1、需设置自己提交参数的charset,匹配目标网站
			 * @2、由于此处postMethodUrl形如 /post.php?fid=8&referer=http%3A//www.gdin.net
			 * @已经URL编码,所以提交之前须 设置application/x-www-form-urlencoded 予以说明
			 */
			post.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");

			System.out.println("request charset:" + post.getRequestCharSet());

			NameValuePair formhash = new NameValuePair("formhash", formhashStr);// 页面取
			NameValuePair posttime = new NameValuePair("posttime", posttimeStr);// 页面取
			NameValuePair wysiwyg = new NameValuePair("wysiwyg", "1");
			NameValuePair iconid = new NameValuePair("iconid", "");
			NameValuePair subject = new NameValuePair("subject", titleValue);// 标题
			NameValuePair checkbox = new NameValuePair("checkbox", "0");
			NameValuePair message = new NameValuePair("message", contentValue);// 内容
			NameValuePair tags = new NameValuePair("tags", "");
			NameValuePair readperm = new NameValuePair("readperm", "");
			NameValuePair htmlon = new NameValuePair("htmlon", "0");
			NameValuePair parseurloff = new NameValuePair("parseurloff", "1");
			NameValuePair smileyoff = new NameValuePair("smileyoff", "1");
			NameValuePair bbcodeoff = new NameValuePair("bbcodeoff", "1");
			NameValuePair tagoff = new NameValuePair("tagoff", "1");
			NameValuePair ordertype = new NameValuePair("ordertype", "1");
			NameValuePair attention_add = new NameValuePair("attention_add",
					"1");
			NameValuePair usesig = new NameValuePair("usesig", "1");
			NameValuePair addfeed = new NameValuePair("addfeed", "1");
			NameValuePair hiddenreplies = new NameValuePair("hiddenreplies",
					"1");
			NameValuePair topicsubmit = new NameValuePair("topicsubmit", "true");

			post.setRequestBody(new NameValuePair[] { formhash, posttime,
					wysiwyg, iconid, subject, checkbox, message, tags,
					readperm, htmlon, parseurloff, smileyoff, bbcodeoff,
					tagoff, ordertype, attention_add, usesig, addfeed,
					hiddenreplies, topicsubmit });

			HttpClient client = clientInfo.getHttpclient();

			int statusCode = client.executeMethod(post);

			if (302 == statusCode) {
				messageInfo.setSendedStatus(new Long(2));
			} else {
				messageInfo.setSendedStatus(new Long(3));
				messageInfo.setRemark("执行发帖后,statusCode:"
						+ statusCode
						+ ",发帖失败<br>"
						+ HttpClientUtil.printResponseHtml(post, "gbk", false,
								false));
			}
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setSendedStatus(new Long(3));
			messageInfo.setRemark("发帖报异常<br>" + e.getMessage());
		}
		return messageInfo;
	}

	/**
	 * 重定向到发帖页面
	 */
	public HttpClientInfo redirectPostHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		try {
			GetMethod redirect = new GetMethod(messageInfo.getNewTopicUrl());
			redirect.setRequestHeader("Cookie", clientInfo.getCookies()
					.toString());

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
			if (200 == statusCode) {
				String htmlcontent = printResponseHtml(redirect, "gbk", false,
						false);
				Parser parser = Parser.createParser(htmlcontent, "gbk");
				NodeFilter tagfilter = new NodeClassFilter(InputTag.class);
				NodeList nodeList = parser.extractAllNodesThatMatch(tagfilter);

				for (int i = 0; i < nodeList.size(); i++) {
					if (nodeList.elementAt(i) instanceof InputTag) {
						InputTag inputTag = (InputTag) nodeList.elementAt(i);
						String name = inputTag.getAttribute("name");
						String value = inputTag.getAttribute("value");

						if ("formhash".equals(name)) {
							hiddenValue.put("formhash", value);
						}
						if ("posttime".equals(name)) {
							hiddenValue.put("posttime", value);
						}
					}
				}
				clientInfo.setHiddenHtmlValue(hiddenValue);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("重定向到发帖页面失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取发帖页面信息异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 登录论坛
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {

		HttpClient client = clientInfo.getHttpclient();
		try {
			// 定向到登录页面，抓取表单数据
			Map hiddenValue = getLoginHtmlInfo(clientInfo).getHiddenHtmlValue();
			String formhashValue = hiddenValue.get("formhash").toString();
			String cookietimeValue = hiddenValue.get("cookietime").toString();

			// 模拟登录
			PostMethod post = new PostMethod("/logging.php");

			NameValuePair action = new NameValuePair("action", "login");
			NameValuePair loginsubmit = new NameValuePair("loginsubmit", "yes");
			NameValuePair formhash = new NameValuePair("formhash",
					formhashValue);// 从页面抓取
			NameValuePair referer = new NameValuePair("referer", "index.php");
			NameValuePair loginfield = new NameValuePair("loginfield",
					"username");
			NameValuePair username = new NameValuePair("username", clientInfo
					.getLOGIN_NAME());
			NameValuePair password = new NameValuePair("password", clientInfo
					.getLOGIN_PASSWORD());
			NameValuePair questionid = new NameValuePair("questionid", "0");
			NameValuePair answer = new NameValuePair("answer", "");
			NameValuePair cookietime = new NameValuePair("cookietime",
					cookietimeValue);
			post.setRequestBody(new NameValuePair[] { action, loginsubmit,
					formhash, referer, loginfield, username, password,
					questionid, answer, cookietime });

			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;

			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);

			if (302 == statusCode) {
				// 获取并保存登录Cookie
				clientInfo = HttpClientUtil.saveCurrentCookie(clientInfo);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				String errorContent = "登录失败,username:" + username
						+ "=password:" + password;
				errorContent += HttpClientUtil.printResponseHtml(post, false,
						false);
				clientInfo.setClientRemark(errorContent);
			}
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 抓取登陆页信息
	 * 
	 */
	public HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		try {
			GetMethod redirect = new GetMethod(
					"/logging.php?action=login&amp;loginsubmit=yes");

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
			clientInfo.setStatusCode(statusCode);

			if (200 == statusCode) {
				String htmlcontent = redirect.getResponseBodyAsString();

				Parser parser = Parser.createParser(htmlcontent, "gbk");

				NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);
				NodeList nodeList = parser
						.extractAllNodesThatMatch(inputTagfilter);

				for (int i = 0; i < nodeList.size(); i++) {
					if (nodeList.elementAt(i) instanceof InputTag) {
						InputTag inputTag = (InputTag) nodeList.elementAt(i);
						String name = inputTag.getAttribute("name");
						String value = inputTag.getAttribute("value");

						if ("formhash".equals(name)) {
							hiddenValue.put("formhash", value);
						}
						if ("cookietime".equals(name)) {
							hiddenValue.put("cookietime", value);
						}
					}
				}
				clientInfo.setHiddenHtmlValue(hiddenValue);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("页面信息异常");
			}
			redirect.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面信息异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	private static void backupGetHiddenValue() {
		// ---------------------------login html--------------
		// String nodeHtml = nodeList.toHtml();
		// nodeHtml = nodeHtml.replaceAll("<INPUT " + "(.*?)" + ">", "");//
		// 去除多余的标签
		// nodeHtml = nodeHtml.replaceAll("\"" + ">", "\" />");// 加上结束符，以符合xml规范
		// String inputHtml = "<form>" + nodeHtml + "</form>";
		// // System.out.println("inputHtml:" + "\n" + inputHtml);
		// XmlUtil xmlutil = new XmlUtil();
		// Document doc = xmlutil.readResult(new StringBuffer(inputHtml));
		//
		// List nodes = doc.selectNodes("//form/input");
		//
		// Iterator it = nodes.iterator();
		// while (it.hasNext()) {
		// Element elm = (Element) it.next();
		// if ("formhash".equals(elm.attribute("name").getValue())) {
		// String formhashValue = elm.attribute("value").getValue();
		// hiddenValue.put("formhash", formhashValue);
		// }
		//
		// if ("cookietime".equals(elm.attribute("name").getValue())) {
		// String cookietimeValue = elm.attribute("value").getValue();
		// hiddenValue.put("cookietime", cookietimeValue);
		// }
		// }
		// ---------------------------redirect post html----------
		// nodeHtml = nodeHtml.replaceAll("<INPUT " + "(.*?)" + ">", "");//
		// 去除多余的标签
		// nodeHtml = nodeHtml.replaceAll("disabled", " ");
		// nodeHtml = nodeHtml.replaceAll("\"" + ">", "\" />");// 加上结束符，以符合xml规范
		// nodeHtml = nodeHtml.replaceAll("=\" \"", "");// 去除多余符号
		// String inputHtml = "<form>" + nodeHtml + "</form>";
		// XmlUtil xmlutil = new XmlUtil();
		// Document doc = xmlutil.readResult(new StringBuffer(inputHtml));
		// List nodes = doc.selectNodes("//form/input");
		// Iterator it = nodes.iterator();
		// while (it.hasNext()) {
		// Element elm = (Element) it.next();
		// if (elm.attribute("type") != null) {
		// String typeValue = elm.attribute("type").getValue();
		// if ("button".equals(typeValue)
		// || "checkbox".equals(typeValue)
		// || "checkbox".equals(typeValue)) {
		// continue;
		// }
		// if (elm.attribute("name") != null) {
		// String nameValue = elm.attribute("name").getValue();
		// if ("formhash".equals(nameValue)) {
		// String value = elm.attribute("value")
		// .getValue();
		// hiddenValue.put("formhash", value);
		// }
		// if ("posttime".equals(nameValue)) {
		// String value = elm.attribute("value")
		// .getValue();
		// hiddenValue.put("posttime", value);
		// }
		// }
		// }
		// }
	}
}
