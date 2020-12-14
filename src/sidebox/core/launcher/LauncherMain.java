package sidebox.core.launcher;

import sidebox.lib.io.Out;

/**
 * bootstrap of SideBox launcher
 * this class is to launch LauncherBall and other daemon services.
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class LauncherMain {
	public static LauncherBall launcherBall;
	public static void main(String[] args) {
		Out.stdPrintln("Main","launching.");
		launcherBall=new LauncherBall(60,60);
	}
}
