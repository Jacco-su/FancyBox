package fancybox.core.launcher;

import fancybox.lib.animation.Animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Launcher ball
 * extends from JWindow,click to open launch bar and opened tabs
 * @author Rock Chin
 * @create 2020/12/14
 */
public class LauncherBall extends JWindow {
	/**
	 * drag launcher ball on screen
	 * @author Rock Chin
	 * @create 2020/12/14
	 */
	private static class DragBall implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			LauncherMain.launcherBall.setLocation(ballPosition.x+(e.getXOnScreen()-mousePositionOnScreen.x)
					, ballPosition.y+(e.getYOnScreen()-mousePositionOnScreen.y));
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}
	static Point ballPosition=new Point(),mousePositionOnScreen=new Point();
	//change ball icon on click
	private final static Animation ballAnimationOnClick=new Animation(()->{

	});
	/**
	 * handle mouse event
	 * @author Rock Chin
	 * @create 2020/12/14
	 */
	private static class MouseBall implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(LauncherMain.pluginBar.isVisible()){
				LauncherMain.pluginBar.setVisible(false);
			}else {
				//perform ball animation
				ballAnimationOnClick.perform();

				LauncherMain.pluginBar.showBar();
			}
		}

		//record mouse info when mouse pressed on ball
		@Override
		public void mousePressed(MouseEvent e) {
			LauncherBall.ballPosition=LauncherMain.launcherBall.getLocation();
			LauncherBall.mousePositionOnScreen=e.getLocationOnScreen();
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	final static Color lightLightGray=new Color(255,255,255,240);
	final static Color lightLightLightGray=new Color(255,255,255,140);
	final static BasicStroke iconStroke=new BasicStroke(3);
	public Graphics graphics;
	final static int iconw=20,iconh=10,ix=0,iy=2;
	static BufferedImage ball_bg=new BufferedImage(80,80,BufferedImage.TYPE_INT_ARGB);
	static BufferedImage ball_bg_white=new BufferedImage(69,69,BufferedImage.TYPE_INT_ARGB);
	static Color[] blackTransparent=new Color[80];
	static {
		for(int i=0;i<80;i++){
			blackTransparent[i]=new Color(0,0,0,(i));
		}
	}
	static {
		Graphics g=ball_bg.getGraphics();
		int cx=80/2,cy=80/2;
		for(int i=0;i<80;i++){
			for(int j=0;j<80;j++){
				int d=distance(cx,cy,i,j);
				if(d<cx){
					g.setColor(blackTransparent[79-(int)((double)d/(double)cx*80)]);
//					System.out.println(19-(int)((double)d/(double)cx*20));
					g.drawLine(i,j,i,j);
				}
			}
		}
	}
	private static int distance(int x0,int y0,int x1,int y1){
		return (int)Math.sqrt( Math.pow(x0-x1,2)+Math.pow(y0-y1,2) );
	}
	public void paint(Graphics g){
		graphics=g;
//		//绘制阴影
//		g.setColor(new Color(0,0,0,35));
//		g.fillOval(0,0,this.getWidth(),this.getHeight());
//		//绘制羽化背景
		g.drawImage(ball_bg,0,0,this);
		//绘制一个白底圆形
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(lightLightGray);
		g.fillOval(8,8,this.getWidth()-15,this.getHeight()-15);
		//绘制图标
		g.setColor(Color.darkGray);
		((Graphics2D)g).setStroke(iconStroke);
		g.drawLine(this.getWidth()/2-iconw/2+ix,this.getHeight()/2-iconh/2-6+iy,this.getWidth()/2-iconw/2+iconw+2+ix,this.getHeight()/2-iconh/2-6+iy);
		g.drawLine(this.getWidth()/2-iconw/2+ix,this.getHeight()/2-iconh/2+iy,this.getWidth()/2-iconw/2+iconw+2+ix,this.getHeight()/2-iconh/2+iy);
		g.drawLine(this.getWidth()/2-iconw/2+ix,this.getHeight()/2-iconh/2+6+iy,this.getWidth()/2-iconw/2+iconw+2+ix,this.getHeight()/2-iconh/2+6+iy);
		g.drawLine(this.getWidth()/2-iconw/2+ix,this.getHeight()/2-iconh/2+12+iy,this.getWidth()/2-iconw/2+iconw+2+ix,this.getHeight()/2-iconh/2+12+iy);

	}
	public LauncherBall(int w,int h){
		this.setAlwaysOnTop(true);
		this.setSize(w+10,h+10);
//		this.setOpacity(1);
		this.setLocation(200,200);
		this.setBackground(new Color(0,0,0,0));
//		this.getContentPane().setBackground(new Color(0,0,0,0));
		this.addMouseMotionListener(new DragBall());
		this.addMouseListener(new MouseBall());
		this.setVisible(true);
	}
}
