package com.fordays.masssending.httpclient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.Element;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.util.NodeList;
import com.fordays.masssending.httpclient.imagerecognize.ValidateCode;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * 基于apache httpclient的浏览器模拟工具
 * 
 * @author YanRui
 */

public class HttpClientUtil {
	public static void main(String[] args) {

	}

	/**
	 * 获取图形验证码
	 * 
	 */
	public static String getValidateCode(HttpClient client, String url)
			throws Exception {
		String validateCode = "";
		GetMethod method = new GetMethod(url);
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusLine());
		} else {
			InputStream inStream = method.getResponseBodyAsStream();
			BufferedImage iag = ImageIO.read(inStream);
			ValidateCode imgIdent = new ValidateCode(iag);

			imgIdent.saveJPEG(iag, "D:/ddd.jpg");
			String validate = imgIdent.getValidatecode(4);
			System.out.println("validate:" + validate);
			validateCode = validate;
		}

		return validateCode;
	}

	/**
	 * 初始化HttpClientInfo
	 */
	public static HttpClientInfo initHttpClientInfo(String LOGIN_SITE,
			int LOGIN_PORT, String CHARSET) throws Exception {
		HttpClientInfo clientInfo = new HttpClientInfo();
		clientInfo.setLOGIN_SITE(LOGIN_SITE);
		clientInfo.setLOGIN_PORT(LOGIN_PORT);
		clientInfo.setCharset(CHARSET);

		// 初始化HttpClient
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(LOGIN_SITE, LOGIN_PORT);

		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				CHARSET);
		client.getParams().setSoTimeout(30 * 1000);

		clientInfo.setHttpclient(client);
		return clientInfo;
	}

	/**
	 * 设置HttpClient
	 */
	public static HttpClient setHttpClient(String LOGIN_SITE, int LOGIN_PORT,
			String charset) throws Exception {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(LOGIN_SITE, LOGIN_PORT);

		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				charset);
		return client;
	}

	/**
	 * 保存Cookie
	 */
	public static HttpClientInfo saveCurrentCookie(HttpClientInfo clientInfo)
			throws Exception {
		Cookie[] cookies = getCurrentCookie(clientInfo);
		clientInfo.setCookies(cookies);
		return clientInfo;
	}

	/**
	 * 保存Cookie
	 */
	public static HttpClientInfo saveCurrentCookieASNoMatch(
			HttpClientInfo clientInfo) throws Exception {
		HttpClient client = clientInfo.getHttpclient();
		Cookie[] cookies = getCurrentCookieASNoMatch(client);
		clientInfo.setCookies(cookies);
		return clientInfo;
	}

	/**
	 * 获取Cookie
	 */
	public static Cookie[] getCurrentCookie(HttpClientInfo clientInfo)
			throws Exception {
		String LOGIN_SITE = clientInfo.getLOGIN_SITE();
		int LOGIN_PORT = clientInfo.getLOGIN_PORT();
		HttpClient client = clientInfo.getHttpclient();
		return getCurrentCookie(LOGIN_SITE, LOGIN_PORT, client);
	}

	/**
	 * 获取Cookie
	 */
	public static Cookie[] getCurrentCookie(String LOGIN_SITE, int LOGIN_PORT,
			HttpClient client) throws Exception {
		// 查看 cookie 信息
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match(LOGIN_SITE, LOGIN_PORT, "/", false,
				client.getState().getCookies());

		if (cookies.length == 0) {
			System.out.println("========Cookie is None===========");
		} else {
			System.out.println("========cookie info===========");
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].toString());
			}
			System.out.println("========end cookie info========");
		}
		return cookies;
	}

	/**
	 * 直接获取Cookie,无需CookieSpec match
	 */
	public static Cookie[] getCurrentCookieASNoMatch(HttpClient client)
			throws Exception {
		Cookie[] cookies = client.getState().getCookies();

		if (cookies.length == 0) {
			System.out.println("========Cookie is None===========");
		} else {
			System.out.println("========cookie info===========");
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].toString());
			}
			System.out.println("========end cookie info========");
		}
		return cookies;
	}

	/**
	 * 处理网站自动跳转
	 * 
	 * @param int
	 *            statusCode
	 * @param HttpClientInfo
	 *            clientInfo
	 * @return HttpClientInfo
	 */
	public static HttpClientInfo autoRedirect(int statusCode,
			HttpClientInfo clientInfo) throws AppException {
		try {
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = clientInfo.getPostMethod()
						.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("页面自动跳转到:" + "\n" + location);

					HttpClient client = clientInfo.getHttpclient();

					// 获取Cookie
					Cookie[] cookies = HttpClientUtil
							.getCurrentCookie(clientInfo);
					// 保存登录Cookie
					clientInfo.setCookies(cookies);

					GetMethod redirect = new GetMethod(location);
					redirect.setRequestHeader("Cookie", cookies.toString());

					statusCode = client.executeMethod(redirect);
					System.out.println("自动跳转后,statusCode:" + statusCode);

					HttpClientUtil.printResponseHtml(redirect, true, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	/**
	 * 处理网站自动跳转,取Cookie时No Match
	 * 
	 * @param int
	 *            statusCode
	 * @param HttpClientInfo
	 *            clientInfo
	 * @return HttpClientInfo
	 */
	public static HttpClientInfo autoRedirectASNoMatch(int statusCode,
			HttpClientInfo clientInfo) throws AppException {
		try {
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = clientInfo.getPostMethod()
						.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("页面自动跳转到:" + "\n" + location);

					HttpClient client = clientInfo.getHttpclient();

					// 获取Cookie
					Cookie[] cookies = HttpClientUtil
							.getCurrentCookieASNoMatch(client);
					// 保存登录Cookie
					clientInfo.setCookies(cookies);

					GetMethod redirect = new GetMethod(location);
					redirect.setRequestHeader("Connection ", "Keep-Alive");
					redirect.setRequestHeader("Cookie", cookies.toString());

					statusCode = client.executeMethod(redirect);

					System.out.println("redirect==== :" + statusCode);
					printResponseHtml(redirect, true, false);// ------------
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}

	/**
	 * 填写类别名称，到发帖页面取optiond的value值
	 * 
	 * @应用:JavaEye
	 */
	public static String getTopicValueByHtmlContent(HttpClientInfo clientInfo)
			throws Exception {
		String value = "";

		String htmlcontent = clientInfo.getResponseBodyAsString();
		String charset = clientInfo.getCharset();
		String optionText = clientInfo.getTopicType();

		Parser parser = Parser.createParser(htmlcontent, charset);

		NodeFilter selectTagfilter = new NodeClassFilter(SelectTag.class);

		NodeList nodeList = parser.extractAllNodesThatMatch(selectTagfilter);

		// System.out.println(nodeList.toHtml());
		// 得到符合条件的Tag内容
		// for (int i = 0; i <= nodeList.size(); i++) {
		// if (nodeList.elementAt(i) instanceof SelectTag) {
		// SelectTag tag = (SelectTag) nodeList.elementAt(i);
		// NodeList optionList = tag.getChildren();
		//
		// System.out.println(optionList.toHtml());
		// // for (int j = 0; j < optionList.size(); j++) {
		// // // 报异常：原因未知
		// // // java.lang.ClassCastException:
		// // // org.htmlparser.nodes.TextNode
		// // OptionTag optionTag = (OptionTag) optionList.elementAt(j);
		// // }
		// }
		// }

		// 替代上面的方�法
		value = getOptionValueByText(nodeList.toHtml(), optionText);
		// clientInfo.setTopicTypeValue(value);

		// System.out.println("topicValue:" + value);
		return value;
	}

	public static String getOptionValueByText(Parser parser,
			String htmlcontent, String charset, String optionText)
			throws Exception {
		NodeFilter selectTagfilter = new NodeClassFilter(SelectTag.class);
		NodeList nodeList = parser.extractAllNodesThatMatch(selectTagfilter);
		return getOptionValueByText(nodeList.toHtml(), optionText);
	}

	public static String getFormActionValueByHtml(Parser parser)
			throws Exception {
		NodeFilter formTagfilter = new NodeClassFilter(FormTag.class);
		NodeList nodeList = parser.extractAllNodesThatMatch(formTagfilter);

		System.out.println("nodeList:" + "\n" + nodeList.toHtml());

		return "";
	}

	public static String getOptionValueByText(String content, String selectText)
			throws Exception {
		String value = "1";

		// content = checkContentXml(content);

		XmlUtil xml = new XmlUtil();
		Document doc = xml.readResult(new StringBuffer(content));
		List nodes = doc.selectNodes("//select/option");
		Iterator it = nodes.iterator();
		while (it.hasNext()) {
			Element elm = (Element) it.next();
			if (selectText.equals(elm.getText())) {
				value = elm.attributeValue("value");
				System.out.println(elm.getText() + "--value=" + value);
			}
		}
		return value;
	}

	public static String checkContentXml(String content) {
		System.out.println("before checkContentXml" + "\n" + content);

		content = content.replaceAll("Selected", "");//

		content = getStringAsBetween("<select", ">", content);

		content = getStringList(content);

		System.out.println("after checkContentXml" + "\n" + content);
		return "";
	}

	public static String getStringAsBetween(String beforeStr, String endStr,
			String content) {
		String findStr = "";
		Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");// 匹配<title>�头，</title>结尾的文�
		Matcher m = p.matcher(content);// 开始编译
		while (m.find()) {
			findStr = m.group(1);// 获取被匹配的部分
			System.out.println(findStr);
		}
		return findStr;
	}

	public static String getStringList(String content) {
		List strList = new ArrayList();

		String beforeStr = " ";
		String endStr = beforeStr;

		Pattern p = Pattern.compile(beforeStr + "([^" + endStr + "]*)");// 匹配<title>�头，</title>结尾的文�
		Matcher m = p.matcher(content);// 开始编译
		while (m.find()) {
			String findStr = m.group(1);// 获取被匹配的部分
			System.out.println(findStr);
			strList.add(findStr);
		}

		System.out.println("---string list---");
		for (int i = 0; i < strList.size(); i++) {
			String nameValue = strList.get(i).toString();
			nameValue = nameValue.replace("=", "=" + "'");

		}
		return "";
	}

	/**
	 * 打印PostMethod 反馈信息
	 * 
	 * @param PostMethod
	 *            post
	 * @param String
	 *            charset
	 * @param boolean
	 *            是否在控制台打印输出 isSystemOut
	 * @param boolean
	 *            是否将内容打印到文件 isPrintToTxt
	 */
	public static String printResponseHtml(PostMethod post,
			boolean isSystemOut, boolean isPrint) throws AppException,
			Exception {
		System.out.println("======post response====");

		String charset = post.getResponseCharSet();

		System.out.println("responseCharSet:" + charset);
		InputStream in = post.getResponseBodyAsStream();
		return getStringAsInputStream(in, charset, isSystemOut, isPrint);
	}

	/**
	 * 指定编码打印
	 */
	public static String printResponseHtml(PostMethod post, String charset,
			boolean isSystemOut, boolean isPrint) throws AppException,
			Exception {
		System.out.println("======post response====");
		System.out.println("responseCharSet:" + post.getResponseCharSet());
		System.out.println("指定编码:" + charset);

		InputStream in = post.getResponseBodyAsStream();
		return getStringAsInputStream(in, charset, isSystemOut, isPrint);
	}

	/**
	 * 打印GetMethod 反馈信息
	 * 
	 * @param PostMethod
	 *            post
	 * @param String
	 *            charset
	 * @param boolean
	 *            是否在控制台打印输出 isSystemOut
	 * @param boolean
	 *            是否将内容打印到文件 isPrintToTxt
	 */
	public static String printResponseHtml(GetMethod get, boolean isSystemOut,
			boolean isPrint) throws AppException, Exception {
		System.out.println("======get response====");
		String charset = get.getResponseCharSet();
		System.out.println("responseCharSet:" + charset);
		InputStream in = get.getResponseBodyAsStream();
		return getStringAsInputStream(in, charset, isSystemOut, isPrint);
	}

	/**
	 * 指定编码打印
	 */
	public static String printResponseHtml(GetMethod get, String charset,
			boolean isSystemOut, boolean isPrint) throws AppException,
			Exception {
		System.out.println("======get response====");
		System.out.println("responseCharSet:" + get.getResponseCharSet());
		System.out.println("指定编码:" + charset);
		InputStream in = get.getResponseBodyAsStream();
		return getStringAsInputStream(in, charset, isSystemOut, isPrint);
	}

	public static String getStringAsInputStream(InputStream inputSteam,
			String charset, boolean isSystemOut, boolean isPrint)
			throws AppException, Exception {
		String html = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputSteam, charset));
		StringBuffer sb = new StringBuffer();
		int chari;
		while ((chari = in.read()) != -1) {
			sb.append((char) chari);
		}
		html = sb.toString();

		if (isSystemOut) {
			System.out.println(html);
		}

		if (isPrint) {
			printStringToTxt(html);
		}

		in.close();
		System.out.println("======end response====");
		return html;
	}

	public static void printStringToTxt(String content) {
		try {
			FileWriter fw = new FileWriter("F:\\testInfo.html", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
