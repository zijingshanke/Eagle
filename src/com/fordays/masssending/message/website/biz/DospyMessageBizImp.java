package com.fordays.masssending.message.website.biz;

import java.util.Map;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
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
 * 塞班智能手机网论坛 http://bbs.dospy.com 发帖接口实现类
 * 
 */
public class DospyMessageBizImp extends MessageUtil implements MessageUtilBiz {

	/**
	 * 论坛回帖
	 */
	public MessageInfo replyMessage(MessageInfo messageInfo)
			throws AppException {
		try {
			// 初始化HttpClientInfo
			HttpClientInfo clientInfo = MessageUtil
					.initHttpClientInfo(messageInfo);

			if (clientInfo.isSuccess()) {
				clientInfo = loginWebSite(clientInfo);
				// if (clientInfo.isSuccess()) {
				// clientInfo = redirectReplyHtml(clientInfo, messageInfo);
				// if (clientInfo.isSuccess()) {
				// messageInfo = publishReplyMessage(clientInfo,
				// messageInfo);
				// return messageInfo;
				// }
				// }
			}
			// messageInfo = setErrorInfo(clientInfo, messageInfo);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常");
		}
		return messageInfo;
	}

	/**
	 * 论坛发主帖
	 */
	public MessageInfo publishMessage(MessageInfo messageInfo)
			throws AppException {
		try {
			// 初始化HttpClientInfo
			HttpClientInfo clientInfo = MessageUtil
					.initHttpClientInfo(messageInfo);

			if (clientInfo.isSuccess()) {
				clientInfo = getLoginHtmlInfo(clientInfo);
				if (clientInfo.isSuccess()) {
					clientInfo = loginWebSite(clientInfo);
					if (clientInfo.isSuccess()) {
						// clientInfo = redirectPostHtml(clientInfo,
						// messageInfo);
						// if (clientInfo.isSuccess()) {
						// messageInfo = publishMessage(clientInfo,
						// messageInfo);
						// return messageInfo;
						// }
					}
				}
			}
			// messageInfo = setErrorInfo(clientInfo, messageInfo);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setRemark("不可预期的异常<br>" + e.getMessage());
		}
		return messageInfo;
	}

	/**
	 * 论坛发主帖实现方法
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
			String publishMethod = hiddenValue.get("publishMethod");
			PostMethod post = null;
			if ("1".equals(publishMethod)) {
				// clientInfo = publishMessage_strItem(clientInfo, messageInfo);
			} else if ("2".equals(publishMethod)) {
				// clientInfo = publishMessage_iditem(clientInfo, messageInfo);
			} else {
				messageInfo.setValidated(false);
				messageInfo
						.setValidateString("publishMethod invalid,after redirectPostHtml()");
				return messageInfo;
			}

			if (clientInfo.isSuccess()) {
				post = clientInfo.getPostMethod();
				int statusCode = clientInfo.getHttpclient().executeMethod(post);

				System.out.println("after publishMessage:" + statusCode);

				if (302 == statusCode) {
					messageInfo.setSendedStatus(new Long(2));
					Header header = post.getResponseHeader("location");
					if (header != null) {
						String location = header.getValue();

						System.out.println("publish success redirect location:"
								+ location);
					}
				} else {
					messageInfo.setSendedStatus(new Long(3));

					String remark = "执行发帖后,statusCode:" + statusCode + ",发帖失败"
							+ "<br>";
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
	 * 登录论坛
	 * 
	 * @param HttpClientInfo
	 *            clientInfo
	 */
	public HttpClientInfo loginWebSite(HttpClientInfo clientInfo)
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

			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");

			System.out.println("request charset:"
					+ postMethod.getRequestCharSet());
			NameValuePair action = new NameValuePair("action", "login");
			NameValuePair formhash = new NameValuePair("formhash", /* formhashStr */
					"5dcb0bfd");
			NameValuePair referer = new NameValuePair("referer", "");
			NameValuePair loginfield = new NameValuePair("loginfield",
					"username");
			NameValuePair username = new NameValuePair("username", loginName);
			NameValuePair password = new NameValuePair("password",
					loginPassword);
			NameValuePair questionid = new NameValuePair("questionid", "0");
			NameValuePair answer = new NameValuePair("answer", "");
			NameValuePair cookietime = new NameValuePair("cookietime",
					"2592000");
			NameValuePair loginmode = new NameValuePair("loginmode", "");

			NameValuePair styleid = new NameValuePair("styleid", "");

			postMethod.setRequestBody(new NameValuePair[] { action, formhash,
					referer, loginfield, username, password, questionid,
					answer, cookietime, loginmode, styleid });

			NameValuePair[] nvp = postMethod.getParameters();

			for (int i = 0; i < nvp.length; i++) {
				System.out.println(nvp[i]);
			}

			int statusCode = client.executeMethod(postMethod);
			System.out.println("after login statusCode:" + statusCode);

			HttpClientUtil.printResponseHtml(postMethod, "GBK", true, false);

			client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);

			Cookie[] cookies = HttpClientUtil.getCurrentCookieASNoMatch(client);
			clientInfo.setCookies(cookies);

			cookies = HttpClientUtil.getCurrentCookie(clientInfo);

			// if (302 == statusCode) {
			// // 从头中取出转向的地址
			// Header locationHeader = postMethod
			// .getResponseHeader("location");
			// if (locationHeader != null) {
			// String location = locationHeader.getValue();
			// System.out.println("redirected to:" + "\n" + location);
			//
			//				
			//
			// GetMethod redirect = new GetMethod(location);
			// redirect.setRequestHeader("Cookie", cookies.toString());
			// statusCode = client.executeMethod(redirect);
			// System.out.println("--after GetMethod " + statusCode);
			//
			// if (200 == statusCode) {
			clientInfo.setSuccess(true);
			// } else {
			// clientInfo.setSuccess(false);
			// clientInfo.setClientRemark("after execute log");
			// HttpClientUtil.printResponseHtml(redirect, true, false);
			// }
			// } else {
			// clientInfo.setSuccess(false);
			// clientInfo.setClientRemark("after execute login,Header is null");
			// }
			// } else {
			// clientInfo.setSuccess(false);
			// clientInfo.setClientRemark("登录论坛失败<br>"
			// + HttpClientUtil.printResponseHtml(postMethod, "gbk",
			// false, false));
			// }
			postMethod.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 定向到回帖页面（页面填写帐号密码回帖）
	 */
	public HttpClientInfo redirectReplyHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		try {
			String newTopicUrl = messageInfo.getNewTopicUrl();
			GetMethod redirect = new GetMethod(newTopicUrl);

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);

			if (200 == statusCode) {
				clientInfo = setHiddenInputValueAsReply(clientInfo);
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
	 * 回帖实现方法（直接填写帐号、密码）
	 */
	private MessageInfo publishReplyMessage(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		try {
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
			String messageContent = messageInfo.getContent();

			String action = "http://post.tianya.cn/new/techforum/content_hotelsubmit.asp?idwriter="
					+ idwriterStr + "&key=" + keyStr + "&ttitem=" + ttitemStr;
			PostMethod postMethod = new PostMethod(action);
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			NameValuePair idItem = new NameValuePair("idItem", idItemStr);
			NameValuePair strItem = new NameValuePair("strItem", strItemStr);
			NameValuePair chrAuthor = new NameValuePair("chrAuthor",
					chrAuthorStr);
			NameValuePair idArticle = new NameValuePair("idArticle",
					idArticleStr);
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

			if (302 == statusCode) {
				messageInfo.setValidated(true);
			} else {
				messageInfo.setValidated(false);
				messageInfo.setValidateString(HttpClientUtil.printResponseHtml(
						postMethod, "gbk", false, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageInfo.setValidated(false);
			messageInfo.setValidateString(e.getMessage());
		}
		return messageInfo;
	}

	/**
	 * 设置回帖页面隐藏域
	 */
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
					if ("ttitem".equals(name)) {
						hiddenValue.put("ttitem", value);
					}
					if ("pageAll".equals(name)) {
						hiddenValue.put("pageAll", value);
					}
					if ("apn".equals(name)) {
						hiddenValue.put("apn", value);
					}
				}
			}
			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取回帖页面隐藏域失败<br>" + e.getMessage());
		}
		return clientInfo;
	}

	public HttpClientInfo getLoginHtmlInfo(HttpClientInfo clientInfo)
			throws AppException {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		try {

			GetMethod redirect = new GetMethod(
					"http://bbs.dospy.com/logging.php?action=login");

			clientInfo.getHttpclient().executeMethod(redirect);
			clientInfo.setStatusCode(redirect.getStatusCode());

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
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("抓取登录页面隐藏域失败<br>" + e.getMessage());
		}
		return clientInfo;
	}

	public HttpClientInfo redirectPostHtml(HttpClientInfo clientInfo,
			MessageInfo messageInfo) throws AppException {
		try {
			String newTopicUrl = messageInfo.getNewTopicUrl();
			GetMethod redirect = new GetMethod(newTopicUrl);
			redirect.setRequestHeader("Cookie", clientInfo.getCookies()
					.toString());
			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);

			System.out.println("redirectPostHtml statusCode:" + statusCode);

			HttpClientUtil.printResponseHtml(redirect, "GBK", true, false);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
