package com.fordays.masssending.message.website.biz;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.util.NodeList;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * 西陆论坛 http://club.xilu.com/ 发帖接口实现类
 */
public class XiLuMessageBizImp extends MessageUtil implements MessageUtilBiz {

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
						messageInfo = publishMessage(clientInfo, messageInfo);
						// return messageInfo;
					}
				}
			}
			// messageInfo = setErrorInfo(clientInfo, messageInfo);
			messageInfo = null;
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常");
		}
		return messageInfo;
	}

	public static void main(String[] args) {
		try {
			String LOGIN_SITE = "club.xilu.com";
			int LOGIN_PORT = 80;
			String CHARSET = "gb2312";
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
			HttpClient client = clientInfo.getHttpclient();
			HttpClientUtil.getCurrentCookieASNoMatch(client);

			Cookie[] cookies = clientInfo.getCookies();

			String location = "http://post.xilu.com/emas/postpage.php?boardid=2696";

			GetMethod redirect = new GetMethod(location);
			// redirect.setRequestHeader("Cookie", cookies.toString());
			int statusCode = client.executeMethod(redirect);
			System.out.println("自动跳转后,statusCode:" + statusCode);
			String htmlcontent = HttpClientUtil.printResponseHtml(redirect,
					false, false);

			Parser parser = Parser.createParser(htmlcontent, "gb2312");
			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);
			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);
			System.out.println(nodeList.toHtml());

			PostMethod post = new PostMethod(
					"http://post.xilu.com/emas/post.php");
			System.out.println("request charset:" + post.getRequestCharSet());

			NameValuePair boardid = new NameValuePair("boardid", "2696");
			NameValuePair smile = new NameValuePair("smile", "1");
			NameValuePair changeuser = new NameValuePair("changeuser", "");
			NameValuePair msgtype = new NameValuePair("msgtype", "0");

			NameValuePair title = new NameValuePair("title", "闲来无事,过来跟大家唠两句");// 标题
			NameValuePair message = new NameValuePair("body",
					"hello world ,大家好。今天天气不错，出太阳了。地板上讨厌的水珠也不见了。。");// 内容

			NameValuePair input_kw = new NameValuePair("input_kw", "");// 关键字

			NameValuePair handpad = new NameValuePair("handpad", "");
			NameValuePair usezt = new NameValuePair("usezt", "0");

			NameValuePair signed = new NameValuePair("signed", "1");// 签名
			NameValuePair username = new NameValuePair("username", "psfww");
			NameValuePair passwd = new NameValuePair("passwd", "0756888");
			NameValuePair credit = new NameValuePair("credit", "");

			post.setRequestBody(new NameValuePair[] { boardid, smile,
					changeuser, msgtype, title, message, input_kw, handpad,
					usezt, signed, username, passwd, credit });

			statusCode = client.executeMethod(post);

			System.out.println("after publishMessage:" + statusCode);

			HttpClientUtil.getCurrentCookie("club.xilu.com", 80, client);

			HttpClientUtil.printResponseHtml(post, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			// Map<String, String> hiddenValue =
			// clientInfo.getHiddenHtmlValue();
			// String formhashStr = hiddenValue.get("formhash").toString();
			// String typeidStr = hiddenValue.get("typeid").toString();
			// String actionValue = hiddenValue.get("action").toString();

			PostMethod post = new PostMethod("/post.php");
			/**
			 * @特别说明：
			 * @1、需设置自己提交参数的charset,匹配目标网站
			 * @2、由于此处postMethodUrl形如 /post.php?fid=8&referer=http%3A//www.gdin.net
			 * @已经URL编码,所以提交之前须 设置application/x-www-form-urlencoded 予以说明
			 */
			// post.setRequestHeader("Content-Type",
			// "application/x-www-form-urlencoded;charset=GBK");
			System.out.println("request charset:" + post.getRequestCharSet());

			NameValuePair title = new NameValuePair("title", titleValue);// 标题
			NameValuePair message = new NameValuePair("message", contentValue);// 内容

			NameValuePair input_kw = new NameValuePair("input_kw", "");// 关键字
			NameValuePair signed = new NameValuePair("signed", "1");// 签名
			NameValuePair username = new NameValuePair("username", messageInfo
					.getLoginName());
			NameValuePair password = new NameValuePair("password", messageInfo
					.getLoginPassword());

			post.setRequestBody(new NameValuePair[] { title, message, input_kw,
					signed, username, password });

			HttpClient client = clientInfo.getHttpclient();

			int statusCode = client.executeMethod(post);

			System.out.println("after publishMessage:" + statusCode);
			HttpClientUtil.printResponseHtml(post, true, false);

			if (200 == statusCode) {
				messageInfo.setSendedStatus(new Long(2));
			} else {
				messageInfo.setSendedStatus(new Long(3));
				messageInfo.setRemark("执行发帖后,statusCode:" + statusCode
						+ ",发帖失败");
				// HttpClientUtil.printResponseHtml(post, "gbk", true);
				System.out.println(post.getResponseBodyAsString());
			}
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setSendedStatus(new Long(3));
			messageInfo.setRemark("发帖报异常");
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
				String htmlcontent = printResponseHtml(redirect, true, false);
				// action
				hiddenValue = setActionValue(htmlcontent, hiddenValue);
				System.out.println("action value:" + hiddenValue.get("action"));
				// formhash
				hiddenValue = setHiddenInputValue(htmlcontent, hiddenValue);
				System.out.println("formhash:" + hiddenValue.get("formhash"));
				// 话题
				hiddenValue = setTypeId(htmlcontent, hiddenValue, "闲谈");
				System.out.println("typeid:" + hiddenValue.get("typeid"));

				clientInfo.setHiddenHtmlValue(hiddenValue);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("重定向到发帖页面失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取发帖页面信息异常");
		}
		return clientInfo;
	}

	private static Map<String, String> setTypeId(String htmlcontent,
			Map<String, String> hiddenValue, String optionText)
			throws AppException {
		Parser parser = Parser.createParser(htmlcontent, "gbk");
		try {
			NodeFilter selectTagfilter = new NodeClassFilter(SelectTag.class);
			NodeList nodeList = parser
					.extractAllNodesThatMatch(selectTagfilter);

			String nodecontent = "<form>" + nodeList.toHtml() + "</form>";

			// System.out.println("select node content:" + "\n" + nodecontent);

			String typeid = HttpClientUtil.getOptionValueByText(nodecontent,
					optionText);
			hiddenValue.put("typeid", typeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hiddenValue;
	}

	private static Map<String, String> setActionValue(String htmlcontent,
			Map<String, String> hiddenValue) throws AppException {
		try {
			Parser parser = Parser.createParser(htmlcontent, "gbk");
			NodeFilter formTagfilter = new NodeClassFilter(FormTag.class);
			NodeList nodeList = parser.extractAllNodesThatMatch(formTagfilter);

			String content = nodeList.toHtml().substring(0, 150);

			// System.out.println(content);

			int beginIndex = content.indexOf("action");
			int endIndex = content.indexOf("yes\"");

			content = content.substring(beginIndex + 8, endIndex + 3);

			hiddenValue.put("action", "/" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hiddenValue;
	}

	private static Map<String, String> setHiddenInputValue(String htmlcontent,
			Map<String, String> hiddenValue) {
		try {
			Parser parser = Parser.createParser(htmlcontent, "gbk");
			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);

			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			String nodeHtml = nodeList.toHtml();
			// System.out.println("--nodeHtml--:" + "\n" + nodeHtml);

			// nodeHtml = nodeHtml.replaceAll("<INPUT " + "(.*?)" + ">",
			// "");// 去除多余的标签
			nodeHtml = nodeHtml.replaceAll("disabled", " ");
			nodeHtml = nodeHtml.replaceAll("\"" + ">", "\" />");// 加上结束符，以符合xml规范
			nodeHtml = nodeHtml.replaceAll("=\" \"", "");// 去除多余符号

			String inputHtml = "<form>" + nodeHtml + "</form>";

			XmlUtil xmlutil = new XmlUtil();
			Document doc = xmlutil.readResult(new StringBuffer(inputHtml));
			List nodes = doc.selectNodes("//form/input");
			Iterator it = nodes.iterator();
			while (it.hasNext()) {
				Element elm = (Element) it.next();
				if (elm.attribute("type") != null) {
					String typeValue = elm.attribute("type").getValue();
					if ("button".equals(typeValue)
							|| "checkbox".equals(typeValue)
							|| "checkbox".equals(typeValue)) {
						continue;
					}

					if (elm.attribute("name") != null) {
						String nameValue = elm.attribute("name").getValue();
						if ("formhash".equals(nameValue)) {
							String value = elm.attribute("value").getValue();
							hiddenValue.put("formhash", value);
						}

						// if ("posttime".equals(nameValue)) {
						// String value = elm.attribute("value")
						// .getValue();
						// hiddenValue.put("posttime", value);
						// }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hiddenValue;
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
			// Map hiddenValue =
			// getLoginHtmlInfo(clientInfo).getHiddenHtmlValue();
			// String formhashValue = hiddenValue.get("formhash").toString();
			// String cookietimeValue =
			// hiddenValue.get("cookietime").toString();

			// 模拟登录
			PostMethod post = new PostMethod(
					"http://login.sina.com.cn/signup/signin.php");
			NameValuePair reference = new NameValuePair("reference", "login");
			NameValuePair entry = new NameValuePair("entry", "sso");
			NameValuePair reg_entry = new NameValuePair("reg_entry", "space");

			NameValuePair username = new NameValuePair("username", clientInfo
					.getLOGIN_NAME());
			NameValuePair password = new NameValuePair("password", clientInfo
					.getLOGIN_PASSWORD());

			post.setRequestBody(new NameValuePair[] { reference, entry,
					reg_entry, username, password });

			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;

			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);
			HttpClientUtil.printResponseHtml(post, true, false);

			// if (200 == statusCode) {
			// // 获取并保存登录Cookie
			// clientInfo = HttpClientUtil.saveCurrentCookie(clientInfo);
			// clientInfo.setSuccess(true);
			// } else {
			// clientInfo.setSuccess(false);
			// clientInfo.setClientRemark("登录失败,username:" + username
			// + "=password:" + password);
			// HttpClientUtil.printResponseHtml(post, true,false);
			// }
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常");
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
					"http://bbs.rayli.com.cn/post.php?action=newthread&fid=159");

			clientInfo.getHttpclient().executeMethod(redirect);

			clientInfo.setStatusCode(redirect.getStatusCode());

			String htmlcontent = redirect.getResponseBodyAsString();

			Parser parser = Parser.createParser(htmlcontent, "gbk");

			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);
			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);
			String nodeHtml = nodeList.toHtml();

			// System.out.println("nodeHtml:" + "\n" + nodeHtml);

			nodeHtml = nodeHtml.replaceAll("<INPUT " + "(.*?)" + ">", "");// 去除多余的标签
			nodeHtml = nodeHtml.replaceAll("\"" + ">", "\" />");// 加上结束符，以符合xml规范

			String inputHtml = "<form>" + nodeHtml + "</form>";

			// System.out.println("inputHtml:" + "\n" + inputHtml);

			XmlUtil xmlutil = new XmlUtil();
			Document doc = xmlutil.readResult(new StringBuffer(inputHtml));

			List nodes = doc.selectNodes("//form/input");

			Iterator it = nodes.iterator();
			while (it.hasNext()) {
				Element elm = (Element) it.next();
				if ("formhash".equals(elm.attribute("name").getValue())) {
					String formhashValue = elm.attribute("value").getValue();
					hiddenValue.put("formhash", formhashValue);
				}

				if ("cookietime".equals(elm.attribute("name").getValue())) {
					String cookietimeValue = elm.attribute("value").getValue();
					hiddenValue.put("cookietime", cookietimeValue);
				}
			}
			redirect.releaseConnection();

			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面信息异常");
			System.out.println("formhash:" + hiddenValue.get("formhash"));
			System.out.println("cookietime:" + hiddenValue.get("cookietime"));
		}
		return clientInfo;
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
}
