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
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCRUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCResult;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.exception.AppException;

/**
 * www.javaeye.com发帖接口实现类
 */
public class ZhuHaiShiChuangMessageBizImp implements MessageUtilBiz {
	public static void main(String[] args) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setLoginSite("member.zhuhai.gd.cn");
		messageInfo.setLoginPort(80);
		messageInfo.setLoginName("qmpaytest");
		messageInfo.setLoginPassword("111111");

		MessageUtilBiz util = new ZhuHaiShiChuangMessageBizImp();
		try {
			util.publishMessage(messageInfo);
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

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

			// clientInfo = redirectPostHtml(clientInfo, messageInfo);

			// messageInfo = publishMessage(clientInfo, messageInfo);
		} catch (Exception e) {
			e.printStackTrace();
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

		PostMethod post = new PostMethod("/login.aspx");
		NameValuePair VIEWSTATE = new NameValuePair(
				"_VIEWSTATE",
				"dDwxNDEwNDg2NTcwO3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8Mj47aTwxMj47aTwxMz47aTwxNj47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47PjtsPHQ8QDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLztodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vOz47Oz47dDw7bDxpPDE+Oz47bDx0PHA8bDxfIUl0ZW1Db3VudDs+O2w8aTwyPjs+PjtsPGk8MD47aTwxPjtpPDI+O2k8Mz47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAPFw8U0NSSVBUIExBTkdVQUdFPSdKYXZhU2NyaXB0J1w+Oz47Oz47Pj47dDw7bDxpPDA+Oz47bDx0PEA8MDtodHRwOi8vbWouemh1aGFpLmdkLmNuLzvnj6Dmtbfop4bnqpflrrblsYXpopHpgZPlhajmlrDmlLnniYjkuIrnur/vvIE7Pjs7Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8QDwxO2h0dHA6Ly93d3cuemh1aGFpLmdkLmNuL2hvbWUvYWEvd2xhbi8754+g5rW35peg57q/5a695bimV2lGaeeDreeCuea4heWNlTs+Ozs+Oz4+O3Q8O2w8aTwwPjs+O2w8dDxAPFw8L1NDUklQVFw+Oz47Oz47Pj47Pj47Pj47dDxAPGh0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLzs+Ozs+Oz4+O3Q8QDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87Pjs7Pjt0PHA8cDxsPEltYWdlVXJsOz47bDxodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi9pbWFnZXMvbG9naW5fNi5naWY7Pj47Pjs7Pjt0PEA8aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vOz47Oz47dDw7bDxpPDA+Oz47bDx0PEA8aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLztodHRwOi8vbWVtYmVyLnpodWhhaS5nZC5jbi87aHR0cDovL21lbWJlci56aHVoYWkuZ2QuY24vO2h0dHA6Ly9tZW1iZXIuemh1aGFpLmdkLmNuLzs+Ozs+Oz4+Oz4+Oz4+O2w8YnRuX0xvZ2luOz4+");
		NameValuePair txt_Account = new NameValuePair("txt_Account",
				"zijingshanke");
		NameValuePair txt_Password = new NameValuePair("txt_Password", "598719");

		try {
			// 取验证码
			String validateCode = getValidateCode(client);
			System.out.println("validateCode:" + validateCode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		NameValuePair txt_ValidateCode = new NameValuePair("txt_ValidateCode",
				"1");

		post.setRequestBody(new NameValuePair[] { VIEWSTATE, txt_Account,
				txt_Password, txt_ValidateCode });

		clientInfo.setPostMethod(post);// 保存PostMethod

		int statusCode = 0;
		try {
			// statusCode = client.executeMethod(post);
			System.out.println("after execute login method:" + statusCode);
			System.out.println(post.getResponseBodyAsString());

			// 获取并保存登录Cookie
			// clientInfo = HttpClientUtil.saveCurrentCookie(clientInfo);
			// autoRedirect(statusCode, post, client, clientInfo.getCookies());
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

	public static String getValidateCode(HttpClient client) throws AppException {
		String pathType = "http";
		String imgurl = "http://member.zhuhai.gd.cn/ValidateImage.aspx";
		String imgtype = "png";
		// imgurl =
		// "http://localhost/mass-sending/servlet/com.neza.base.NumberImage";
		// imgtype = "bmp";
		imgurl = "http://passport.360buy.com/ImageVerifier.axd?uid=c360a45f-02b2-4255-8f2e-61191bfc3866";
		imgtype = "jpg";

		// try {
		// OCResult ocresult = OCRUtil.recognizeValidation(client, "http",
		// imgurl, imgtype);
		//
		// if (ocresult.isValidated()) {
		// System.out.println(ocresult.getResultString());
		// } else {
		// System.out.println(ocresult.getValidateInfo());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		//		}
		return "";
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
