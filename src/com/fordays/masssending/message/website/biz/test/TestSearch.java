package com.fordays.masssending.message.website.biz.test;

import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import com.fordays.masssending.base.htmlparser.LiTag;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;

public class TestSearch {
	public static void main(String[] args) {
		int LOGIN_PORT = 80;
		String LOGIN_SITE = "www.360buy.com";
		String CHARSET = "gb2312";

		try {
			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
			HttpClient client = clientInfo.getHttpclient();

			String keyword = "nikon L110";
			keyword = URLEncoder.encode(keyword, CHARSET);

			String geturl = "http://search.360buy.com/Search?keyword="
					+ keyword;

			System.out.println("geturl:" + geturl);
			GetMethod get = new GetMethod(geturl);
			get.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GB2312");

			int statusCode = client.executeMethod(get);
			System.out.println("login status:" + statusCode);

			if (statusCode == 200) {
				String htmlcontent = HttpClientUtil.printResponseHtml(get,
						false, false);
				Parser parser = Parser.createParser(htmlcontent, "gb2312");

				NodeFilter liTagfilter = new TagNameFilter("li");

				NodeList nodeList = parser
						.extractAllNodesThatMatch(liTagfilter);
				String nodeHtml = nodeList.toHtml();
				System.out.println("--nodeHtml--:" + "\n" + nodeHtml);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
