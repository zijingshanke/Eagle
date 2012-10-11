package com.fordays.masssending.httpclient.imagerecognize.ocr;

import java.io.File;

/**
 * 图形识别结果对象
 */
public class OCResult {
	private boolean isValidated = false;

	private String validateInfo = "";
	private String resultString = "";

	private File imageFile = null;// 原始图形
	private File tiffImageFile = null;// 处理后供组件识别的图形格式

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getValidateInfo() {
		return validateInfo;
	}

	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}

	public File getTiffImageFile() {
		return tiffImageFile;
	}

	public void setTiffImageFile(File tiffImageFile) {
		this.tiffImageFile = tiffImageFile;
	}
}
