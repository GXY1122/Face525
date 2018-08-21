package com.gxy.core;





import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCVTest {

	public static Mat dobj(Mat src){
		Mat dst=src.clone();
		CascadeClassifier ccdect=new CascadeClassifier("D:\\java\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml");
		MatOfRect mor=new MatOfRect();
		ccdect.detectMultiScale(dst, mor);
		if (mor.toArray().length<=0) {
			return src;
		}
		for (Rect rect:mor.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height) ,new Scalar(0,0,255),2);
			
		}
		return dst;
		}
	public static Mat glasses(Mat src){
		Mat dst=src.clone();
		CascadeClassifier ccdect=new CascadeClassifier("D:\\java\\opencv\\build\\etc\\haarcascades\\haarcascade_eye_tree_eyeglasses.xml");
		MatOfRect mor=new MatOfRect();
		ccdect.detectMultiScale(dst, mor);
		if (mor.toArray().length<=0) {
			return src;
		}
		for (Rect rect:mor.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height) ,new Scalar(0,0,255),2);
			
		}
		return dst;
		}
	
	public static Mat face(Mat src){
		Mat dst=src.clone();
		CascadeClassifier ccdect=new CascadeClassifier("D:\\java\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml");
		MatOfRect mor=new MatOfRect();
		Mat tmp_img = null;
		ccdect.detectMultiScale(dst, mor);
		if (mor.toArray().length<=0) {
			return src;
		}
		for (Rect rect:mor.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height) ,new Scalar(0,0,255),2);
			tmp_img=new Mat(src,rect);
		
			
		}
		return tmp_img;
		}
	
	public static ArrayList<Rect> faceRect(Mat src){
		Mat dst=src.clone();
		CascadeClassifier ccdect=new CascadeClassifier("D:\\java\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml");
		MatOfRect mor=new MatOfRect();
		ccdect.detectMultiScale(dst, mor);
		ArrayList<Rect> list=new ArrayList<Rect>();
		if (mor.toArray().length<=0) {
			return null;
		}
		for (Rect rect:mor.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height) ,new Scalar(0,0,255),2);
			list.add(rect);
		}
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\target.jpg",dst);
		Mat glass=face(Imgcodecs.imread("C:\\Users\\Administrator\\Desktop\\target.jpg"));
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\face.jpg", glass);
		return list;
		
	}
	
	public static Mat noce(Mat src){
		Mat dst=src.clone();
		CascadeClassifier ccdect=new CascadeClassifier("D:\\java\\opencv\\build\\etc\\haarcascades\\haarcascade_mcs_nose.xml");
		MatOfRect mor=new MatOfRect();
		ccdect.detectMultiScale(dst, mor);
		if (mor.toArray().length<=0) {
			return src;
		}
		for (Rect rect:mor.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height) ,new Scalar(0,0,255),2);
			
		}
		return dst;
		}
	
	/*public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
			Mat src=Imgcodecs.imread("C:\\Users\\Administrator\\Desktop\\img\\133.jpg");
			Mat dst1=dobj(src);
			Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\eye.jpg", dst1);
			Mat dst2=glasses(src);
			Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\glasses.jpg", dst2);
			Mat dst3=face(src);
			Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\face.jpg", dst3);
			Mat dst4=noce(src);
			Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\noce.jpg", dst4);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
	
	

}
