package fancybox.lib.entry;

import fancybox.core.bar.PluginBar;
import fancybox.lib.image.ImageConvert;

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

	public EntryOnBar(BufferedImage icon, String name){
		this.icon=icon;
		this.name=name;
		init();
	}
	public EntryOnBar(BufferedImage icon, String name, String description){
		this.icon=icon;
		this.name=name;
		this.description=description;
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
	}

	/**
	 * override paint method to render a entry
	 * transparent bg and icon in center
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g){

		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(icon,0,0,this);
	}
}
