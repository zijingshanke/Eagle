package com.fordays.masssending.message.website.biz;

import java.io.IOException;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.exception.AppException;

/**
 * www.javaeye.com发帖接口实现类
 */
public class JavaEyeMessageBizImp implements MessageUtilBiz {

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
		clientInfo.setRedirectNewUrl(messageInfo.getNewTopicUrl());
		clientInfo.setTopicType(messageInfo.getTopic());
		clientInfo.setTopicValueMethod("2");

		try {
			clientInfo = MessageUtil.redirectAndSaveInfo(clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientInfo;
	}

	/**
	 * javaeye论坛发帖
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
	 * 登录JavaEye
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {

		HttpClient client = clientInfo.getHttpclient();
		// 模拟登录
		PostMethod post = new PostMethod("/login");
		NameValuePair name = new NameValuePair("name", clientInfo
				.getLOGIN_NAME());
		NameValuePair pass = new NameValuePair("password", clientInfo
				.getLOGIN_PASSWORD());
		NameValuePair remember_me = new NameValuePair("remember_me", "1");
		post.setRequestBody(new NameValuePair[] { name, pass, remember_me });

		clientInfo.setPostMethod(post);// 保存PostMethod

		int statusCode = 0;
		try {
			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);

			// 获取并保存登录Cookie
			clientInfo = HttpClientUtil.saveCurrentCookie(clientInfo);
			autoRedirect(statusCode, post, client, clientInfo.getCookies());
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

	public HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
