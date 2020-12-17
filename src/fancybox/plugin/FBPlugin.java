package fancybox.plugin;

import java.awt.image.BufferedImage;

/**
 * describe a FancyBox Plugin which has name,icon and action to do.
 * @author Rock Chin
 * @create 2020/12/15
 */
public abstract class FBPlugin {
	public BufferedImage icon;
	public String name;
	public String description;
	public void setInfo(BufferedImage icon,String name,String description){
		this.icon=icon;
		this.name=name;
		this.description=description;
	}
	public abstract void init();
	public abstract void main();
	public abstract void stop();
}
