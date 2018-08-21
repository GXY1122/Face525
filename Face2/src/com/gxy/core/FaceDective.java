/*
 R>95   

G>40   
B>20  
R>G  
R>B   
Max(R,G,B)-Min(R,G,B)>15   
Abs(R-G)>15  
���������������Ƿ�ɫ
 
 Y   = 0.257*R+0.564*G+0.098*B+16  
Cb = -0.148*R-0.291*G+0.439*B+128  
Cr  = 0.439*R-0.368*G-0.071*B+128  

 Cb > 77 And Cb < 127  
Cr > 133 And Cr < 173  
*/

/**
 * description:��YCbCrɫ��ͨ���ȡͼƬ����RGB����ͨ��������ʽ��ת��YCbCrɫ����ͨ�� Cb > 77 And Cb < 127  
Cr > 133 And Cr < 173  ���ж��Ƿ�ΪƤ�������Ƥ��������ж�ֵ������
 * */

package com.gxy.core;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FaceDective {
	public boolean gray=false;
	   public int[] getImagePixel(String imagePath,int i,int j) throws Exception {  
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
	public Image skinDet(String imagePath,String targetPath,Rect rect) throws Exception{  
		File im=copyByImageIO(imagePath, targetPath);
		InputStream is=new FileInputStream(im);
		BufferedImage bi=ImageIO.read(is);
		Image image=bi;
		int h=bi.getHeight();
		int w=bi.getWidth();
        int[] d = new int[h*w];  
        long startime=System.currentTimeMillis();
        System.out.println("开始时间--"+startime);
        for(int i =rect.y;i<rect.y+rect.height-1;i=i+2){  
            for(int j=rect.x;j<rect.x+rect.width-1;j=j+2){  
 
                if(getYCbCr(imagePath, i, j))  
                {
                System.out.println("i="+i+",j="+j);
                bi.setRGB(j, i,0xff00fff0);
                }
            }  
        }
        ImageIO.write(bi, "jpg", new File("C:\\Users\\Administrator\\Desktop\\timg2.jpg"));
        long endtime=System.currentTimeMillis();
        System.out.println("结束时间--"+endtime);
        System.out.println("总耗时--"+(endtime-startime));
		return image;  
    }  
	
	public boolean getYCbCr(String imagePath, int i,int j) throws Exception{
		int rgb[]=getImagePixel(imagePath, i, j);
        int r = rgb[0];  
        int g = rgb[1];  
        int b = rgb[2];  
		 double Cb = 128 - 0.168736*r - 0.331264*g + 0.5*b;  
         double Cr = 128 + 0.5*r - 0.418688*g - 0.081312*b;
         if (Cb > 77 && Cb<127&& Cr>133 && Cr<173) {
        		 return true;
		}
		return false;
	}
	
//	public boolean isFace(String imagePath,int i,int j,int h,int w) throws Exception{
//		if (i!=0&&i!=h&&j!=0&&j!=w&&getYCbCr(imagePath, i, j)) {
//		boolean top=getYCbCr(imagePath, i-1, j);
//		boolean bottom=getYCbCr(imagePath, i+1, j);
//		boolean left=getYCbCr(imagePath, i, j-1);
//		boolean right=getYCbCr(imagePath, i, j+1);
//		if (top&&bottom&&left&&right) {
//			return true;
//		}
//		}
//		return false;
//		
//	}
//	
	
	public static File copyByImageIO(String sourcePath,String targetPath) throws Exception {
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
	/*public static void main(String[] args) throws Exception {
		FaceDective faceDective=new FaceDective();
		OpenCVTest oct=new OpenCVTest();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src=Imgcodecs.imread("C:\\Users\\Administrator\\Desktop\\img\\jobs1.jpg");
		Rect face1=oct.faceRect(src);
		Imgproc.rectangle(src, new Point(face1.x,face1.y),new Point(face1.x+face1.width,face1.y+face1.height) ,new Scalar(0,0,255),2);
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\faceRect.jpg", src);
		faceDective.skinDet("C:\\Users\\Administrator\\Desktop\\faceRect.jpg", "C:\\Users\\Administrator\\Desktop\\tar.jpg",face1);
	}*/
}
