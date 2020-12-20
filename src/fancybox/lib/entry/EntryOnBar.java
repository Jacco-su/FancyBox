package fancybox.lib.entry;

import fancybox.core.bar.PluginBar;
import fancybox.lib.image.ImageConvert;
import fancybox.plugin.FBPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * universal entry including icon of entry and action to perform while call
 * @author Rock Chin
 * @create 2020/12/15
 * @edit 2020/12/15
 */
public class EntryOnBar extends JButton{
	BufferedImage icon;
	String name="noName";
	String description="noDescription";


	public FBPlugin plugin;
	final static EntryListener entryListener=new EntryListener();
	public EntryOnBar(FBPlugin plugin){
		this.icon=plugin.icon.getSubimage(0,0,plugin.icon.getWidth(),plugin.icon.getHeight());
		this.name=plugin.name;
		this.description=plugin.description;
		this.plugin=plugin;
//		System.out.println(getMouseListeners().length);
		removeMouseListener(getMouseListeners()[0]);
		init();
	}
	final static Color transparent=new Color(0,0,0,0);
	private void init(){
		this.setBackground(transparent);
		this.setSize(PluginBar.ENTRY_WIDTH_HEIGHT,PluginBar.ENTRY_WIDTH_HEIGHT);
			//调整图像大小为40*40
		if(icon.getWidth()>PluginBar.ENTRY_WIDTH_HEIGHT||icon.getHeight()>PluginBar.ENTRY_WIDTH_HEIGHT) {
				ImageConvert imageConvert = new ImageConvert(icon);
			imageConvert.changeResolutionRate(PluginBar.ENTRY_WIDTH_HEIGHT / (double) icon.getWidth());
			this.icon = imageConvert.getProduct();
		}
		//设置监听器
		this.addMouseListener(entryListener);
		//绘制背景版
		Graphics ig=bgImage.getGraphics();
		((Graphics2D)ig).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		//绘制点击层
		ig.setColor(halfTransparent);
		ig.fillRect(0,0,this.getWidth(),this.getHeight());
		ig.setColor(lightParentGray);
		int w=icon.getWidth(),h=icon.getHeight();
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				if(((icon.getRGB(i,j)>>24) & 0xff)>100){
					ig.drawLine(i,j,i,j);
				}
			}
		}
	}
	private final static Color lightParentGray=new Color(20,20,20,100);
	private final static Color halfTransparent=new Color(0,0,0,1);
	private BufferedImage bgImage=new BufferedImage(PluginBar.ENTRY_WIDTH_HEIGHT
			,PluginBar.ENTRY_WIDTH_HEIGHT,BufferedImage.TYPE_INT_ARGB);
	/**
	 * override paint method to render a entry
	 * transparent bg and icon in center
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g){
//		g.setColor(transparent);
//		g.fillRect(0,0,this.getWidth(),this.getHeight());

		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		//绘制底层
		g.drawImage(bgImage,2,2,this);
		//绘制图标
		g.drawImage(icon,0,0,this);
	}
}
