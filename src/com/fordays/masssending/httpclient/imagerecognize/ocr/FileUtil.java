package com.fordays.masssending.httpclient.imagerecognize.ocr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

import com.fordays.masssending.httpclient.HttpClientInfo;
import com.neza.tool.DateUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUtil {

	public static HttpClientInfo saveImageFile(HttpClientInfo clientInfo,
			HttpClient client, String url, String IMG_FORMAT) throws Exception {
		try {
			File imgFile = saveImageFile(client, url, IMG_FORMAT);
			clientInfo.setImgFile(imgFile);
			clientInfo.setSuccess(true);
		} catch (Exception e) {
			clientInfo.setSuccess(false);
			clientInfo.setClientRemark("获取验证码图片异常<br>" + e.getMessage());
		}
		return clientInfo;
	}

	/**
	 * 获取图形验证码（测试）
	 * 
	 */
	public static File saveImageFile(HttpClient client, String url,
			String IMG_FORMAT) throws Exception {
		File file = null;

		GetMethod method = new GetMethod(url);
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("FileUtil.saveImageFile failed: "
					+ method.getStatusLine());
		} else {
			InputStream inStream = method.getResponseBodyAsStream();
			BufferedImage iag = ImageIO.read(inStream);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[10000];
			while (inStream.read(b) > 0) {
				baos.write(b);
			}
			byte[] imageByte = baos.toByteArray();
			String type = ImageUtil.getImageType(imageByte);
			// if (type.equals(ImageUtil.TYPE_NOT_AVAILABLE))
			// throw new Exception("file is not a image");
			BufferedImage myImage = ImageUtil.readImage(imageByte);

			String saveUrl = "";

			String filePath = createFileDirAsTest().getAbsolutePath();

			String filename = DateUtil.getDateString("MMddHHmmss");
			saveUrl = filePath + File.separator + filename + "." + IMG_FORMAT;
			// System.out.println("saveUrl:" + saveUrl);

			if ("bmp".equals(IMG_FORMAT)) {
				saveImage(myImage, saveUrl, IMG_FORMAT);
			} else if ("jpg".equals(IMG_FORMAT)) {
				saveJPEG(iag, saveUrl);
			}
			file = new File(saveUrl);
		}
		return file;
	}

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
	public static void saveJPEG(BufferedImage iag, String savePath)
			throws FileNotFoundException, IOException {
		OutputStream jos = new FileOutputStream(savePath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(jos);
		JPEGEncodeParam jpegEP = JPEGCodec.getDefaultJPEGEncodeParam(iag);

		jpegEP.setQuality((float) 1, true);
		encoder.encode(iag, jpegEP);
		jos.flush();
		jos.close();
	}

	public static void saveImage(BufferedImage iag, String savePath,
			String IMAGE_FORMAT) throws FileNotFoundException, IOException {
		OutputStream jos = new FileOutputStream(savePath);
		ImageUtil.printImage(iag, IMAGE_FORMAT, jos);
	}

	/**
	 * 读取InputStream保存为ImageFile
	 */
	public static File saveTempImageFile(InputStream in, String IMAGE_FORMAT)
			throws Exception {
		File tmpFile = File.createTempFile("img", "." + IMAGE_FORMAT);
		OutputStream out = new FileOutputStream(tmpFile);

		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);

		return tmpFile;
	}

	/**
	 * 读取InputStream保存为ImageFile,保持较高像素
	 */
	public static File saveTempImageFile2(InputStream in, String IMAGE_FORMAT)
			throws Exception {
		File tmpFile = new File("F:/", "test.bmp");
		OutputStream out = new FileOutputStream(tmpFile);

		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);

		return tmpFile;
	}

	public static File saveImageToFile(Image image, String fileType,
			String IMAGE_DIR) throws IOException {
		File imageDir = new File(IMAGE_DIR);
		String imageFileName = "tempImage" + new Date();
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);
		File imageFile = new File(imageDir, imageFileName);
		ImageIO.write(bufferedImage, fileType, imageFile);
		return imageFile;
	}

	public static File createFileDirAsTest() {
		String filePath = com.neza.base.Constant.WEB_INFO_PATH;
		if ("".equals(filePath)) {
			filePath = "E:\\tempValidateCode";
		} else {
			filePath += "\\tempValidateCode\\";
		}

		File imgfileDir = new File(filePath);
		if (!imgfileDir.exists()) {
			imgfileDir.mkdirs();
		}
		return createFileDir(filePath);
	}

	public static File createFileDirAsTestTiff() {
		String filePath = com.neza.base.Constant.WEB_INFO_PATH;
		if ("".equals(filePath)) {
			filePath = "E:\\tempValidateCode\\tiff";
		} else {
			filePath += "\\tempValidateCode\\tiff";
		}

		File imgfileDir = new File(filePath);
		if (!imgfileDir.exists()) {
			imgfileDir.mkdirs();
		}
		return createFileDir(filePath);
	}

	public static File createFileDir(String filePath) {
		File imgfileDir = new File(filePath);
		if (!imgfileDir.exists()) {
			imgfileDir.mkdirs();
		}

		return imgfileDir;
	}
}
