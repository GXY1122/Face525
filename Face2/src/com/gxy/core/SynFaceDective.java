package com.gxy.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import com.gxy.entity.Sim_m;

public class SynFaceDective {
	 // static BufferedImage bi;
	// static String imagePath = "C:\\Users\\Administrator\\Desktop\\000.jpg";
	// static String targetPath =
	// "C:\\Users\\Administrator\\Desktop\\target.jpg";
	static Color col = new Color(255, 255, 0);

	public static File copyByImageIO(String sourcePath, String targetPath)
			throws Exception {
		File output = null;
		try {
			File input = new File(sourcePath);
			BufferedImage bim = ImageIO.read(input);
			output = new File(targetPath);
			ImageIO.write(bim, "jpg", output);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return output;
	}

	public int[] getImageRGB(String imagePath, int i, int j) throws Exception {
		int[] rgb = new int[3];
		File file = new File(imagePath);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int pixel = bi.getRGB(j, i);
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
		return rgb;
	}

	public boolean getYCbCr(String imagePath, int i, int j) throws Exception {
		int rgb[] = getImageRGB(imagePath, i, j);
		int r = rgb[0];
		int g = rgb[1];
		int b = rgb[2];
		double Cb = 128 - 0.168736 * r - 0.331264 * g + 0.5 * b;
		double Cr = 128 + 0.5 * r - 0.418688 * g - 0.081312 * b;
		if (Cb > 77 && Cb < 127 && Cr > 133 && Cr < 173) {
			return true;
		}
		return false;
	}

	public Image skinFindTopLeft(BufferedImage bi, String imagePath,
			String targetPath, Rect rect) throws Exception {
		int h = bi.getHeight();
		int w = bi.getWidth();
		int[] d = new int[h * w];
		long startime = System.currentTimeMillis();
		System.out.println("开始时间--" + startime + "--" + Thread.currentThread());
		for (int i = rect.y; i < (2 * rect.y + rect.height - 1) / 2; i = i + 2) {
			for (int j = rect.x; j < (2 * rect.x + rect.width - 1) / 2; j = j + 2) {

				if (getYCbCr(imagePath, i, j)) {
					System.out.println("i=" + i + ",j=" + j + "--"
							+ Thread.currentThread());
					bi.setRGB(j, i, col.getRGB());
				}
			}
		}
		ImageIO.write(bi, "jpg", new File(targetPath));
		long endtime = System.currentTimeMillis();
		System.out.println("结束时间--" + endtime + "--" + Thread.currentThread());
		System.out.println("总耗时--" + (endtime - startime) + "--"
				+ Thread.currentThread());
		return bi;
	}

	public Image skinFindTopRight(BufferedImage bi, String imagePath,
			String targetPath, Rect rect) throws Exception {
		int h = bi.getHeight();
		int w = bi.getWidth();
		int[] d = new int[h * w];
		long startime = System.currentTimeMillis();
		System.out.println("开始时间--" + startime + "--" + Thread.currentThread());
		for (int i = rect.y; i < (2 * rect.y + rect.height - 1) / 2; i = i + 2) {
			for (int j = (2 * rect.x + rect.width) / 2; j < rect.x + rect.width
					- 1; j = j + 2) {

				if (getYCbCr(imagePath, i, j)) {
					System.out.println("i=" + i + ",j=" + j + "--"
							+ Thread.currentThread());
					bi.setRGB(j, i, col.getRGB());
				}
			}
		}
		ImageIO.write(bi, "jpg", new File(targetPath));
		long endtime = System.currentTimeMillis();
		System.out.println("结束时间--" + endtime + "--" + Thread.currentThread());
		System.out.println("总耗时--" + (endtime - startime) + "--"
				+ Thread.currentThread());
		return bi;
	}

	public Image skinFindBottomLeft(BufferedImage bi, String imagePath,
			String targetPath, Rect rect) throws Exception {
		int h = bi.getHeight();
		int w = bi.getWidth();
		int[] d = new int[h * w];
		long startime = System.currentTimeMillis();
		System.out.println("开始时间--" + startime + "--" + Thread.currentThread());
		for (int i = (2 * rect.y + rect.height - 1) / 2; i < rect.y
				+ rect.height - 1; i = i + 2) {
			for (int j = rect.x; j < (2 * rect.x + rect.width - 1) / 2; j = j + 2) {

				if (getYCbCr(imagePath, i, j)) {
					System.out.println("i=" + i + ",j=" + j + "--"
							+ Thread.currentThread());
					bi.setRGB(j, i, col.getRGB());
				}
			}
		}
		ImageIO.write(bi, "jpg", new File(targetPath));
		long endtime = System.currentTimeMillis();
		System.out.println("结束时间--" + endtime + "--" + Thread.currentThread());
		System.out.println("总耗时--" + (endtime - startime) + "--"
				+ Thread.currentThread());
		return bi;
	}

	public Image skinFindBottomRight(BufferedImage bi, String imagePath,
			String targetPath, Rect rect) throws Exception {
		int h = bi.getHeight();
		int w = bi.getWidth();
		int[] d = new int[h * w];
		long startime = System.currentTimeMillis();
		System.out.println("开始时间--" + startime + "--" + Thread.currentThread());
		for (int i = (2 * rect.y + rect.height) / 2; i < rect.y + rect.height
				- 1; i = i + 2) {
			for (int j = (2 * rect.x + rect.width) / 2; j < rect.x + rect.width
					- 1; j = j + 2) {

				if (getYCbCr(imagePath, i, j)) {
					System.out.println("i=" + i + ",j=" + j + "--"
							+ Thread.currentThread());

					bi.setRGB(j, i, col.getRGB());
				}
			}
		}
		ImageIO.write(bi, "jpg", new File(targetPath));
		long endtime = System.currentTimeMillis();
		System.out.println("结束时间--" + endtime + "--" + Thread.currentThread());
		System.out.println("总耗时--" + (endtime - startime) + "--"
				+ Thread.currentThread());
		return bi;
	}


	public BufferedImage getbi(String imagePath, String targetPath)
			throws Exception {

		File im = copyByImageIO(imagePath, targetPath);
		InputStream is = new FileInputStream(im);
		BufferedImage bi = ImageIO.read(is);
		return bi;

	}


	/*
	 * Cb_Mean = 117.4361 Cr_Mean = 156.5599 Cov00 = 160.1301 Cov01 = 12.1430
	 * Cov10 = 12.1430 Cov11 = 299.4574
	 */
	
	public int[] Cov(BufferedImage bi, String imagePath, String targetPath,int beginI,int endI,int beginJ,int endJ)
			throws Exception {

		double Cb_Mean = 117.4361;
		double Cr_Mean = 156.5599;
		double Cov00 = 160.1301;
		double Cov01 = 12.1430;
		double Cov10 = 12.1430;
		double Cov11 = 299.4574;
		int[] pix=new int[(endI-beginI)*(endJ-beginJ)];
		int index=0;
		double max = 0;
		for (int i = beginI; i < endI; i++) {
			for (int j = beginJ; j < endJ; j++) {
				int rgb[] = getImageRGB(imagePath, i, j);
				int r = rgb[0];
				int g = rgb[1];
				int b = rgb[2];
				double Cb = 128 - 0.168736 * r - 0.331264 * g + 0.5 * b;
				double Cr = 128 + 0.5 * r - 0.418688 * g - 0.081312 * b;
				double tt = (Cb - Cb_Mean)
						* ((Cb - Cb_Mean) * Cov11 - (Cr - Cr_Mean) * Cov10)
						+ (Cr - Cr_Mean)
						* (-(Cb - Cb_Mean) * Cov01 + (Cr - Cr_Mean) * Cov00);
				tt = (-0.5 * tt) / (Cov00 * Cov11 - Cov01 * Cov10);
				pix[index++]=(int) tt;
				
			}

		}
		return pix;
	}

	
	public Map<Integer, Integer> lieBlack(BufferedImage bi){
		Color col=new Color(0, 0, 0);
		Map<Integer,Integer > map=new HashMap<Integer,Integer>();
		int height=bi.getHeight();
		int width=bi.getWidth();
		for (int j = 0; j <width ; j++) {
			int sum=0;
			for (int i = 0; i < height; i++) {
				int color=bi.getRGB(j, i);
				if (color==col.getRGB()) {
					sum++;
				}
			}
			map.put(j, sum);
		}
		return map;
	}
	
	public Map<Integer, Integer> hangBlack(BufferedImage bi){
		Color col=new Color(0, 0, 0);
		Map<Integer,Integer > map=new HashMap<Integer,Integer>();
		int height=bi.getHeight();
		int width=bi.getWidth();
		for (int i = 0; i <height ; i++) {
			int sum=0;
			for (int j = 0; j < width; j++) {
				int color=bi.getRGB(j, i);
				if (color==col.getRGB()) {
					sum++;
				}
			}
			map.put(i, sum);
		}
		return map;
	}
	
	
	
	/*public static void main(String[] args) throws Exception {
		String imagePath = "C:\\Users\\Administrator\\Desktop\\result\\1.jpg";
		String targetPath = "C:\\Users\\Administrator\\Desktop\\result\\tar.jpg";
		SynFaceDective sfd = new SynFaceDective();
		BufferedImage bi = sfd.getbi(imagePath, targetPath);
		Map<Integer, Integer> hangMap = sfd.hangBlack(bi);
		Map<Integer, Integer> lieMap = sfd.lieBlack(bi);
		int[] hang = sfd.biaoji(hangMap);
		int[] lie = sfd.biaoji(lieMap);
		BufferedImage bi2 = sfd.getbi("C:\\Users\\Administrator\\Desktop\\img\\jobs1.jpg", targetPath);
		sfd.dinwei(bi2, lie[0], lie[1], hang[0], hang[1]);

	}*/
	
	public int[] biaoji(Map<Integer, Integer> map) {
		int[] a = new int[2];
		Iterator iterator = map.entrySet().iterator();
		int max = 0;
		while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			int value = (int) entry.getValue();
			int key = (int) entry.getKey();
			if (value > max) {
				max = value;
			}
		}
		Map<Integer, Double> map2 = new HashMap<>();
		Iterator iterator2 = map.entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry entry = (Entry) iterator2.next();
			double value = ((int) entry.getValue() * 1.0) / max;
			int key = (int) entry.getKey();
			if (value == 1.0) {
				int begin = key;
				int end1 = 0;
				int end2 = 0;
				for (int i = key; i > 0; i--) {
					if ((map.get(i) * 1.0) / max > 0.5) {
						end2=i;
						continue;
					} else {
						end1 = i;
						break;
					}
				}
				for (int i = key; i < map.size(); i++) {
					if ((map.get(i) * 1.0) / max > 0.55) {
						end2=i;
						continue;
					} else {
						end2 = i;
						break;
					}
				}
				//System.out.println(end1 + "----" + end2);

				a[0] = end1;
				a[1] = end2;
			}

		}
		return a;
	}
	
	public void dinwei(BufferedImage bi, int lieEnd1, int lieEnd2,
			int hangEnd1, int hangEnd2) throws IOException {
		Graphics g = bi.getGraphics();
		Graphics2D g2d = (Graphics2D) g; 
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.drawRect(lieEnd1, hangEnd1, lieEnd2 - lieEnd1, hangEnd2 - hangEnd1);
		FileOutputStream out = new FileOutputStream(
				"C:\\Users\\Administrator\\Desktop\\dinwei.jpg");
		ImageIO.write(bi, "jpg", out);
	}
	

	
	/*public static void main(String[] args) throws Exception {
		String imagePath = "C:\\Users\\Administrator\\Desktop\\img\\face6.jpg";
		String targetPath = "C:\\Users\\Administrator\\Desktop\\result\\target.jpg";
		SynFaceDective sfd = new SynFaceDective();
		BufferedImage bi = sfd.getbi(imagePath, targetPath);
		Sim_m simm=sfd.Cov(bi, imagePath, targetPath);
		int[] pix=simm.getPix();
		bi.setRGB(0, 0, simm.getWidth(), simm.getHeight(), pix, 0, simm.getWidth());
		ImageIO.write(bi, "jpg", new File("C:\\Users\\Administrator\\Desktop\\result\\target1.jpg"));
		System.out.println(1);

	}*/
}
