package fancybox.lib.animation;

/**
 * a animation model with a proxyThread for perform this animation
 * @author Rock Chin
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class Animation{
	private Runnable runnable;
	public void perform(){
		Thread proxyThr = new Thread(this.runnable);
		proxyThr.start();
	}
	public Animation(Runnable runnable){
		this.runnable=runnable;
	}
}
