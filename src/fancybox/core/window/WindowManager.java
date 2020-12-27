package fancybox.core.window;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.animation.Animation;
import fancybox.lib.ui.FBWindow;

import java.util.ArrayList;

/**
 * manager of all windows,locate window and manager relationship between them.
 * 管理所有的窗口，确定每个窗口的位置以及管理他们之间的位置关系
 * @author Rock Chin
 * @create 2020/12/17
 */
public class WindowManager {
	//store all windows
	public ArrayList<FBWindow> windows=new ArrayList<>();
	public final static int WINDOW_DISTANCE=10;
	public final static int TOP_DISTANCE=100,BOTTOM_DISTANCE=100;

	static ArrayList<FBWindow> showingWindows=new ArrayList<>();
	public Animation hideAll=new Animation(()->{
		showingWindows.clear();
		for (FBWindow window:windows){
			if (window.isVisible()){
				showingWindows.add(window);
			}
		}
		for (int i=10;i>=0;i--){
			for (FBWindow window:windows){
				if (window.isVisible()) {
					window.setOpacity(1f / 10f * i);
				}
			}
			try{Thread.sleep(5);}catch (Exception ignore){};
		}
		for (FBWindow window:windows){
			window.setSuperVisible(false);
		}
		LauncherMain.windowList.repaint();
		LauncherMain.pluginBar.repaint();
	});
	public Animation showAll=new Animation(()->{
//		showingWindows.forEach(FBWindow::performShowAnim);
		for (FBWindow window:windows){
			if (showingWindows.contains(window)) {
				window.setOpacity(0);
				window.setSuperVisible(true);
			}
		}
		for(int i=0;i<=10;i++){
			for (FBWindow window:windows) {
				if (window.isVisible()) {
					window.setOpacity(1f / 10f * i);
				}
			}
			try {
				Thread.sleep(5);
			} catch (Exception ignore) {
			}
		}
		LauncherMain.windowList.repaint();
		LauncherMain.pluginBar.repaint();
	});

}
