package fancybox.core.bar;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.animation.Animation;
import fancybox.lib.entry.EntryOnBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * a bar storing all plugin and provides entries for all plugin.
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class PluginBar extends JWindow {
	public final static int ENTRY_WIDTH_HEIGHT=60;
	//animation to perform when bar opening
	public final static Animation barOpening=new Animation(()->{
//		PluginBar bar=LauncherMain.pluginBar;
//		bar.setSize(bar.getWidth(),0);
		//设置所有的entry为不可见
//		for(EntryOnBar entry:LauncherMain.pluginBar.entriesOnBar){
//			entry.setVisible(false);
//		}
//		//挨个显示
//		for(EntryOnBar entry:LauncherMain.pluginBar.entriesOnBar){
//			entry.setOpaque(true);
//			entry.setVisible(true);
//		}
		for (int i=0;i<10;i++){
			LauncherMain.pluginBar.setOpacity((float) (i*0.1));
			try{
				Thread.sleep(13);
			}catch (Exception e){}
		}
	});
	//animation to perform when bar hiding
	public final static Animation barHiding=new Animation(()->{

	});
	//stored all entries of plugins
	ArrayList<EntryOnBar> entriesOnBar=new ArrayList<>();
	public void registerEntry(EntryOnBar entryOnBar){
		entriesOnBar.add(entryOnBar);
		//set size and location
		this.setSize(PluginBar.ENTRY_WIDTH_HEIGHT,(PluginBar.ENTRY_WIDTH_HEIGHT+15)*entriesOnBar.size());
		entryOnBar.setLocation(0,(PluginBar.ENTRY_WIDTH_HEIGHT+15)*(entriesOnBar.size()-1));
		this.add(entryOnBar);
	}

	/**
	 * init plugin bar
	 */
	public PluginBar(){
		this.setAlwaysOnTop(true);
		this.setLayout(null);
		this.setBackground(new Color(0,0,0,0));
//		showBar();
	}
	public void showBar(){
		this.setLocation(LauncherMain.launcherBall.getWidth()/2-this.getWidth()/2+LauncherMain.launcherBall.getX()
				,LauncherMain.launcherBall.getY()-this.getHeight());
		// perform bar open animation
		this.setOpacity(0.0f);
		this.setVisible(true);
		barOpening.perform();
	}
	public void hideBar(){

	}
	public void registerFBEntry(){

	}
}
