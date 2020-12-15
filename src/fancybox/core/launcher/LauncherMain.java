package fancybox.core.launcher;

import fancybox.core.bar.PluginBar;
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
	public static void main(String[] args) throws Exception{
		Out.stdPrintln("CountMain","launching.");
		pluginBar=new PluginBar();
		launcherBall=new LauncherBall(70,70);
		//test
		EntryOnBar test=new EntryOnBar(ImageIO.read(
				new FileInputStream("E:\\10105\\Pictures\\桂中校徽.png")),"test");
		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pluginBar.registerEntry(test);
		//test2
		EntryOnBar test2=new EntryOnBar(ImageIO.read(
				new FileInputStream("E:\\10105\\Pictures\\桂中校徽.png")),"test");
		test2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pluginBar.registerEntry(test2);
		//test3
		EntryOnBar test3=new EntryOnBar(ImageIO.read(
				new FileInputStream("E:\\10105\\Pictures\\桂中校徽.png")),"test");
		test3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pluginBar.registerEntry(test3);
	}
}
