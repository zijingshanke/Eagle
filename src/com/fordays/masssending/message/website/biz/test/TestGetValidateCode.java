package com.fordays.masssending.message.website.biz.test;

import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.commons.httpclient.HttpClient;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.FileUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.ImageFilter;
import com.fordays.masssending.httpclient.imagerecognize.ocr.ImageIOHelper;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCRUtil;
import com.fordays.masssending.httpclient.imagerecognize.ocr.OCResult;

public class TestGetValidateCode {
	public static void main(String[] args) {
		testGetValidateCode();
		// testRate();
	}

	/**
	 * 识别率测试
	 */
	public static void testRate() {
		for (int i = 0; i < 19; i++) {
			testGetValidateCode();
			try {
				Thread.sleep(new Long(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void testGetValidateCode() {
		try {
			int LOGIN_PORT = 80;
			String LOGIN_SITE = "192.168.1.88";
			String CHARSET = "utf-8";
			String url = "";
			String IMG_FORMAT = "";

			/**
			 * 识别率 60% 后期增加去除干扰素，可提高识别率
			 * 
			 * @二值化
			 */
			LOGIN_SITE = "passport.360buy.com";
			url = "http://passport.360buy.com/ImageVerifier.axd?uid=c360a45f-02b2-4255-8f2e-61191bfc3866";
			IMG_FORMAT = "jpg";

			// fdays b2b
			LOGIN_SITE = "b2b.fdays.com";
			url = "http://b2b.fdays.com/FSAdmin/Main/CheckCode.aspx";

			// qq充值中心(中文)
			LOGIN_SITE = "cverify.qq.com";
			url = "http://cverify.qq.com/getimage?0.8719126674481119";
			IMG_FORMAT = "PNG";

			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo

			HttpClient client = clientInfo.getHttpclient();

			File originalFile = FileUtil.saveImageFile(client, url, IMG_FORMAT);// 下载图片

			// String filepath = "E:\\tempValidateCode\\validatecode\\bmp7.bmp";
			// IMG_FORMAT = "bmp";
			// File originalFile = new File(filepath);

			System.out.println("originalFile:" + originalFile);

			File tiffFile = createTiffFileAsFilter(originalFile);//

			// OCResult ocresult = OCRUtil.recognizeValidation(imgFile);

			OCResult ocresult = OCRUtil.recognizeValidationAsFilter(
					originalFile, tiffFile);

			if (ocresult.isValidated()) {
				System.out.println("Code:" + ocresult.getResultString());
			} else {
				System.out.println("验证码识别失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File createTiffFileAsFilter(File originalFile) {
		BufferedImage buffImage = ImageIOHelper.getBufferImage(originalFile);

		// 图形过滤
		ImageFilter filter = new ImageFilter(buffImage);

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.changeGrey();// 二�值化

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.sharp();// 锐化（清晰度）

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.median();// 中值滤波

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.lineGrey();// 线性灰度变换

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.grayFilter();// 转为黑白灰度�

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.changeGrey();// 二�值化

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.scaling(new Double(2));// 平滑缩放

		// BufferedImage-->File(TIFF格式)
		File tiffFile = OCRUtil.createTIFFile(buffImage);

		return tiffFile;
	}

	public static void difficult() {
		String LOGIN_SITE = "";
		String url = "";

		// 珠海视窗 图片可下载，无法识别，原因不详
		LOGIN_SITE = "member.zhuhai.gd.cn";
		url = "http://member.zhuhai.gd.cn/ValidateImage.aspx";

		/**
		 * 识别率 很低 必须增加去除干扰素、噪点等特殊处理
		 */
		LOGIN_SITE = "passport.csdn.net";
		url = "http://passport.csdn.net/ShowExPwd.aspx?temp=g709oqvf";

		/**
		 * 百度贴吧 识别率很低，识别前须作特殊处理
		 */
		LOGIN_SITE = "passport.baidu.com";
		url = "http://passport.baidu.com/?verifypic&t=1269081185143";

		// 上海热线论坛 识别率低，需要去背景
		LOGIN_SITE = "sso.online.sh.cn";
		url = "http://sso.online.sh.cn/passport/action/IdentifyCode?f=286980";

		// 凯迪社区，图片太小，需要放大
		LOGIN_SITE = "club.kdnet.net";
		url = "http://club.kdnet.net/newbbs/DV_getcode.asp";

		// fdays b2c
		LOGIN_SITE = "person.fdays.com";
		url = "http://person.fdays.com/ValidateCode.aspx";

		LOGIN_SITE = "www.qq.com";
		url = "http://ptlogin2.qq.com/getimage?aid=5001501&0.09261594615824364";

		// 和讯博客(识别率低15%)
		LOGIN_SITE = "www.hexun.com";
		url = "http://comment.blog.hexun.com/inc/vcodepic.aspx?articleid=48486649&pp=13763244";
		// IMG_FORMAT="jpg";

	}

	/**
	 * 直接识别，无图形过滤示例
	 */
	public static void testDirect() {

	}

	public static void success() {
		int LOGIN_PORT = 80;
		String LOGIN_SITE = "192.168.1.88";

		/**
		 * 钱门后台类型（ 图片URL需到有验证码的页面抓取）
		 * 
		 * @识别率 90%以上
		 */
		LOGIN_PORT = 8080;
		String url = "http://192.168.1.88:8080/rencaiduo/servlet/com.neza.base.NumberImage;jsessionid=90589501347FD3F2C1E099F377415769";
		String IMG_FORMAT = "jpg";

		// 上海热线论坛 90%
		LOGIN_SITE = "sso.online.sh.cn";
		url = "http://sso.online.sh.cn/passport/action/IdentifyCode?f=286980";
	}

}
