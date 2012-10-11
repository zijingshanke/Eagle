package com.fordays.masssending.message.website.biz;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
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
 * 金融界论坛 http://www.jrj.com.cn/ 发帖接口实现类
 */
public class JinRongjieMessageBizImp extends MessageUtil implements
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
			postMethodUrl = postMethodUrl.replace("post.jsp", "post_do.jsp");
			System.out.println("postMethodUrl:" + postMethodUrl);

			String titleValue = messageInfo.getTitle();
			String contentValue = messageInfo.getContent();

			PostMethod post = new PostMethod(postMethodUrl);

			NameValuePair articleid = new NameValuePair("articleid", "-1");
			NameValuePair isautocopy = new NameValuePair("isautocopy", "7");
			NameValuePair icon = new NameValuePair("icon", "-1");
			NameValuePair title = new NameValuePair("title", titleValue);
			NameValuePair content = new NameValuePair("content", contentValue);

			post.setRequestBody(new NameValuePair[] { articleid, isautocopy,
					icon, title, content });

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

		try {
			String redirectUrl = messageInfo.getNewTopicUrl();
			GetMethod redirect = new GetMethod(redirectUrl);
			redirect.setRequestHeader("Cookie", clientInfo.getCookies()
					.toString());

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);

			System.out.println("redirectPostHtml statusCode:" + statusCode);
			if (200 == statusCode) {
				printResponseHtml(redirect, true, false);
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

		try {
			String user_name_str = clientInfo.getLOGIN_NAME();
			String passwordStr = clientInfo.getLOGIN_PASSWORD();

			// 模拟登录
			PostMethod post = new PostMethod(
					"http://sso.jrj.com.cn/sso/ssologin");
			NameValuePair isVerifyCode = new NameValuePair("isVerifyCode",
					"false");
			NameValuePair ReturnURL = new NameValuePair("ReturnURL",
					"http://bbs.jrj.com.cn");
			NameValuePair SPID = new NameValuePair("SPID", "bbs");
			NameValuePair LoginID = new NameValuePair("LoginID", user_name_str);
			NameValuePair Passwd = new NameValuePair("Passwd", passwordStr);
			post.setRequestBody(new NameValuePair[] { isVerifyCode, ReturnURL,
					SPID, LoginID, Passwd });

			clientInfo.setPostMethod(post);// 保存PostMethod

			int statusCode = 0;

			statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);

			if (302 == statusCode) {
				client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
				
				// 获取并保存登录Cookie
				clientInfo = HttpClientUtil.autoRedirectASNoMatch(statusCode,
						clientInfo);

				// clientInfo = HttpClientUtil
				// .autoRedirect(statusCode, clientInfo);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("登录失败,username:" + LoginID
						+ "=password:" + Passwd);
				HttpClientUtil.printResponseHtml(post, false, true);
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
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		try {
			GetMethod redirect = new GetMethod("http://reg.163.com/login.jsp");

			clientInfo.getHttpclient().executeMethod(redirect);

			clientInfo.setStatusCode(redirect.getStatusCode());

			String htmlcontent = redirect.getResponseBodyAsString();

			Parser parser = Parser.createParser(htmlcontent, "utf-8");

			NodeFilter inputTagfilter = new NodeClassFilter(InputTag.class);
			NodeList nodeList = parser.extractAllNodesThatMatch(inputTagfilter);

			String nodeHtml = nodeList.toHtml();
			String beforeStr = "name=syscheckcode value=\"";
			String endStr = "\" />";
			Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
			Matcher m = p.matcher(nodeHtml);// 开始编译
			while (m.find()) {
				String findStr = m.group(1);// 获取被匹配的部分
				hiddenValue.put("syscheckcode", findStr);
			}

			if (hiddenValue.get("syscheckcode") != null) {
				clientInfo.setHiddenHtmlValue(hiddenValue);
				clientInfo.setSuccess(true);
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("抓取登录页面syscheckcode异常");
			}
			redirect.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面信息异常");
		}
		return clientInfo;
	}

	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
}
