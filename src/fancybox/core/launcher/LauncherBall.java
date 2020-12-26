package fancybox.core.launcher;

import fancybox.lib.ui.FBWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
	/**
	 * handle mouse event
	 * @author Rock Chin
	 * @create 2020/12/14
	 */
	private static class MouseBall implements MouseListener{
		static ArrayList<FBWindow> showingWindows=new ArrayList<>();
		static boolean windowListShowing=false;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(LauncherMain.pluginBar.isVisible()){
				LauncherMain.pluginBar.setVisible(false);
				windowListShowing=LauncherMain.windowList.isVisible();
				LauncherMain.windowList.hideWindowList();
				showingWindows.clear();
				for(FBWindow win:LauncherMain.windowManager.windows){
					if(win.isVisible()) {
						showingWindows.add(win);
						win.setVisible(false);
					}
				}
			}else {
				LauncherMain.pluginBar.showBar();
				showingWindows.forEach(FBWindow::setVisibleTrue);
				if(windowListShowing)
					LauncherMain.windowList.showingAnim.perform();
			}
			LauncherMain.launcherBall.repaint();
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
	final static BasicStroke iconStrokeLight=new BasicStroke(1);
	public Graphics graphics;
	final static int iconw=20,iconh=10,ix=0,iy=2;
	static BufferedImage ball_bg=new BufferedImage(80,80,BufferedImage.TYPE_INT_ARGB);
	static BufferedImage ball_bg_white=new BufferedImage(69,69,BufferedImage.TYPE_INT_ARGB);
	static Color[] blackTransparent=new Color[80];
	static {
		for(int i=0;i<80;i++){
			blackTransparent[i]=new Color(80,80,80,(i));
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
	private final static int bx=26,by=29;
	final static Color transparent=new Color(0,0,0,0);
	boolean inited=false;
	public void paint(Graphics g){
		graphics=g;
//		//绘制羽化背景
		if (!inited) {
			g.drawImage(ball_bg, 0, 0, this);
			inited=true;
		}
		//绘制一个白底圆形
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(lightLightGray);
		g.fillOval(8,8,this.getWidth()-15,this.getHeight()-15);
		//绘制图标
		g.setColor(Color.darkGray);
		((Graphics2D)g).setStroke(iconStroke);
		if(!LauncherMain.pluginBar.isVisible()){
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			g.drawRoundRect(bx-1, by, 31, 7, 5, 5);
			g.drawRect(bx + 3, by + 7, 24, 20);
			g.drawLine(bx + 9, by + 7 + 5, bx + 7 + 13, by + 7 + 5);
		}else{
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			g.drawRoundRect(bx-1, by-4, 31, 7, 5, 5);
			g.drawRect(bx + 3, by + 9, 24, 20);
			g.drawLine(bx + 9, by + 7 + 7, bx + 7 + 13, by + 7 + 7);
			g.setColor(Color.white);
			g.fillRect(bx + 5, by + 6,20,6);
			g.fillRect(bx + 1, by + 6,28,4);
		}
	}
	public LauncherBall(int w,int h){
		this.setAlwaysOnTop(true);
		this.setSize(w+10,h+10);
		this.setLocation(1700,400);
		this.setBackground(new Color(0,0,0,0));
		this.addMouseMotionListener(new DragBall());
		this.addMouseListener(new MouseBall());
		this.setVisible(true);
	}
}
