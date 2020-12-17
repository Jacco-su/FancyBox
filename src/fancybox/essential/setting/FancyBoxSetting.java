package fancybox.essential.setting;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.entry.EntryOnBar;
import fancybox.lib.ui.FBWindow;
import fancybox.plugin.FBPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FancyBoxSetting extends FBPlugin {
	static BufferedImage setting;
	static {
		try {
			setting = ImageIO.read(new File("res/FancyBox/setting.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		super.setInfo(setting,"FBSetting","setting");
		EntryOnBar entry=new EntryOnBar(this);
		LauncherMain.pluginBar.registerEntry(entry);
	}
	private FBWindow settingMain=new FBWindow(this);
	@Override
	public void main() {
//		System.exit(0);
		settingMain=new FBWindow(this);
		settingMain.setVisible(true);
	}

	@Override
	public void stop() {

	}
}
