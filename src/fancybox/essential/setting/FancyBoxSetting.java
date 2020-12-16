package fancybox.essential.setting;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.entry.EntryOnBar;
import fancybox.plugin.FBPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FancyBoxSetting extends FBPlugin {
	BufferedImage setting;

	{
		try {
			setting = ImageIO.read(new File("res/FancyBox/setting.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		super.register(setting,"FBSetting","setting");
		EntryOnBar entry=new EntryOnBar(setting,"FBSetting");
		entry.addActionListener((e)->{
			System.exit(0);
		});
		LauncherMain.pluginBar.registerEntry(entry);
	}

	@Override
	public void main() {
	}

	@Override
	public void stop() {

	}
}
