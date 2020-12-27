package fancybox.lib.ui;

import fancybox.core.bar.PluginBar;
import fancybox.core.launcher.LauncherMain;
import fancybox.core.window.WindowManager;
import fancybox.lib.animation.Animation;
import fancybox.lib.image.ImageConvert;
import fancybox.plugin.FBPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			g.drawImage(shade,0,0,this);
		}
	}
	public final static BasicStroke borderStroke=new BasicStroke(2);
	/**
	 * paint tool bar as a JPanel
	 */
	private static class ToolBar extends JPanel{
		public void paint(Graphics g){
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//绘制圆角矩形底
			g.setColor(getForeground());
			g.fillRoundRect(-20,0,this.getWidth()+15,this.getHeight()-5,20,20);
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

	//window operation buttons
	JPanel operBtnPanel;
	static BufferedImage hidebi=new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
	static BufferedImage closebi=new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
	static BasicStroke operBtnStroke=new BasicStroke(2);
	static {
		Graphics2D g=hidebi.createGraphics();
		g.setColor(Color.WHITE);
		g.setStroke(operBtnStroke);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawLine(0,hidebi.getHeight()/2-4,hidebi.getWidth(),hidebi.getHeight()/2-4);
		g.drawLine(0,hidebi.getHeight()/2-3,hidebi.getWidth(),hidebi.getHeight()/2-3);

		g=closebi.createGraphics();
		g.setStroke(operBtnStroke);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawLine(2,2,closebi.getWidth()-4,closebi.getHeight()-4);
		g.drawLine(closebi.getWidth()-4,2,2,closebi.getHeight()-4);
	}
	/**
	 * defines a oper btn
	 */
	public class WindowOperBtn extends JButton{
		BufferedImage image;
		public WindowOperBtn(BufferedImage image,int w,int h){
			this.setBackground(toolBar.getForeground());
			this.setSize(w,h);
			ImageConvert convert=new ImageConvert(image);
			this.image=convert.changeResolutionRate((double) this.getWidth()/(double) image.getWidth()).getProduct();
		}
		@Override
		public void paint(Graphics g){
			g.setColor(getBackground());
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			g.drawImage(image,0,0,this);
//			System.out.println("paintOper"+getBackground());
		}
	}
	public void setOperButtons(){
		logob=new WindowOperBtn(plugin.icon,30,30);
		logob.setLocation(operBtnPanel.getWidth()-46,10);

		hide=new WindowOperBtn(hidebi,20,20);
		hide.setLocation(logob.getX()+5,operBtnPanel.getHeight()-65);
		close=new WindowOperBtn(closebi,20,20);
		close.setLocation(logob.getX()+5,hide.getY()+hide.getHeight()+10);
	}

	WindowOperBtn logob;
	WindowOperBtn hide;
	WindowOperBtn close;
	public void initOperPanel(){
		operBtnPanel=new JPanel();
		operBtnPanel.setBackground(transparent);
		operBtnPanel.setLayout(null);
		operBtnPanel.setLocation(0,0);
		operBtnPanel.setSize(this.getWidth(),this.getHeight());

		setOperButtons();
		operBtnPanel.add(logob);
		operBtnPanel.add(close);
		close.addActionListener(e -> {
//			System.out.println("close");
			dispose();
		});
		operBtnPanel.add(hide);
		hide.addActionListener(e -> {
//			System.out.println("hide");
			setVisible(false);
		});

		super.add(operBtnPanel);
	}



	public final static Color psc[]=new Color[] {new Color(0,0,0),new Color(127,127,127)
			,new Color(136,0,21),new Color(237,28,36),new Color(255,127,39)
			,new Color(255,242,0),new Color(34,177,76),new Color(0,162,232)
			,new Color(63,72,204),new Color(136,73,164),new Color(235,235,235)
			,new Color(255,255,255)};
	public final static Color DEFAULT_WINDOW_COLOR=new Color(66, 171, 255);
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
		this.setForeground(DEFAULT_WINDOW_COLOR);
		super.setLayout(null);

		super.add(panel);
		LauncherMain.windowManager.windows.add(this);
		toolBar.setLocation(0,0);
		super.add(toolBar);
		shadeBg.setLocation(0,0);
		super.add(shadeBg);

//		initOperPanel();

		this.setOpacity(0.98f);
	}
//
	@Override
	public void paint(Graphics g){
		shadeBg.update(g);
		toolBar.update(g);
		operBtnPanel.update(g);
//		System.out.println(operBtnPanel.getBounds()+" "+operBtnPanel.getComponents().length+" "+operBtnPanel.getComponents()[0].getBounds());
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
//			operBtnPanel.setSize(w+55,h+5);
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
		if(operBtnPanel!=null){
			for(Component component:operBtnPanel.getComponents()){
				component.setBackground(fg);
			}
		}
	}
	/**
	 *
	 * @param bg
	 */
	@Override
	public void setBackground(Color bg){
		this.panel.setBackground(bg);
	}

	public final Animation showingAnim=new Animation(()->{
		this.setOpacity(0);
		super.setVisible(true);
		for(int i=0;i<=10;i++){
			this.setOpacity(1f/10f*i);
			try{Thread.sleep(5);}catch (Exception ignore){};
		}
		LauncherMain.windowList.repaint();
		LauncherMain.pluginBar.repaint();
	});
	public final Animation hidingAnim=new Animation(()->{
		for(int i=10;i>=0;i--){
			this.setOpacity(1f/10f*i);
			try{Thread.sleep(5);}catch (Exception ignore){};
		}
		super.setVisible(false);
		LauncherMain.windowList.repaint();
		LauncherMain.pluginBar.repaint();
	});
	int shiftStart=0,shiftEnd=0;
	public final Animation shiftYAnim=new Animation(()->{
		int deltax=shiftEnd-shiftStart;
		int step=deltax/Math.abs(deltax);
		for (int i=shiftStart;Math.abs(shiftEnd-i)>5;i+=step*6){
			super.setLocation(getX(),getY()+step*6);
			try{Thread.sleep(1);}catch (Exception ignore){};
		}
		super.setLocation(getX(),shiftEnd);
	});
	public void setSuperVisible(boolean b){
		super.setVisible(b);
	}
	@Override
	public void setVisible(boolean b){
		if (b){
			showFBW();
		}else {
			hideFBW();
		}
	}
	public void performShowAnim(){
		super.setLocation(LauncherMain.launcherBall.getX() - this.getWidth(),getY());
		showingAnim.perform();
	}
	public void showFBW(){
		this.updateLocation();
//		System.out.println(operBtnPanel.getBounds());
//		this.setOperButtons();
		this.initOperPanel();
//		System.out.println("updated");
		super.add(panel);
//		show();
		this.showingAnim.perform();
		this.thumb=getThumb();
	}
	public void hideFBW(){
//		System.out.println("hide");
		this.hidingAnim.perform();
		new Thread(()->{
			try {
				Thread.sleep(130);
			}catch (Exception ignored){}
			for(FBWindow window:LauncherMain.windowManager.windows){
				if(window.isVisible()&&window.getY()>this.getY()){
					window.shiftY(-(this.getHeight()+WindowManager.WINDOW_DISTANCE));
				}
			}
		}).start();
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
				super.setLocation(LauncherMain.launcherBall.getX() - this.getWidth()
						, WindowManager.TOP_DISTANCE + usedHeight);
			} else {
				//移动之下的窗口
				for (FBWindow window : LauncherMain.windowManager.windows) {
					if (window.isVisible()) {
						window.shiftY( this.getHeight() + WindowManager.WINDOW_DISTANCE);
						//验证此窗口是否超出范围
						if (window.getY()+window.getHeight()+WindowManager.WINDOW_DISTANCE>screenHeight-WindowManager.BOTTOM_DISTANCE){
							window.setVisible(false);
						}
					}
				}
				super.setLocation(LauncherMain.launcherBall.getX() - this.getWidth(),WindowManager.TOP_DISTANCE);
			}
		}
	}
	public void shiftY(int height){
//		super.setLocation(this.getX(),this.getY()+height);
		this.shiftStart=getY();
		this.shiftEnd=getY()+height;
		shiftYAnim.perform();
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
	Animation disposeAnim=new Animation(()->{
		hideFBW();
		try{Thread.sleep(200);}catch (Exception ignored){}
		LauncherMain.windowManager.windows.remove(this);
		LauncherMain.pluginBar.repaint();
		super.dispose();
		if(LauncherMain.windowList.isVisible()){
			LauncherMain.windowList.reload();
		}
		if (this.exitOnClose){
			plugin.stop();
		}
		LauncherMain.pluginBar.repaint();
	});
	@Override
	public void dispose(){
		disposeAnim.perform();
	}
	public JPanel getPanel(){
		return panel;
	}
}
