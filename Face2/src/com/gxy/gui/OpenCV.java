package com.gxy.gui;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.gxy.core.OpenCVTest;

public class OpenCV {
public static void main(String[] args) {
	try {
		OpenCVTest test=new OpenCVTest();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat src=Imgcodecs.imread("C:\\Users\\Administrator\\Desktop\\img\\133.jpg");
		Mat dst1=test.dobj(src);
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\eye.jpg", dst1);
		Mat dst2=test.glasses(src);
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\glasses.jpg", dst2);
		Mat dst3=test.face(src);
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\face.jpg", dst3);
		Mat dst4=test.noce(src);
		Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\noce.jpg", dst4);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
