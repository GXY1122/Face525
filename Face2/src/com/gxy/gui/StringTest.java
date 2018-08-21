package com.gxy.gui;

public class StringTest {
public static void main(String[] args) {
	String name="C:\\Users\\Administrator\\Desktop\\result\\noce.jpg";
	String subname=name.substring(name.lastIndexOf("\\"),name.indexOf(".")).replace("\\", "image_");
	System.out.println(subname);
}
}
