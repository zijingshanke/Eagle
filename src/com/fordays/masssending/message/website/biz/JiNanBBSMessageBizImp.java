package com.fordays.masssending.message.website.biz;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
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
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * 暨南大学珠海分校http://bbs.jnustu.net发帖接口实现类
 */
public class JiNanBBSMessageBizImp implements MessageUtilBiz {

	/**
	 * 论坛发帖(论坛发帖除外)
	 */
	public MessageInfo publishMessage(MessageInfo messageInfo)
			throws AppException {
		try {
			// 初始化HttpClientInfo
			HttpClientInfo clientInfo = MessageUtil
					.initHttpClientInfo(messageInfo);

			clientInfo = loginWebSite(clientInfo);

			clientInfo = redirectPostHtml(clientInfo, messageInfo);

			messageInfo = publishMessage(clientInfo, messageInfo);
			messageInfo = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageInfo;
	}

	/**
	 * 跳转到发帖页面
	 */
	public HttpClientInfo redirectPostHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		// ----------
		// try {
		// GetMethod redirect = new GetMethod("/ajax.php?action=updateseccode");
		// redirect.setRequestHeader("Cookie", clientInfo.getCookies()
		// .toString());
		// int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
		// if (200 == statusCode) {
		// HttpClientUtil.printResponseHtml(redirect, "utf-8", true);// -----
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//

		clientInfo.setRedirectNewUrl(messageInfo.getNewTopicUrl());
		clientInfo.setTopicType(messageInfo.getTopic());
		clientInfo.setTopicValueMethod("2");

		try {
			// clientInfo = MessageUtil.redirectAndSaveInfo(clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
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
			String postMethodUrl = messageInfo.getNewTopicUrl();

			postMethodUrl = postMethodUrl.substring(0,
					(postMethodUrl.length() - 4));

			String topicValue = clientInfo.getTopicTypeValue();
			String titleValue = messageInfo.getTitle();
			String contentValue = messageInfo.getContent();

			System.out.println("postMethodUrl:" + postMethodUrl);

			PostMethod post = new PostMethod(postMethodUrl);
			NameValuePair sys_tag_id = new NameValuePair("topic[sys_tag_id]",
					topicValue);
			NameValuePair title = new NameValuePair("topic[title]", titleValue);
			NameValuePair body = new NameValuePair("topic[body]", contentValue);
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

			messageInfo.setSendedStatus(new Long(2));
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setSendedStatus(new Long(3));
		}
		return messageInfo;
	}

	/**
	 * 登录BBS
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {

		HttpClient client = clientInfo.getHttpclient();

		// 定向到登录页面，抓取表单数据
		Map hiddenValue = getLoginHtmlInfo(clientInfo).getHiddenHtmlValue();

		String formhashValue = hiddenValue.get("formhash").toString();
		String cookietimeValue = hiddenValue.get("cookietime").toString();

		// 模拟登录
		PostMethod post = new PostMethod("/logging.php");

		NameValuePair action = new NameValuePair("action", "login");
		NameValuePair loginsubmit = new NameValuePair("loginsubmit", "yes");
		NameValuePair formhash = new NameValuePair("formhash", formhashValue);// 从页面抓取
		NameValuePair referer = new NameValuePair("referer", "index.php");
		NameValuePair loginfield = new NameValuePair("loginfield", "username");
		NameValuePair username = new NameValuePair("username", clientInfo
				.getLOGIN_NAME());
		NameValuePair password = new NameValuePair("password", clientInfo
				.getLOGIN_PASSWORD());
		NameValuePair questionid = new NameValuePair("questionid", "0");

		NameValuePair answer = new NameValuePair("answer", "");
		NameValuePair cookietime = new NameValuePair("cookietime",
				cookietimeValue);

		post.setRequestBody(new NameValuePair[] { action, loginsubmit,
				formhash, referer, loginfield, username, password, questionid,
				answer, cookietime });

		clientInfo.setPostMethod(post);// 保存PostMethod

		int statusCode = 0;
		try {
			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);
			// System.out.println(post.getResponseBodyAsString());

			// 获取并保存登录Cookie
			clientInfo = HttpClientUtil.saveCurrentCookie(clientInfo);
			post.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
					"/logging.php?action=login&referer=http%3A//bbs.jnustu.net/");

			clientInfo.getHttpclient().executeMethod(redirect);

			clientInfo.setStatusCode(redirect.getStatusCode());

			String htmlcontent = redirect.getResponseBodyAsString();
			String charset = "UTF-8";

			Parser parser = Parser.createParser(htmlcontent, charset);

			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);
			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			String inputHtml = "<form>" + nodeList.toHtml() + "</form>";
			System.out.println("inputHtml:" + "\n" + inputHtml);

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
			clientInfo.setHiddenHtmlValue(hiddenValue);
			redirect.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	public static void autoRedirect(int statusCode, PostMethod postMethod,
			HttpClient client, Cookie[] cookies) throws AppException {
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				System.out.println("redirected to:" + "\n" + location);

				// System.out.println("---before GetMethod()---");
				GetMethod redirect = new GetMethod(location);
				redirect.setRequestHeader("Cookie", cookies.toString());
				try {
					statusCode = client.executeMethod(redirect);
					System.out.println("--after GetMethod " + statusCode);
					// System.out.println(redirect.getResponseBodyAsString());
					redirect.releaseConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
