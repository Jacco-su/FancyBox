package fancybox.test;

import fancybox.lib.entry.EntryOnBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class EntryOnBarPaintTest {
	public static void main(String[] args)throws Exception {
		JWindow tw=new JWindow();
		tw.setBackground(new Color(0,0,0,0));
		tw.setBounds(400,400,40,40);
		//E:\10105\Pictures\e63eeb2e-37df-424e-9227-405f37ee80fd.JPG
		EntryOnBar te=new EntryOnBar( ImageIO.read(
				new FileInputStream("E:\\10105\\Pictures\\桂中校徽.png"))
				,"test");
		te.setLocation(0,0);
		tw.setLayout(null);
		tw.add(te);
		tw.setVisible(true);
	}
}
