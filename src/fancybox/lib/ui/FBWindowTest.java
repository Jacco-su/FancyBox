package fancybox.lib.ui;

import javax.swing.*;
import java.awt.*;

public class FBWindowTest extends JWindow {
	JPanel panel=new JPanel();
	public FBWindowTest(){
		panel.setSize(200,200);
		panel.setLocation(0,0);
		panel.setLayout(null);
		this.setLayout(null);
		super.add(panel);
		init();
	}
	public void paint(Graphics g){
		super.paint(g);
	}
	public void init(){
		super.setAlwaysOnTop(true);
		super.setBounds(200,200,200,200);

		super.setVisible(true);
	}
}
