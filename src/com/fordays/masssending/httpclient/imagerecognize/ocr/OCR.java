package com.fordays.masssending.httpclient.imagerecognize.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OCR {
	private final String LANG_OPTION = "-l";
	private final String EOL = System.getProperty("line.separator");
	private String tessPath = "F:/tesseract/tesseract.exe";
	
	public String recognizeTIFFText(File tiffFile) throws Exception {
		StringBuffer strB = new StringBuffer();

		List<String> cmd = new ArrayList<String>();

		// cmd.add(tessPath + "\\tesseract");
		cmd.add(tessPath);

		cmd.add("");
		cmd.add(tiffFile.getName());
		cmd.add(LANG_OPTION);
		cmd.add("eng");

		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(tiffFile.getParentFile());

		cmd.set(1, tiffFile.getName());

		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();

		int w = process.waitFor();
		System.out.println("exec tesseract Exit value = " + w);

		if (w == 0) {
			String outputFilePath = tiffFile.getAbsolutePath() + ".txt";
			InputStream inFile = new FileInputStream(outputFilePath);
			InputStreamReader inRead = new InputStreamReader(inFile, "UTF-8");
			BufferedReader in = new BufferedReader(inRead);

			String str;

			while ((str = in.readLine()) != null) {
				strB.append(str).append(EOL);
			}
			in.close();
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files. There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recognize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			tiffFile.delete();
			throw new RuntimeException(msg);
		}

		new File(tiffFile.getAbsolutePath() + ".txt").delete();

		return formatResult(strB.toString());
	}

	/**
	 * 格式化验证码识别结果
	 */
	private static String formatResult(String str) {
		if ("".equals(str) || str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c) || Character.isLetter(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
