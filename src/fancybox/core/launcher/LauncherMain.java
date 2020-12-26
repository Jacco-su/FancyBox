package fancybox.core.launcher;

import fancybox.core.bar.PluginBar;
import fancybox.core.bar.WindowList;
import fancybox.core.loader.PluginLoader;
import fancybox.core.window.WindowManager;
import fancybox.lib.entry.EntryOnBar;
import fancybox.lib.io.Out;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

/**
 * bootstrap of FancyBox launcher
 * this class is to launch LauncherBall and other daemon services.
 * @author Rock Chin
 * @create 2020/12/14
 */
public class LauncherMain {
	public static LauncherBall launcherBall;
	public static PluginBar pluginBar;
	public static PluginLoader pluginLoader;
	public static WindowManager windowManager;
	public static WindowList windowList;
	public static void main(String[] args) throws Exception{
		Out.stdPrintln("CountMain","launching.");
		pluginBar=new PluginBar();
		launcherBall=new LauncherBall(70,70);
		pluginLoader=new PluginLoader();
		pluginLoader.loadInternalPlugin();
		windowManager=new WindowManager();
		windowList=new WindowList();
	}
}
