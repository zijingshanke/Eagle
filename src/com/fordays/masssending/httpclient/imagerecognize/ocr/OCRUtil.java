package com.fordays.masssending.httpclient.imagerecognize.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import com.neza.exception.AppException;

/**
 * 图形识别工具类
 */
public class OCRUtil {

	/**
	 * 识别验证码（无图形过滤）
	 */
	public static OCResult recognizeValidation(File imageFile) throws Exception {
		OCResult ocresult = validate(imageFile);
		return recognizeOCResult(ocresult);
	}

	/**
	 * 识别验证码（图形过滤）
	 */
	public static OCResult recognizeValidationAsFilter(File imageFile,
			File tiffImageFile) throws Exception {
		OCResult ocresult = validate(imageFile, tiffImageFile);
		return recognizeOCResultAsFilter(ocresult);
	}

	/**
	 * 识别OCResult,无图形过滤
	 */
	public static OCResult recognizeOCResult(OCResult result) throws Exception {
		String recognizeText = "";
		try {
			File tempFile = result.getImageFile();

			BufferedImage buffImage = ImageIOHelper.getBufferImage(tempFile);// File-->BufferedImage

			System.out.println("tempFile Path:" + tempFile.getAbsolutePath());
			// tempFile.delete();// delete temp file

			File tiffFile = createTIFFile(buffImage);// create TIFF file

			System.out.println("tiffFile Path:" + tiffFile.getAbsolutePath());

			OCR ocr = new OCR();
			recognizeText = ocr.recognizeTIFFText(tiffFile);

			// tiffFile.delete();//

			result.setValidated(true);
			result.setResultString(recognizeText);
		} catch (Exception e) {
			e.printStackTrace();
			result.setValidated(false);
			result.setValidateInfo("ImageFileter=null,recognizeText 异常");
		}
		return result;
	}

	/**
	 * 识别OCResult,提前进行图形过滤
	 */
	public static OCResult recognizeOCResultAsFilter(OCResult result)
			throws Exception {
		String recognizeText = "";
		try {
			File tempFile = result.getImageFile();

			System.out.println("tempFile Path:" + tempFile.getAbsolutePath());
			// tempFile.delete();// delete temp file

			File tiffFile = result.getTiffImageFile();
			// System.out.println("tiffFile Path:" +
			// tiffFile.getAbsolutePath());

			OCR ocr = new OCR();
			recognizeText = ocr.recognizeTIFFText(tiffFile);

			// tiffFile.delete();//
			result.setValidated(true);
			result.setResultString(recognizeText);
		} catch (Exception e) {
			e.printStackTrace();
			result.setValidated(false);
			result.setValidateInfo("ImageFileter=null,recognizeText 异常");
		}
		return result;
	}

	public static OCResult validate(File imageFile) throws AppException {
		OCResult ocresult = new OCResult();

		// 暂时不做验证
		ocresult.setValidated(true);
		ocresult.setImageFile(imageFile);

		return ocresult;
	}

	public static OCResult validate(File imageFile, File tiffImageFile)
			throws AppException {
		OCResult ocresult = new OCResult();

		// 暂时不做验证
		ocresult.setValidated(true);
		ocresult.setImageFile(imageFile);
		ocresult.setTiffImageFile(tiffImageFile);

		return ocresult;
	}

	/**
	 * 获取TIFF文件，不做过滤
	 * 
	 */
	public static File createTIFFile(BufferedImage buffImage) {
		// BufferedImage-->File(TIFF格式)
		File tiffFile = ImageIOHelper.createTIFFImage(buffImage);
		return tiffFile;
	}

	public static File createTIFFileAsFilter(File imageFile) {
		// File-->BufferedImage
		BufferedImage buffImage = ImageIOHelper.getBufferImage(imageFile);
		return createTIFFile(buffImage);
	}

	/**
	 * @获取图像过滤后的TIFF文件
	 * @实际应用时应为抽象方法或接口，于具体业务方法中实�现
	 * @过滤方案依实际图像确�定
	 */
	public static File createTIFFileAsFilter(BufferedImage buffImage) {
		// 图形过滤
		ImageFilter filter = new ImageFilter(buffImage);

		filter = new ImageFilter(buffImage);
		buffImage = filter.changeGrey();// 二�值化

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.sharp();// 锐化（清晰度）

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.median();// 中�?�滤�?

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.lineGrey();// 线�?�灰度变�?

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.grayFilter();// 转为黑白灰度�?

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.changeGrey();// 二�?�化

		// filter = new ImageFilter(buffImage);
		// buffImage = filter.scaling(new Double(2));// 平滑缩放

		// BufferedImage-->File(TIFF格式)

		return createTIFFile(buffImage);
	}

	/**
	 * 
	 */
	public static File removeFile(File oldFile, boolean isDeleteOld) {
		String tarFilePath = "F:\\output\\" + oldFile.getName();
		ImageIOHelper.ioImage(oldFile, tarFilePath);
		oldFile.delete();
		File newFile = new File(tarFilePath);
		return newFile;
	}
}
