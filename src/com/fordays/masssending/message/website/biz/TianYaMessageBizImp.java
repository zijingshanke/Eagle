package com.fordays.masssending.message.website.biz;

import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.fordays.masssending.base.util.StringUtil;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.MessageInfo;
import com.fordays.masssending.message.MessageUtil;
import com.fordays.masssending.message.biz.MessageUtilBiz;
import com.neza.exception.AppException;

/**
 * 天涯论坛 http://bbs.rayli.com.cn 发帖接口实现类
 * 
 * 
 * 
 */
public class TianYaMessageBizImp extends MessageUtil implements MessageUtilBiz {

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
				// clientInfo = loginWebSite(clientInfo);
				// if (clientInfo.isSuccess()) {
				clientInfo = redirectReplyHtml(clientInfo, messageInfo);
				if (clientInfo.isSuccess()) {
					messageInfo = publishReplyMessage(clientInfo, messageInfo);
					return messageInfo;
				}
				// }
			}
			messageInfo = setErrorInfo(clientInfo, messageInfo);
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
				clientInfo = publishMessage_strItem(clientInfo, messageInfo);
			} else if ("2".equals(publishMethod)) {
				clientInfo = publishMessage_iditem(clientInfo, messageInfo);
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
	 * 发帖方法iditem
	 */
	public static HttpClientInfo publishMessage_iditem(
			HttpClientInfo clientInfo, MessageInfo messageInfo)
			throws Exception {
		try {
			Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

			String idItemValue = hiddenValue.get("iditem");
			String idwriter = hiddenValue.get("idWriter");
			String Key = hiddenValue.get("Key");
			String topicValue = messageInfo.getTopic();

			// System.out.println("---iditem topicvalue:" + topicValue);
			// System.out.println(messageInfo.getTitle());

			String posturl = "";
			if ("692".equals(idItemValue)) {
				posturl = "http://post.tianya.cn/new/techforum/compose_hotelsubmit.asp?idwriter="
						+ idwriter + "&key=" + Key + "&ttitem=" + idItemValue;
			} else {
				posturl = "http://post.tianya.cn/new/techforum/compose_submit.asp?idwriter="
						+ idwriter + "&key=" + Key + "&ttitem=" + idItemValue;
			}

			System.out.println(posturl);

			PostMethod post = new PostMethod(posturl);
			post.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			NameValuePair ckey = new NameValuePair("ckey", Key);// 页面取
			NameValuePair cidWriter = new NameValuePair("cidWriter", idwriter);// 页面取
			NameValuePair idItem = new NameValuePair("idItem", idItemValue);
			NameValuePair idSign = new NameValuePair("idSign", "1");
			NameValuePair Submit = new NameValuePair("Submit", "Response");
			NameValuePair strTitle = new NameValuePair("strTitle", messageInfo
					.getTitle());
			NameValuePair strSubItem = new NameValuePair("strSubItem",
					topicValue);
			NameValuePair strContent = new NameValuePair("strContent",
					messageInfo.getContent());
			NameValuePair PicDesc = new NameValuePair("PicDesc", "");
			NameValuePair strAlbumPicURL = new NameValuePair("strAlbumPicURL",
					"http://");
			NameValuePair strFlashURL = new NameValuePair("strFlashURL", "");
			NameValuePair IntFlashWidth = new NameValuePair("IntFlashWidth",
					"400");
			NameValuePair IntFlashHeight = new NameValuePair("IntFlashHeight",
					"300");
			NameValuePair idItem_hidden = new NameValuePair("idItem_hidden",
					idItemValue);
			NameValuePair strKind = new NameValuePair("strKind", "(原创)");// (转载)

			post.setRequestBody(new NameValuePair[] { ckey, cidWriter, idItem,
					idSign, Submit, strTitle, strSubItem, strContent, PicDesc,
					strAlbumPicURL, strFlashURL, IntFlashWidth, IntFlashHeight,
					idItem_hidden, strKind });
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
		try {
			String newTopicUrl = messageInfo.getNewTopicUrl();
			GetMethod redirect = new GetMethod(newTopicUrl);
			redirect.setRequestHeader("Cookie", clientInfo.getCookies()
					.toString());

			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);

			if (200 == statusCode) {
				clientInfo = choosePublishMethod(newTopicUrl, clientInfo);//

				if (clientInfo.isSuccess()) {
					Map<String, String> hiddenValue = clientInfo
							.getHiddenHtmlValue();
					String publishMethod = hiddenValue.get("publishMethod");

					if ("1".equals(publishMethod)) {
						clientInfo = getStrItemByUrl(newTopicUrl, clientInfo);// ------

						if (clientInfo.isSuccess() == false) {
							return clientInfo;
						}
					} else if ("2".equals(publishMethod)) {
						clientInfo = getIdItemByUrl(newTopicUrl, clientInfo);

						if (clientInfo.isSuccess()) {
							// String htmlcontent = HttpClientUtil
							// .printResponseHtml(redirect, "gb2312",
							// false, true);

							// hiddenValue = setHiddenInputValue_idItem(
							// htmlcontent, hiddenValue);
							// clientInfo.setHiddenHtmlValue(hiddenValue);

						} else {
							return clientInfo;
						}
					} else {
						clientInfo.setSuccess(false);
						clientInfo.setClientRemark("非法的发帖方法");
						return clientInfo;
					}
					clientInfo.setSuccess(true);
				} else {
					return clientInfo;
				}
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
	 * 根据发帖页面路径,判断使用何种发帖方法
	 * 
	 */
	public static HttpClientInfo choosePublishMethod(String newTopicUrl,
			HttpClientInfo clientInfo) throws AppException {
		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();

		if (newTopicUrl != null) {
			int strItemIndex = newTopicUrl.lastIndexOf("strItem=");
			if (strItemIndex == -1) {
				int iditemIndex = newTopicUrl.lastIndexOf("iditem=");
				if (iditemIndex == -1) {
					clientInfo.setSuccess(false);
					clientInfo.setClientRemark("发帖页面URL缺少参数");
				} else {
					hiddenValue.put("publishMethod", "2");

					clientInfo.setSuccess(true);
				}
			} else {
				hiddenValue.put("publishMethod", "1");
				clientInfo.setSuccess(true);
			}
		} else {
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("发帖页面URL为空");
		}
		clientInfo.setHiddenHtmlValue(hiddenValue);
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
		try {
			HttpClient client = clientInfo.getHttpclient();
			String loginName = clientInfo.getLOGIN_NAME();
			String password = clientInfo.getLOGIN_PASSWORD();

			// 模拟登录
			PostMethod postMethod = new PostMethod("/user/v3_loginsubmit.asp");
			NameValuePair action = new NameValuePair("action", "login_action");
			NameValuePair vwriter = new NameValuePair("vwriter", loginName);
			NameValuePair vpassword = new NameValuePair("vpassword", password);
			NameValuePair rmflag = new NameValuePair("rmflag", "1");
			postMethod.setRequestBody(new NameValuePair[] { action, vwriter,
					vpassword, rmflag });

			clientInfo.setPostMethod(postMethod);// 保存PostMethod

			int statusCode = client.executeMethod(postMethod);
			System.out.println("after login statusCode:" + statusCode);

			if (302 == statusCode) {
				// 从头中取出转向的地址
				Header locationHeader = postMethod
						.getResponseHeader("location");
				if (locationHeader != null) {
					String location = locationHeader.getValue();
					System.out.println("redirected to:" + "\n" + location);

					// 保存idWriter and Key value
					clientInfo = getIdWriterKeyByUrl(location, clientInfo);

					if (clientInfo.isSuccess()) {
						// 获取并保存登录Cookie
						Cookie[] cookies = HttpClientUtil.getCurrentCookie(
								clientInfo.getLOGIN_SITE(), clientInfo
										.getLOGIN_PORT(), client);
						clientInfo.setCookies(cookies);

						GetMethod redirect = new GetMethod(location);
						redirect.setRequestHeader("Cookie", cookies.toString());
						statusCode = client.executeMethod(redirect);
						System.out.println("--after GetMethod " + statusCode);

						if (200 == statusCode) {
							clientInfo.setSuccess(true);
						} else {
							clientInfo.setSuccess(false);
							clientInfo.setClientRemark("after execute log");
							HttpClientUtil.printResponseHtml(redirect, true,
									false);
						}
					} else {
						return clientInfo;
					}
				} else {
					clientInfo.setSuccess(false);
					clientInfo
							.setClientRemark("after execute login,Header is null");
				}
			} else {
				clientInfo.setSuccess(false);
				clientInfo.setClientRemark("登录论坛失败<br>"
						+ HttpClientUtil.printResponseHtml(postMethod, "gbk",
								false, false));
			}
			postMethod.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("登录论坛异常<br>" + e.getMessage());
		}
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
			clientInfo
					.setClientRemark("发帖页面URL取strItem异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 从发帖页面url取版块value(iditem)
	 */
	private static HttpClientInfo getIdItemByUrl(String location,
			HttpClientInfo clientInfo) {
		// String location =
		// "http://www.tianya.cn/new/techforum/Compose.asp?iditem=49";

		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		try {
			String beforeStr = "iditem=";
			String endStr = " ";
			Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");
			Matcher m = p.matcher(location);// 开始编译
			while (m.find()) {
				String findStr = m.group(1);// 获取被匹配的部分
				System.out.println("iditem:" + findStr);
				hiddenValue.put("iditem", findStr);
			}
			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("发帖页面URL取iditem异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 从自动跳转的Location url 中提取Key
	 */
	private static HttpClientInfo getIdWriterKeyByUrl(String location,
			HttpClientInfo clientInfo) {
		// location =
		// "http://login.hainan.net/hainan/user/v3_loginsubmit_2.asp?strTianyaLoginReturnURL=&strTianyaLoginFowardURL=&submitedpage=&idWriter=33728720&Key=858596924&strWriter=qmpaytest&sys_grade=&ManagerCategory=False&rmCode=78AEEBD810D590278303A88FB1CB4930&pageKey_trans_login=409CEC988C79EDF5E3915291C28F6405";

		Map<String, String> hiddenValue = clientInfo.getHiddenHtmlValue();
		try {
			int beginIndex = location.indexOf("submitedpage=&");
			int endIndex = location.indexOf("&strWriter");

			location = location.substring(beginIndex + 14, endIndex);
			System.out.println(location);

			Vector<String> keyValues = StringUtil
					.getVectorString(location, "&");

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
			clientInfo.setHiddenHtmlValue(hiddenValue);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("取idWriter和Key值异常<br>" + e.getMessage());
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
		return null;
	}
}
