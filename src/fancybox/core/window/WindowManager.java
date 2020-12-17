package fancybox.core.window;

import fancybox.lib.ui.FBWindow;

import java.awt.*;
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


}
