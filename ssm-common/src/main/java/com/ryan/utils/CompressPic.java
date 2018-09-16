package com.ryan.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 压缩图片流工具
 * @author YoriChen
 * @date 2018/5/21
 */
public class CompressPic {

	/**
	 * 传入byte[],传出byte[]
	 * @param imageByte 图片字节数组 
	 * @param width 生成小图片宽度 
	 * @param height 生成小图片高度 
	 * @param gp 是否等比缩放 
	 * @return 
	 */
	public static byte[] compressPic(byte[] imageByte, int width, int height, boolean gp) {
		byte[] inByte = null;
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
			Image img = ImageIO.read(byteInput);

			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return inByte;
			} else {
				int newWidth; int newHeight;
				// 判断是否是等比缩放
				if (gp == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					// 输出的图片宽度
					newWidth = width;
					// 输出的图片高度
					newHeight = height;
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
				 * 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				ImageWriter imgWrier;
				ImageWriteParam imgWriteParams;
				// 指定写图片的方式为 jpg
				imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
				imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
				// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
				//imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
				// 这里指定压缩的程度，参数qality是取值0~1范围内，
				//imgWriteParams.setCompressionQuality((float)45217/imageByte.length);

				// imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
				//ColorModel colorModel = ColorModel.getRGBdefault();
				// 指定压缩时使用的色彩模式
				//imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(100, 100)));
				// 将压缩后的图片返回字节流
				ByteArrayOutputStream out = new ByteArrayOutputStream(imageByte.length);
				imgWrier.reset();
				// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				// 调用write方法，就可以向输入流写图片
				imgWrier.write(null, new IIOImage(tag, null, null), imgWriteParams);
				out.flush();
				out.close();
				byteInput.close();
				inByte = out.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inByte;
	}
}
