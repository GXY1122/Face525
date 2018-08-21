package com.gxy.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.gxy.core.SynFaceDective;
import com.gxy.entity.Sim_m;
import com.gxy.entity.White;

public class Test {
	
	static SynFaceDective sfd=new SynFaceDective();
	private static String imagePath="C:\\Users\\Administrator\\Desktop\\img\\timg.jpg";
	private static String targetPath="C:\\Users\\Administrator\\Desktop\\ym\\target2.jpg";
	static InputStream file;
	static InputStream file2;
	static InputStream file3;
	static BufferedImage bi;
	static int height;
	static int width;
	public static class TestThread implements Runnable{

		private int beginI;
		private int endI;
		private int beginJ;
		private int endJ;
		private int[] pix;
		
		
		public int[] getPix() {
			return pix;
		}
		public void setPix(int[] pix) {
			this.pix = pix;
		}
		public void setBeginI(int beginI) {
			this.beginI = beginI;
		}
		public void setEndI(int endI) {
			this.endI = endI;
		}

		public void setBeginJ(int beginJ) {
			this.beginJ = beginJ;
		}

		public void setEndJ(int endJ) {
			this.endJ = endJ;
		}

		@Override
		public void run() {
			
			try {
				 pix=sfd.Cov(bi, imagePath, targetPath, beginI, endI, beginJ, endJ);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static int[] CombinInt(int[] a,int[] b,int[] c,int[] d){
		int[] abcd=new int[a.length+b.length+c.length+d.length];
		for (int i = 0; i < abcd.length; i++) {
			if (i<a.length) {
				abcd[i]=a[i];
			}else if(i>=a.length&&i<a.length+b.length){
				abcd[i]=b[i-a.length];
			}else if(i>=a.length+b.length&&i<a.length+b.length+c.length){
				abcd[i]=c[i-a.length-b.length];
			}else if(i>=a.length+b.length+c.length&&i<a.length+b.length+c.length+d.length){
				abcd[i]=d[i-a.length-b.length-c.length];
			}
			
		}
		System.out.println(abcd.length+"------"+a.length+"```"+b.length+"```"+c.length+"````"+d.length);
		return abcd;
		
	}
	
	public static int findWhite(BufferedImage bi,int begin_X,int begin_Y,int subWidth,int subHeight){
		int sum=0;
		Color col=new Color(255, 255, 255);
		Color col2=new Color(0, 0, 0);
		for (int i = begin_Y; i < begin_Y+subHeight; i++) {
			for (int j = begin_X; j < begin_X+subWidth; j++) {
			/*	System.out.println(bi.getRGB(j, i)+"-----"+col.getRGB()+"---------"+col2.getRGB());*/
				if (bi.getRGB(j, i)==col.getRGB()) {
					sum++;
				}
			}
		}
		
		return sum;
		
	}
	
	public static void findMouth(BufferedImage bi,int lieEnd1, int lieEnd2,
			int hangEnd1, int hangEnd2) throws IOException {
		for (int i = hangEnd1; i < hangEnd2; i++) {
			for (int j = lieEnd1; j < lieEnd2; j++) {
				int pixel = bi.getRGB(j, i);
				 int r= (pixel & 0xff0000) >> 16;
				int g = (pixel & 0xff00) >> 8;
				int b = (pixel & 0xff);
				double o=Math.acos((0.5*(2*r-g-b))/Math.sqrt((r-b)*(r-b)+(r-b)*(g-b)));
				if (o<=0.2) {
					bi.setRGB(j, i, Color.GREEN.getRGB());
				}
			}
		}
		ImageIO.write(bi, "jpg", new File("C:\\Users\\Administrator\\Desktop\\mouth.jpg"));
	}
	
	
	
	
	public static White findEyes(BufferedImage bi,int lieEnd1, int lieEnd2,
			int hangEnd1, int hangEnd2){
		int height=hangEnd2 - hangEnd1;
		int width=lieEnd2 - lieEnd1;
		int middlePoint_X=(lieEnd1+lieEnd2)/2;
		int middlePoint_Y=(hangEnd1+hangEnd2)/2;
		int subWidth=width/15;
		int subHeight=height/38;
		int sum=0;
		int index=0;
		int[] summ=new int[height*width];
		Map<Point,Integer> map=new HashMap<>();
		for (int i = hangEnd1; i < middlePoint_Y; i++) {
			for (int j = lieEnd1; j < lieEnd2; j++) {
				sum=findWhite(bi, j, i, subWidth, subHeight);
				summ[index++]=sum;
				Point xy=new Point();
				xy.setLocation(i, j);
				map.put(xy, sum);
				
				
			}
		}
		int maxSum=findMax(summ);
		White wt=new White();
		wt.setMap(map);
		wt.setMaxSum(maxSum);
		return wt;
	}
	
	
	
	
	
	public static int findMax(int[] a){
		int max=0;
		for (int i = 0; i < a.length; i++) {
			if (a[i]>max) {
				max=a[i];
			}
		}
		return max;
	}
	
	
	public static void biaojiEyes(White wt,BufferedImage bi) throws IOException{
		int max=wt.getMaxSum();
		Map<Point, Integer> map=wt.getMap();
		Iterator it=map.entrySet().iterator();
		List<Point> list=new ArrayList<>();
		while (it.hasNext()) {
			Map.Entry entry=(Entry) it.next();
			double sum=(int)entry.getValue()*1.0/max;
			Point key=(Point) entry.getKey();
			if (sum>0.4) {
				list.add(key);
				bi.setRGB(key.y,key.x , Color.green.getRGB());
			}
		}
		ImageIO.write(bi, "jpg", new File("C:\\Users\\Administrator\\Desktop\\eyees2.jpg"));
		System.out.println(list);
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		file=new FileInputStream(imagePath);
		file2=new FileInputStream(imagePath);
		file3=new FileInputStream(targetPath);
		bi=ImageIO.read(file);
		height=bi.getHeight();
		width=bi.getWidth();
		int[] a = null;
		int[] b=null;
		int[] c=null;
		int[] d=null;
		//1
		TestThread testThread1=new TestThread();
		testThread1.setBeginI(0);
		testThread1.setBeginJ(0);
		testThread1.setEndI(height/4);
		testThread1.setEndJ(width);
		
		//2
		TestThread testThread2=new TestThread();
		testThread2.setBeginI(height/4);
		testThread2.setBeginJ(0);
		testThread2.setEndI(height/2);
		testThread2.setEndJ(width);
		
		//3
		TestThread testThread3=new TestThread();
		testThread3.setBeginI(height/2);
		testThread3.setBeginJ(0);
		testThread3.setEndI(3*height/4);
		testThread3.setEndJ(width);
		
		//4
		TestThread testThread4=new TestThread();
		testThread4.setBeginI(3*height/4);
		testThread4.setBeginJ(0);
		testThread4.setEndI(height);
		testThread4.setEndJ(width);
		
		
		Thread t1=new Thread(testThread1);
		Thread t2=new Thread(testThread2);
		Thread t3=new Thread(testThread3);
		Thread t4=new Thread(testThread4);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		int[] lastPix=new int[height*width];
		lastPix=CombinInt(testThread1.getPix(), testThread2.getPix(), testThread3.getPix(), testThread4.getPix());
		bi.setRGB(0, 0, width, height, lastPix, 0, width);
		ImageIO.write(bi, "jpg", new File(targetPath));
		BufferedImage bi2=ImageIO.read(file2);
		Map<Integer, Integer> hangMap = sfd.hangBlack(bi);
		Map<Integer, Integer> lieMap = sfd.lieBlack(bi);
		int[] hang = sfd.biaoji(hangMap);
		int[] lie = sfd.biaoji(lieMap);
		sfd.dinwei(bi2, lie[0], lie[1], hang[0], hang[1]);
		System.out.println(1);
		BufferedImage bi3=ImageIO.read(file3);
		White wt=findEyes(bi3, lie[0], lie[1], hang[0], hang[1]);
		biaojiEyes(wt,bi2);
		findMouth(bi2, lie[0], lie[1], hang[0], hang[1]);
		
	}
}
