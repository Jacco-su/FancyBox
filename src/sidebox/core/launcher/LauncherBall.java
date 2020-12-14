package sidebox.core.launcher;

import javax.swing.*;
import java.awt.*;

/**
 * Launcher ball
 * extends from JWindow,click to open launch bar and opened tabs
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class LauncherBall extends JWindow {
	final static Color lightLightGray=new Color(250,250,250,215);
	public void paint(Graphics g){
		//绘制阴影
		g.setColor(new Color(0,0,0,35));
		g.fillOval(0,0,this.getWidth(),this.getHeight());
		//绘制一个白底圆形
		g.setColor(lightLightGray);
		g.fillOval(1,1,this.getWidth()-3,this.getHeight()-3);
	}
	public LauncherBall(int w,int h){
		this.setAlwaysOnTop(true);
		this.setSize(w,h);
		this.setOpacity(1);
		this.setLocation(200,200);
		this.setBackground(new Color(0,0,0,0));
//		this.getContentPane().setBackground(new Color(0,0,0,0));
		this.setVisible(true);
	}
}
