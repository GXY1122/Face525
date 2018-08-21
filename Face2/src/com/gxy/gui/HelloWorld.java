package com.gxy.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.gxy.core.*;

public class HelloWorld {

    /**
     * @param args
     */
    public static void main(String[] args) {

        File file = new File("C:\\Users\\Administrator\\Desktop\\result\\");
        file.mkdir();

        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    JFrame frame = new ImageViewerFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }
}

class ImageViewerFrame extends JFrame {

    String name = "";

    public ImageViewerFrame() throws Exception {
        setTitle("ImageViewer");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        label = new JLabel();
        add(label);
        JPanel panel = new JPanel();
        Container container = getContentPane();
        JButton btn = new JButton("显示检测结果");
        container.setLayout(new FlowLayout());
        panel.add(btn);
        container.add(panel);
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menu = new JMenu("文件");
        menubar.add(menu);
        JMenuItem openItem = new JMenuItem("打开文件");
        menu.add(openItem);
        JMenuItem exitItem = new JMenuItem("关闭文件");
        menu.add(exitItem);
        JMenuItem faceItem = new JMenuItem("人脸检测");
        menu.add(faceItem);
        JMenuItem eyeItem = new JMenuItem("眼睛定位");
        menu.add(eyeItem);
        JMenuItem noceItem = new JMenuItem("鼻子定位");
        menu.add(noceItem);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String subName = name.substring(name.lastIndexOf("\\"), name.indexOf(".")).replace("\\", "image_");
                // TODO Auto-generated method stub
                label.setIcon(new ImageIcon(
                        "C:\\Users\\Administrator\\Desktop\\" + subName + "target.jpg"));
            }
        });

        openItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    name = chooser.getSelectedFile().getPath();
                    System.out.println(name);
                    label.setIcon(new ImageIcon(name));
                    label.setVerticalAlignment(SwingUtilities.CENTER);
                }
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });

        eyeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subName = name.substring(name.lastIndexOf("\\"), name.indexOf(".")).replace("\\", "image_");
                OpenCVTest test = new OpenCVTest();
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                Mat src = Imgcodecs.imread(name);
                Mat dst2 = test.glasses(src);
                Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\" + subName + "glasses.jpg", dst2);
                label.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\result\\" + subName + "glasses.jpg"));

            }
        });

        noceItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subName = name.substring(name.lastIndexOf("\\"), name.indexOf(".")).replace("\\", "image_");
                OpenCVTest test = new OpenCVTest();
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                Mat src = Imgcodecs.imread(name);
                Mat dst = test.noce(src);
                Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\result\\" + subName + "noce.jpg", dst);
                label.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\result\\" + subName + "noce.jpg"));
            }
        });


        faceItem.addActionListener(new ActionListener() {

            private String imagePath = "";
            private String targetPath = "";
            private Rect facei = null;
            final SynFaceDective syn = new SynFaceDective();
            BufferedImage bi = null;

            class topImg1 implements Runnable {

                @Override
                public void run() {
                    try {
                        syn.skinFindTopLeft(bi, imagePath, targetPath, facei);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            class topImg2 implements Runnable {

                @Override
                public void run() {
                    try {
                        syn.skinFindTopRight(bi, imagePath, targetPath, facei);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            class bottomImg1 implements Runnable {

                @Override
                public void run() {
                    try {
                        syn.skinFindBottomLeft(bi, imagePath, targetPath, facei);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            class bottomImg2 implements Runnable {

                @Override
                public void run() {
                    try {
                        syn.skinFindBottomRight(bi, imagePath, targetPath,
                                facei);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                String subName = name.substring(name.lastIndexOf("\\"), name.indexOf(".")).replace("\\", "image_");
                targetPath = "C:\\Users\\Administrator\\Desktop\\result\\" + subName + "target.jpg";
                System.out.println(Thread.activeCount());
                try {
                    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                    imagePath = name;
                    System.out.println(imagePath);
                    OpenCVTest oct = new OpenCVTest();
                    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                    Mat src = Imgcodecs.imread(imagePath);
                    Mat glasses1 = oct.glasses(src);
                    // Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\target1.jpg",
                    // glasses1);
                    ArrayList<Rect> face = oct.faceRect(src);
                    if (face == null) {
                        Imgcodecs
                                .imwrite(
                                        "C:\\Users\\Administrator\\Desktop\\target.jpg",
                                        glasses1);
                    } else {
                        for (int i = 0; i < face.size(); i++) {
                            facei = face.get(i);
							/*Imgproc.rectangle(
									glasses1,
									new Point(face.get(i).x, face.get(i).y),
									new Point(
											face.get(i).x + face.get(i).width,
											face.get(i).y + face.get(i).height),
									new Scalar(0, 0, 255), 2);*/
                            /*					Imgcodecs.imwrite("C:\\Users\\Administrator\\Desktop\\target.jpg",glasses1);*/
                            bi = syn.getbi(
                                    "C:\\Users\\Administrator\\Desktop\\target.jpg",
                                    targetPath);
                            topImg1 top1 = new topImg1();
                            topImg2 top2 = new topImg2();
                            bottomImg1 bot1 = new bottomImg1();
                            bottomImg2 bot2 = new bottomImg2();
                            Thread t1 = new Thread(top1);
                            Thread t2 = new Thread(bot1);
                            Thread t3 = new Thread(top2);
                            Thread t4 = new Thread(bot2);
                            t1.start();
                            t2.start();
                            t3.start();
                            t4.start();
                            label.setText("正在检测，可能需要点时间，降低图片分辨率可以减少检测时间！");
                            t1.join();
                            t2.join();
                            t3.join();
                            t4.join();
                            label.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\result\\" + subName + "target.jpg"));
                        }
                    }

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
    }

    private JLabel label;
    private JFileChooser chooser;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 500;
}