package fancybox.lib.entry;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.ui.FBWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EntryListener implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
		ArrayList<FBWindow> pluginWindows=new ArrayList<>();
		EntryOnBar source=((EntryOnBar)e.getSource());
		//查找由此plugin创建的window
		for(FBWindow window: LauncherMain.windowManager.windows){
			if (window.plugin==source.plugin) {
				pluginWindows.add(window);
			}
		}
		if(pluginWindows.size()==0){
			source.plugin.main();
		}else if (pluginWindows.size()==1){//如果只有一个窗口则切换此窗口的可见性
			pluginWindows.get(0).setVisible(!pluginWindows.get(0).isVisible());
			//TODO 显示新建按钮
		}else {//有多个窗口则打开窗口列表供选择

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
