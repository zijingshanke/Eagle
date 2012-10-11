package com.fordays.masssending.httpclient.imagerecognize.ocr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JOptionPane;
import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;

/**
 * 图形对象之间的转�?:
 */
public class ImageIOHelper {

	public ImageIOHelper() {
	}

	public static File createImage(File imageFile, String imageFormat) {
		File tempFile = null;
		try {
			Iterator<ImageReader> readers = ImageIO
					.getImageReadersByFormatName(imageFormat);
			ImageReader reader = readers.next();

			ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
			reader.setInput(iis);
			// Read the stream metadata
			IIOMetadata streamMetadata = reader.getStreamMetadata();

			// Set up the writeParam
			TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(
					Locale.US);
			tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

			// Get tif writer and set output to file
			Iterator<ImageWriter> writers = ImageIO
					.getImageWritersByFormatName("tiff");
			ImageWriter writer = writers.next();

			BufferedImage bi = reader.read(0);
			IIOImage image = new IIOImage(bi, null, reader.getImageMetadata(0));
			tempFile = tempImageFile(imageFile);
			ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);
			writer.setOutput(ios);
			writer.write(streamMetadata, image, tiffWriteParam);
			ios.close();

			writer.dispose();
			reader.dispose();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return tempFile;
	}

	public static File createTIFFImage(BufferedImage bi) {
		File tempFile = null;
		try {
			// tempFile = File.createTempFile("tempImageFile", ".tif");//
			// 默认操作系统路径

			tempFile = File.createTempFile("tempImageFile", ".tif", FileUtil
					.createFileDirAsTestTiff());// 指定保存路径

			tempFile.deleteOnExit();

			TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(
					Locale.US);

			tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

			// Get tif writer and set output to file
			Iterator<ImageWriter> writers = ImageIO
					.getImageWritersByFormatName("tiff");
			ImageWriter writer = writers.next();

			IIOImage image = new IIOImage(bi, null, null);

			tempFile = tempImageFile(tempFile);

			ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);
			writer.setOutput(ios);
			writer.write(null, image, tiffWriteParam);
			ios.close();
			writer.dispose();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return tempFile;
	}

	public static File tempImageFile(File imageFile) {
		String path = imageFile.getPath();
		StringBuffer strB = new StringBuffer(path);
		strB.insert(path.lastIndexOf('.'), 0);
		return new File(strB.toString().replaceFirst("(?<=\\.)(\\w+)$", "tif"));
	}

	public static BufferedImage getBufferImage(File imageFile) {
		BufferedImage al = null;
		try {
			String imageFileName = imageFile.getName();
			String imageFormat = imageFileName.substring(imageFileName
					.lastIndexOf('.') + 1);
			Iterator<ImageReader> readers = ImageIO
					.getImageReadersByFormatName(imageFormat);
			ImageReader reader = readers.next();

			if (reader == null) {
				JOptionPane
						.showConfirmDialog(null,
								"Need to install JAI Image I/O package.\nhttps://jai-imageio.dev.java.net");
				return null;
			}

			ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
			reader.setInput(iis);

			al = reader.read(0);

			reader.dispose();
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return al;
	}

	public static BufferedImage imageToBufferedImage(Image image) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		return bufferedImage;
	}

	public static BufferedImage imageProducerToBufferedImage(
			ImageProducer imageProducer) {
		return imageToBufferedImage(Toolkit.getDefaultToolkit().createImage(
				imageProducer));
	}

	public static byte[] image_byte_data(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}

	/**
	 * 
	 * @param srcUrl
	 *            源图片路�?
	 * @return 目标图片路径
	 */
	public static void ioImage(String srcUrl, String tarUrl) {
		File file = new File(srcUrl);
		ioImage(file, tarUrl);
	}

	public static void ioImage(File file, String tarUrl) {
		try {
			// 生成读取图片到内存的输入�?
			FileInputStream finput = new FileInputStream(file);

			// 生成从内存将图片输出到磁盘的输出�?
			FileOutputStream foutput = new FileOutputStream(new File(tarUrl));
			BufferedOutputStream bos = new BufferedOutputStream(foutput);

			int b;
			while (true) {
				if (finput.available() < 1024) {
					//    
					while ((b = finput.read()) != -1) {

						bos.write(b);
					}
					break;
				} else {

					b = finput.read();
					bos.write(b);
				}
			}
			finput.close();
			bos.close();
			foutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
