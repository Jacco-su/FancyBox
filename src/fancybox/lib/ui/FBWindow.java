package fancybox.lib.ui;

import fancybox.core.bar.PluginBar;
import fancybox.core.launcher.LauncherMain;
import fancybox.core.window.WindowManager;
import fancybox.lib.image.ImageConvert;
import fancybox.plugin.FBPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * a window that can be shown by plugin,
 * displays basic info of plugin:logo,name
 * also provides basic buttons for a window:min,fixed,drag,close
 * @author Rock Chin
 * @create 2020/12/15
 */
public class FBWindow extends JWindow {
	public final static int ICON_WIDTH_HEIGHT=30;
	private final static Color transparent=new Color(0,0,0,0);
	private JPanel panel=new JPanel();


	BufferedImage icon;

	public FBPlugin plugin;
	public BufferedImage getFBWIcon() {
		return icon;
	}
	public FBWindow(FBPlugin plugin){
		this.icon=plugin.icon.getSubimage
				(0,0,plugin.icon.getWidth(),plugin.icon.getHeight());
		this.setName(plugin.name);
		this.plugin=plugin;
		init();
	}
	public void init(){
		this.setAlwaysOnTop(true);
		//检查图片是否需要缩放
		if(icon.getWidth()> FBWindow.ICON_WIDTH_HEIGHT||icon.getHeight()>FBWindow.ICON_WIDTH_HEIGHT) {
			ImageConvert imageConvert = new ImageConvert(icon);
			imageConvert.changeResolutionRate(FBWindow.ICON_WIDTH_HEIGHT / (double) icon.getWidth());
			this.icon = imageConvert.getProduct();
		}
		panel.setSize(300,300);
		panel.setLocation(0,0);
		panel.setLayout(null);
		panel.setVisible(true);
		this.setBackground(transparent);
		this.setForeground(Color.darkGray);
		this.setLayout(null);
		super.add(panel);
		LauncherMain.windowManager.windows.add(this);
//		System.out.println("panel:"+panel.getBounds()+"\nwindow:"+this.getBounds());
	}
//
	@Override
	public void paint(Graphics g){
		//绘制panel的控件
		super.paint(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//绘制圆角矩形底
		g.setColor(getForeground());
		g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),20,20);

	}
	@Override
	public Component add(Component component){
		panel.add(component);
		return component;
	}
	@Override
	public void setSize(int w,int h){
		if (!fbwVisible) {
			panel.setSize(w, h);
			super.setSize(w + 45, h);
		}
	}
	@Override
	public void setSize(Dimension d){
		setSize(d.width,d.height);
	}

	/**
	 *
	 * @param bg
	 */
	@Override
	public void setBackground(Color bg){
		this.panel.setBackground(bg);
		super.setBackground(bg);
	}
	private boolean fbwVisible=false;

	public boolean isFBWVisible(){
		return fbwVisible;
	}

	@Override
	public void setVisible(boolean b){
		if (b){
			showFBW();
		}else {
			hideFBW();
		}
		fbwVisible=b;
//		System.out.println("panel:"+panel.getBounds()+"\nwindow:"+this.getBounds());
	}
	public void showFBW(){
		this.updateLocation();
//		System.out.println("updated");
		super.add(panel);
		show();
	}
	public void hideFBW(){
		super.setVisible(false);
	}

	/**
	 * close this window
	 */
	public void close(){
		if (exitOnClose){
			plugin.stop();
		}
	}

	public void updateLocation(){
		//取得屏幕高度
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		if (!fbwVisible) {
			//计算已使用的总高度
			int usedHeight = 0;
			for (FBWindow window1 : LauncherMain.windowManager.windows) {
				if (window1.fbwVisible)
					usedHeight += window1.getHeight() + WindowManager.WINDOW_DISTANCE;
			}
			//检查是否有空间
			if ((screenHeight - usedHeight - WindowManager.TOP_DISTANCE - WindowManager.BOTTOM_DISTANCE)
					> this.getHeight()) {
//				System.out.println("show");
				super.setLocation(LauncherMain.launcherBall.getX() - this.getWidth()
						, WindowManager.TOP_DISTANCE + usedHeight);
			} else {
				//移动之下的窗口
				for (FBWindow window : LauncherMain.windowManager.windows) {
					if (window.fbwVisible) {
//						System.out.println(window.getWidth());
						window.shiftY( this.getHeight() + WindowManager.WINDOW_DISTANCE);
						//验证此窗口是否超出范围
						if (window.getY()+window.getHeight()+WindowManager.WINDOW_DISTANCE>screenHeight-WindowManager.BOTTOM_DISTANCE){
							window.setVisible(false);
//							System.out.println(screenHeight+" "+(this.getY()+this.getHeight()+WindowManager.WINDOW_DISTANCE+WindowManager.BOTTOM_DISTANCE));
						}
					}
				}
				super.setLocation(LauncherMain.launcherBall.getX() - this.getWidth(),WindowManager.TOP_DISTANCE);
			}
		}
	}
	public void shiftY(int height){
		super.setLocation(this.getX(),this.getY()+height);
	}
	/**
	 * 覆盖以禁用父类的设置位置方法
	 * @param x
	 * @param y
	 */
	@Override
	public void setLocation(int x,int y){}

	/**
	 * 覆盖以禁用父类的设置位置方法
	 * @param p
	 */
	@Override
	public void setLocation(Point p){}
	boolean exitOnClose=false;

	/**
	 * call stop() method when this window is closing
	 * @param b
	 */
	public void setExitOnClose(boolean b){
		this.exitOnClose=b;
	}
}
