package fancybox.plugin;

import java.awt.image.BufferedImage;

/**
 * describe a FancyBox Plugin which has name,icon and action to do.
 * @author Rock Chin
 * @create 2020/12/15
 */
public abstract class FBPlugin {
	//TODO set default value
	public BufferedImage icon;
	public String name;
	public String description;
	public int maxInstanceAmount=-1;

	public int getMaxInstanceAmount() {
		return maxInstanceAmount;
	}

	public void setMaxInstanceAmount(int maxInstanceAmount) {
		this.maxInstanceAmount = maxInstanceAmount;
	}

	/**
	 * use this method to set information of this plugin.
	 * calling this is unnecessary but these fields has default value.
	 * @param icon icon of this plugin
	 * @param name name of this plugin
	 * @param description description of this plugin
	 */
	public void setInfo(BufferedImage icon,String name,String description){
		this.icon=icon;
		this.name=name;
		this.description=description;
	}

	/**
	 * call when FancyBox is launching.
	 */
	public abstract void init();

	/**
	 * call when the entryOnBar of this plugin is on clicked.
	 * if this plugin did not register any entry,this method may never be called.
	 * in after version,this method may be called in more other ways.
	 */
	public abstract void main();

	/**
	 * call when the window of this plugin is closing.
	 * if this plugin did not register any window,this method may never be called.
	 * in after version,this method may be called in more other ways.
	 */
	public abstract void stop();
}
