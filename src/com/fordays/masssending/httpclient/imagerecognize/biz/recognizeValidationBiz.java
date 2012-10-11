package com.fordays.masssending.httpclient.imagerecognize.biz;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 验证码识别接�
 */
public interface recognizeValidationBiz {
	public File createTIFFileAsFilter(BufferedImage buffImage) throws Exception;

	public File createTIFFileAsFilter(File imageFile) throws Exception;

}
