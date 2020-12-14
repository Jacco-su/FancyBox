package fancybox.core.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Launcher ball
 * extends from JWindow,click to open launch bar and opened tabs
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class LauncherBall extends JWindow {
	/**
	 * drag launcher ball on screen
	 * @author Rock Chin
	 * @create 2020/12/14
	 * @edit 2020/12/14
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
	 * @edit 2020/12/14
	 */
	private static class MouseBall implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {

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
		this.addMouseMotionListener(new DragBall());
		this.addMouseListener(new MouseBall());
		this.setVisible(true);
	}
}
