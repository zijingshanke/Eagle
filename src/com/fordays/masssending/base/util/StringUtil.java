package com.fordays.masssending.base.util;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtil {

	/**
	 * 读取文本文件的内容
	 * 
	 * @param:String url
	 * @return String
	 */
	public static String readStrFromTxt(String url) {
		String str = "";
		try {
			char data[] = new char[6000];
			FileReader reader = new FileReader(url);

			int num = reader.read(data);
			System.out.println("num is--" + num);

			str = new String(data, 0, num);

			System.out.println("读取的字符是----");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("读取文件失败，原因是�?----------");
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * method 将字符串类型的日期转换为一个Date（java.sql.Date）
	 * 
	 * @param dateString
	 *            需要转换为Date的字符串
	 * @return dataTime Date
	 */
	public final static java.sql.Date string2Date(String dateString)
			throws java.lang.Exception {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);// util类型
		java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());// sql类型
		return dateTime;
	}
	
	/**
	 * 将字符串按分隔符转成Vector @ 支持 |
	 * @param String
	 *            strSrc
	 * @param String
	 *            strKen
	 * @return Vector<String>
	 */
	public static Vector<String> getVectorString(String strSrc, String strKen) {
		StringTokenizer toKen = new StringTokenizer(strSrc, strKen);
		Vector<String> vec = new Vector<String>();
		int i = 0;
		while (toKen.hasMoreElements()) {
			String value = (String) toKen.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			vec.add(i++, value);
		}
		for (int j = 0; j < vec.size(); j++) {
			System.out.println(j + "---" + vec.get(j));
		}
		return vec;
	}
}
