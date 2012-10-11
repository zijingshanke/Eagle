package com.fordays.masssending.message.website.biz;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
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
 * 新华网论坛 http://www.xinhuanet.com 发帖接口实现类
 */
public class XinHuaNetMessageBizImp extends MessageUtil implements
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
					clientInfo = redirectPostHtml(clientInfo, messageInfo);
					if (clientInfo.isSuccess()) {
						// messageInfo = publishMessage(clientInfo,
						// messageInfo);
						// return messageInfo;
					}
				}
			}
			// messageInfo = setErrorInfo(clientInfo, messageInfo);
			messageInfo = null;// 测试通过删除此行
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常");
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

			System.out.println("after publishMessage:" + statusCode);

			if (302 == statusCode) {
				messageInfo.setSendedStatus(new Long(2));
			} else {
				messageInfo.setSendedStatus(new Long(3));
				messageInfo.setRemark("执行发帖后,statusCode:" + statusCode
						+ ",发帖失败");
				HttpClientUtil.printResponseHtml(post, true, false);
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
		// Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		try {
			GetMethod redirect = new GetMethod(messageInfo.getNewTopicUrl());
			redirect.setRequestHeader("Cookie", clientInfo.getCookies()
					.toString());

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);

			System.out.println("redirectPostHtml statusCode:" + statusCode);
			if (200 == statusCode) {

				String htmlcontent = printResponseHtml(redirect, true, false);

				// clientInfo.setHiddenHtmlValue(hiddenValue);
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

	/**
	 * 登录论坛
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
			throws AppException {

		HttpClient client = clientInfo.getHttpclient();
		String userNameStr = clientInfo.getLOGIN_NAME();
		String passwordStr = clientInfo.getLOGIN_PASSWORD();

		try {
			// 模拟登录
			PostMethod post = new PostMethod("/embed_login.jsp");

			NameValuePair targeturl = new NameValuePair("targeturl",
					"http://forum.home.news.cn/listthread/50/1.htm");
			NameValuePair userName = new NameValuePair("userName", userNameStr);
			NameValuePair password = new NameValuePair("password", passwordStr);

			post.setRequestBody(new NameValuePair[] { targeturl, userName,
					password });

			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;

			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);

			if (302 == statusCode) {
				clientInfo = HttpClientUtil.autoRedirectASNoMatch(statusCode,
						clientInfo);
				// 获取并保存登录Cookie
				// clientInfo = HttpClientUtil
				// .saveCurrentCookieASNoMatch(clientInfo);

				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("登录失败,username:" + userName
						+ "=password:" + password);
				System.out.println(post.getResponseBodyAsString());
			}
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常");
		}
		return clientInfo;
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
