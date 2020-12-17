package fancybox.core.loader;

import fancybox.essential.setting.FancyBoxSetting;
import fancybox.plugin.FBPlugin;

/**
 * load plugins from jar or internal classes
 * @author Rock Chin
 * @create 2020/12/16
 */
public class PluginLoader{
	private FBPlugin setting=new FancyBoxSetting();
	public void loadInternalPlugin(){
		setting.init();
	}
}
