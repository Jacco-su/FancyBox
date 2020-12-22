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
	/**
	 * add to this window at the bottom to make a shade effect.
	 * this panel is the whole size of this window,and above panel should be set to smaller than this window.
	 */
	private static class ShadeBg extends JPanel{
		static Color[] blackTransparent=new Color[10];
		static {
			for(int i=0;i<10;i++){
				blackTransparent[i]=new Color(80,80,80,100/10*i);
			}
		}
		final static Color halfTransparent=new Color(100,100,100,100);
		BufferedImage shade;
		public void repaintShade(){
			shade=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g=shade.createGraphics();
//			//右边竖着的
//			for (int i=0;i<10;i++){
//				g.setColor(blackTransparent[9-i]);
//				g.drawLine(this.getWidth()-10+i,10,this.getWidth()-10+i,this.getHeight()-10);
//			}
//			//下面横着的
//			for (int i=0;i<10;i++){
//				g.setColor(blackTransparent[9-i]);
//				g.drawLine(0,this.getHeight()-10+i,this.getWidth(),this.getHeight()-10+i);
//			}
//			g.setColor(halfTransparent);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			for(int i=0;i<10;i++){
				g.setColor(blackTransparent[9-i]);
				g.fillRoundRect(-17,8,this.getWidth()-7+15+i,this.getHeight()-17+i,20,20);
			}
		}
		@Override
		public void setSize(int w,int h){
			super.setSize(w,h);
			repaintShade();
		}
		public ShadeBg(){

		}
		public void paint(Graphics g){
//			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//			g.setColor(bgGray);
//			g.fillRoundRect(-20,5,this.getWidth()-7+25,this.getHeight()-7,20,20);
//			System.out.println((this.getWidth()-10)+" "+(this.getHeight()-10));
			g.drawImage(shade,0,0,this);
		}
	}
	public final static BasicStroke borderStroke=new BasicStroke(2);
	/**
	 * paint tool bar as a JPanel
	 */
	private static class ToolBar extends JPanel{
		public void paint(Graphics g){
//			System.out.println("toolBar:"+getLocation());


			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//绘制圆角矩形底
			g.setColor(getForeground());
			g.fillRoundRect(-20,0,this.getWidth()+15,this.getHeight()-5,20,20);
//			((Graphics2D)g).setStroke(borderStroke);
//			g.setColor(darker(getForeground()));
//			g.fillRoundRect(-20,2,this.getWidth()+13,this.getHeight()-7,20,20);
//			int x=this.getWidth()-30;
//			g.drawLine(x,0,x,this.getHeight());
		}
		public Color darker(Color c){
			int r=c.getRed(),g=c.getGreen(),b=c.getBlue();
			return new Color(Math.max(r-30,0),Math.max(g-30,0),Math.max(b-30,0));
		}
	}

	public final static int ICON_WIDTH_HEIGHT=30;
	private final static Color bgGray=new Color(50,50,50,80);
	private final static Color transparent=new Color(0,0,0,0);
	private JPanel panel=new JPanel();
	private ShadeBg shadeBg=new ShadeBg();
	private ToolBar toolBar= new ToolBar();


	public final static Color psc[]=new Color[] {new Color(0,0,0),new Color(127,127,127)
			,new Color(136,0,21),new Color(237,28,36),new Color(255,127,39)
			,new Color(255,242,0),new Color(34,177,76),new Color(0,162,232)
			,new Color(63,72,204),new Color(136,73,164),new Color(235,235,235)
			,new Color(255,255,255)};

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
//		panel.setLocation(200,0);
		panel.setLayout(null);
		panel.setVisible(true);
		panel.setBackground(psc[10]);
		super.setBackground(transparent);
		this.setForeground(psc[8]);
		this.setLayout(null);

		super.add(panel);
		LauncherMain.windowManager.windows.add(this);
		toolBar.setLocation(0,0);
		super.add(toolBar);
		shadeBg.setLocation(0,0);
		super.add(shadeBg);

		this.setOpacity(0.98f);
//		System.out.println("panel:"+panel.getBounds()+"\nwindow:"+this.getBounds());
	}
//
	@Override
	public void paint(Graphics g){
		//绘制panel的控件
//		super.paint(g);
//		shadeBg.paint(g);
//		toolBar.paint(g);
//		panel.paint(g);

//		shadeBg.repaint(3);
//		toolBar.repaint(20);
//		panel.repaint(40);
		shadeBg.update(g);
		toolBar.update(g);
		panel.update(g);
	}
	@Override
	public Component add(Component component){
		panel.add(component);
		return component;
	}
	@Override
	public void setSize(int w,int h){
		if (!isVisible()) {
			panel.setSize(w, h);
			shadeBg.setSize(w+50+5,h+5);
			toolBar.setSize(w+50+5,h+5);
			super.setSize(w + 50+5, h+5);
		}
	}
	@Override
	public void setSize(Dimension d){
		setSize(d.width,d.height);
	}
	@Override
	public void setForeground(Color fg){
		toolBar.setForeground(fg);
		super.setForeground(fg);
	}
	/**
	 *
	 * @param bg
	 */
	@Override
	public void setBackground(Color bg){
		this.panel.setBackground(bg);
//		super.setBackground(bg);
	}
//	private boolean fbwVisible=false;

//	public boolean isFBWVisible(){
//		return fbwVisible;
//	}

	@Override
	public void setVisible(boolean b){
		if (b){
			showFBW();
		}else {
			hideFBW();
		}
//		fbwVisible=b;
//		System.out.println("panel:"+panel.getBounds()+"\nwindow:"+this.getBounds());
	}
	public void showFBW(){
		this.updateLocation();
//		System.out.println("updated");
		super.add(panel);
		show();
		this.thumb=getThumb();
	}
	public void hideFBW(){
		super.setVisible(false);
		for(FBWindow window:LauncherMain.windowManager.windows){
			if(window.isVisible()&&window.getY()>this.getY()){
				window.shiftY(-(this.getHeight()+WindowManager.WINDOW_DISTANCE));
			}
		}
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
		if (!isVisible()) {
			//计算已使用的总高度
			int usedHeight = 0;
			for (FBWindow window1 : LauncherMain.windowManager.windows) {
				if (window1.isVisible())
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
					if (window.isVisible()) {
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
	public BufferedImage thumb;
	public BufferedImage getThumb(){
		BufferedImage thumb=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		this.paint(thumb.createGraphics());
		return thumb;
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
