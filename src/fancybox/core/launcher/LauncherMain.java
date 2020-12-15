package fancybox.core.launcher;

import fancybox.core.bar.PluginBar;
import fancybox.lib.io.Out;

/**
 * bootstrap of FancyBox launcher
 * this class is to launch LauncherBall and other daemon services.
 * @author Rock Chin
 * @create 2020/12/14
 */
public class LauncherMain {
	public static LauncherBall launcherBall;
	public static PluginBar pluginBar;
	public static void main(String[] args) {
		Out.stdPrintln("Main","launching.");
		launcherBall=new LauncherBall(70,70);
		pluginBar=new PluginBar();
	}
}
