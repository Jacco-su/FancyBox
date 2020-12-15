package fancybox.lib.animation;

/**
 * a animation model with a proxyThread for perform this animation
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public abstract class AbstractAnimation implements Runnable{
	public void perform(){
		Thread proxyThr = new Thread(this);
		proxyThr.start();
	}
}
