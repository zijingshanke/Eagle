package com.fordays.masssending.httpclient.imagerecognize;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.*;
import javax.imageio.ImageIO;

/**
 * 登陆验证图片转换为数�?
 */
public class ValidateCode {

	// 数字字符比特�?
	private final long[][] NUMERIC = { { 512104545, 562436190 }, // '0'
			{ 148931080, 136348222 }, // '1'
			{ 511971394, 69273663 }, // '2'
			{ 511971406, 17045598 }, // '3'
			{ 35168914, 586948743 }, // '4'
			{ 1065486398, 17045598 }, // '5'
			{ 239208494, 830871646 }, // '6'
			{ 1065623684, 69239824 }, // '7'
			{ 512104542, 562436190 }, // '8'
			{ 512104547, 486805660 } // '9'
	};

	// 字框�?
	private int intCharHeight = 10;

	// 字框横向间隙
	private int intCharSpaceH = 5;

	// 字框纵向间隙
	private int intCharSpaceY = 1;

	// 字框�?
	private int intCharWidth = 5;
	private int IntImgHeight;
	private BufferedImage img;
	private int intBgColor;
	private int intCharColor;
	private int intImgWith;
	private int intMaxX;
	private int intMaxY;
	private int intMinX;
	private int intMinY;

	// 座标原点
	private Point pOrigin;
	private String strNum;

	/**
	 * @param img
	 * @throws IOException
	 */
	public ValidateCode(BufferedImage img) throws IOException {
		this.img = img;
		init();
	}

	/**
	 * @param file
	 *            本地文件
	 * @throws IOException
	 */
	public ValidateCode(File file) throws IOException {
		img = ImageIO.read(file);
		init();
	}

	/**
	 * @param url
	 *            远程文件
	 * @throws IOException
	 */
	public ValidateCode(URL url) throws IOException {
		img = ImageIO.read(url);
		init();
	}

	/**
	 * 类初始工�?
	 */
	private void init() {

		// 得到图象的长度和宽度
		intImgWith = img.getWidth();
		IntImgHeight = img.getHeight();

		// 得到图象的背景颜�?
		intBgColor = img.getRGB(7, 4);

		// System.out.println(intBgColor);

		// 初始化图象原点座�?
		pOrigin = new Point(0, 0);
	}

	/** */
	/**
	 * Method description
	 * 
	 */
	private void getBaseInfo() {
		System.out.println(intBgColor + "|" + intCharColor);
		System.out.println(intMinX + "|" + intMinY + "|" + intMaxX + "|"
				+ intMaxY);
	}

	/** */
	/**
	 * 得到字符的左上右下点座标
	 * 
	 * @param intNo
	 *            int 第n个字�?
	 * @return int[]
	 */
	private Point[] getCharRange(int intNo) {

		// 左上右下点座�?
		Point pTopLeft = new Point(0, 0);
		Point pBottomRight = new Point(0, 0);

		// 左上�?
		pTopLeft.x = pOrigin.x + intCharWidth * (intNo - 1) + intCharSpaceH
				* (intNo - 1);
		pTopLeft.y = pOrigin.y;

		// 右下�?
		pBottomRight.x = 1 + pOrigin.x + intCharWidth * intNo + intCharSpaceH
				* (intNo - 1) - 1;
		pBottomRight.y = pOrigin.y + intCharHeight - 1;

		return new Point[] { pTopLeft, pBottomRight };
	}

	/** */
	/**
	 * 与背景颜色比较返回相应的字符
	 * 
	 * @param x
	 *            int 横座�?
	 * @param y
	 *            int 纵座�?
	 * @return char 返回字符
	 */
	private char getBit(int x, int y) {
		int intCurtColor;

		intCurtColor = img.getRGB(x, y);

		// System.out.println("[" + x + "," + y + "]" + intCurtColor + "=="
		// + intBgColor + "==>" + (Math.abs(intCurtColor) >7308252));
		// return (Math.abs(intCurtColor) >= 5689325)
		// ? '0'
		// : '1';
		return (intCurtColor == intBgColor) ? '0' : '1';

		// 5689325 6008535
	}

	/** */
	/**
	 * 得到第n个字符对应的字符�?
	 * 
	 * @param intNo
	 *            int 第n个字�?
	 * @return String 代表字符位的�?
	 */
	private String getCharString(int intNo) {

		// 本字符的左上右下点座�?
		Point[] p = getCharRange(intNo);
		Point pTopLeft = p[0];
		Point pBottomRight = p[1];

		// 换算边界�?
		int intX1, intY1, intX2, intY2;

		intX1 = pTopLeft.x;
		intY1 = pTopLeft.y;
		intX2 = pBottomRight.x;
		intY2 = pBottomRight.y;

		// System.out.println("intX1=" + intX1);
		// System.out.println("intY1=" + intY1);
		// System.out.println("intX2=" + intX2);
		// System.out.println("intY2=" + intY2);

		// 在边界内循环取象�?
		int i, j;
		String strChar = "";

		for (i = intY1; i <= intY2; i++) {
			for (j = intX1; j <= intX2; j++) {
				System.out.print(getBit(j, i));
				strChar = strChar + getBit(j, i);
			}

			System.out.println();
		}

		System.out.println();

		return strChar;
	}

	/**
	 * 得到第n个字符对应数�?
	 * 
	 * @param intNo
	 *            int 第n个字�?
	 * @return int 对应数�??
	 */
	public int getNum(int intNo) {

		// 取得位字符串
		String strChar = getCharString(intNo);

		// System.out.println(intNo+"=="+strChar);
		// 取得串高位串和低位串
		String strCharHigh = strChar.substring(0, strChar.length() / 2);
		String strCharLow = strChar.substring(strChar.length() / 2);

		// 计算高位和低位�??
		long lCharHigh = Long.parseLong(strCharHigh, 2);

		System.out.println(lCharHigh);

		long lCharLow = Long.parseLong(strCharLow, 2);

		System.out.println(lCharLow);

		// 在数字中循环比较
		int intNum = '*';

		for (int i = 0; i <= 9; i++) {
			if ((lCharHigh == NUMERIC[i][0]) && (lCharLow == NUMERIC[i][1])) {
				intNum = i;

				break;
			} else {
				if ((lCharHigh == 834533329) && (lCharLow == 242870177)) {
					intNum = 6;
				} // 834533329 242870177
				else {
					intNum = 1;
				} // 默认�?1 低位�? 937393609 937393601
			}
		}

		return intNum;
	}

	/** */
	/**
	 * 保存图片
	 * 
	 * 
	 * @param length
	 * 
	 * @return
	 */
	public String getValidatecode(int length) {
		String strNum = "";

		for (int i = 1; i <= length; i++) {
			synchronized (this) {
				strNum += String.valueOf(getNum(i));
			}
		}
		return strNum;
	}

	/** */
	/**
	 * Method description
	 * 
	 * 
	 * @param iag
	 * @param savePath
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveJPEG(BufferedImage iag, String savePath)
			throws FileNotFoundException, IOException {
		OutputStream jos = new FileOutputStream(savePath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(jos);
		JPEGEncodeParam jpegEP = JPEGCodec.getDefaultJPEGEncodeParam(iag);

		jpegEP.setQuality((float) 1, true);
		encoder.encode(iag, jpegEP);
		jos.flush();
		jos.close();
	}
}
