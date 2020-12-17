package fancybox.lib.ui;

import fancybox.core.bar.PluginBar;
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
		//检查图片是否需要缩放
		if(icon.getWidth()> FBWindow.ICON_WIDTH_HEIGHT||icon.getHeight()>FBWindow.ICON_WIDTH_HEIGHT) {
			ImageConvert imageConvert = new ImageConvert(icon);
			imageConvert.changeResolutionRate(FBWindow.ICON_WIDTH_HEIGHT / (double) icon.getWidth());
			this.icon = imageConvert.getProduct();
		}
		panel.setLocation(0,0);
		panel.setLayout(null);
		panel.setBackground(Color.white);
		this.setBackground(transparent);
		this.setLayout(null);
		this.add(panel);
	}

	@Override
	public void paint(Graphics g){
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//绘制圆角矩形底
		g.setColor(getForeground());
		g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),10,10);
		//绘制panel的控件
		super.paint(g);
	}
	@Override
	public Component add(Component component){
		panel.add(component);
		return component;
	}
	@Override
	public void setSize(int w,int h){
		panel.setSize(w,h);addNotify();
		super.setSize(w+45,h);
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
	private boolean visible=false;

	/**
	 * 覆盖父类方法以确保返回正确可见状态
	 * @return
	 */
	@Override
	public boolean isVisible(){
		return visible;
	}

	/**
	 * 覆盖父类方法以确保正确使用FancyBox定义的方法来显示此window
	 * @param b
	 */
	@Override
	public void setVisible(boolean b){
		if (b){
			show();
		}else {
			hide();
		}
	}
	@Override
	public void show(){

	}
	@Override
	public void hide(){

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
