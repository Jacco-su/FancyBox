package fancybox.core.bar;

import fancybox.core.launcher.LauncherMain;
import fancybox.lib.animation.Animation;
import fancybox.lib.entry.EntryOnBar;
import fancybox.lib.image.ImageConvert;
import fancybox.lib.ui.FBWindow;
import fancybox.plugin.FBPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Defines a window list JWindow display when a entry of a plugin which has multi-window,
 * allow user to select a window to switch its visible state.
 * @author Rock Chin
 * @create 2020/12/25
 * Merry Christmas!
 */
public class WindowList extends JWindow {
	/**
	 * a WindowEntry that extends from JButton and added to WindowList.
	 * Automatically create when WindowList is being showed.
	 */
	private static class WindowEntry extends JButton{
		private BufferedImage image;
		private FBWindow window;
		/**
		 * create a new WindowEntry
		 * @param img provide a BufferedImage to size this entry and set image to paint on it.
		 */
		WindowEntry(BufferedImage img, FBWindow window){
			this.window=window;
			init(img,()->{
				window.setVisible(!window.isVisible());
//				LauncherMain.windowList.repaint();
			});
		}
		WindowEntry(BufferedImage img, Runnable action){
			//				System.out.println("action");
			init(img, action);
		}
		private void init(BufferedImage img,Runnable clickAction){
			this.image=img;
			this.setSize(img.getWidth()+6,img.getHeight()+6);
			this.removeMouseListener(this.getMouseListeners()[0]);
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					clickAction.run();
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
			});
		}
		@Override
		public void paint(Graphics g){
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//			System.out.println((window==null)+" "+(window==null?"":window.isVisible()));
			if (window!=null) {
				g.setColor(window.isVisible() ? EntryOnBar.showingWindow : EntryOnBar.windowLabel);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
			}
			g.drawImage(image,3,3,this);
			if(window!=null&&!window.isVisible()){
				g.setColor(halfTransparentPlus);
				g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),10,10);
			}
		}
	}
	private final static Color transparent=new Color(0,0,0,0);
	private final static Color halfTransparent=new Color(0,0,0,20);
	private final static Color halfTransparentPlus=new Color(0,0,0,120);
	public FBPlugin plugin;
	private EntryOnBar entryOnBar;
	WindowEntry add;

	public final Animation showingAnim=new Animation(()->{
		calcLocation(entryOnBar);
		Point endLocation=this.getLocation();
		this.setOpacity(0f);
		this.setVisible(true);
		for(int i=1;i<=30;i++){
			this.setOpacity((float) (1.0/30.0)*i);
			this.setLocation( endLocation.x-30+(30/30)*i,endLocation.y );
			try {
				Thread.sleep(1);
			}catch (Exception ignored){}
		}
	});
	public final Animation hidingAnim=new Animation(()->{
		for(int i=10;i>=0;i--){
			this.setOpacity((float) ((1.0/10)*i));
			this.setLocation(getX()-2,getY());
			try {
				Thread.sleep(5);
			}catch (Exception ignored){}
		}
		this.setVisible(false);
	});
	/**
	 * create WindowList with width provided
	 */
	public WindowList(){
		this.setAlwaysOnTop(true);
		this.setLayout(null);
		this.setVisible(false);
		this.setBackground(transparent);

		add=new WindowEntry(addbtn, () -> {
			plugin.main();
//			this.setVisible(false);
			this.reload();
		});
		this.add(add);
	}
	private static final int WINDOW_ENTRY_HEIGHT=30,WINDOW_ENTRY_DISTANCE=15;
	static BufferedImage addbtn=new BufferedImage(WINDOW_ENTRY_HEIGHT+25,WINDOW_ENTRY_HEIGHT+6,BufferedImage.TYPE_INT_ARGB);
	private static final BasicStroke addStroke=new BasicStroke(4);
	static {
		Graphics2D g=addbtn.createGraphics();
		g.setStroke(addStroke);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.lightGray);
		g.drawRoundRect(1,1,addbtn.getWidth()-3, addbtn.getHeight()-3,10,10);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(halfTransparent);
		g.fillRect(0,0,addbtn.getWidth(),addbtn.getHeight());
		g.setColor(Color.WHITE);
		g.drawLine(15,addbtn.getHeight()/2,addbtn.getWidth()-15,addbtn.getHeight()/2);
		g.drawLine(addbtn.getWidth()/2,9,addbtn.getWidth()/2,addbtn.getHeight()-9);
	}
	public void reload(){
		showWindowEntries(plugin,entryOnBar,false);
	}
	private ArrayList<Component> lsComps=new ArrayList<>();
	private ArrayList<Component> currentComps=new ArrayList<>();
	/**
	 * show this Window to display WindowEntries for specific plugin
	 * @param plugin specific plugin
	 */
	public void showWindowEntries(FBPlugin plugin,EntryOnBar pluginEntry,boolean showAnim){
//		System.out.print(lsComps.size()+","+this.getContentPane().getComponents().length);
		this.plugin=plugin;
		this.entryOnBar=pluginEntry;

		int index=0,maxX=addbtn.getWidth()+10;
		for(FBWindow win: LauncherMain.windowManager.windows){
			if(win.plugin==plugin){
				WindowEntry entry=new WindowEntry(new ImageConvert(win.thumb)
						.changeResolutionRate((double)WINDOW_ENTRY_HEIGHT/(double) win.thumb.getHeight())
						.getProduct(),win);
				entry.setLocation(0,(index+1)*(WINDOW_ENTRY_HEIGHT+WINDOW_ENTRY_DISTANCE)+10);
				this.add(entry);
				currentComps.add(entry);
				maxX=Math.max(maxX,entry.getWidth());
				index++;
			}
		}
		for (Component comp:lsComps) {
			this.remove(comp);
		}
		lsComps.clear();
		lsComps.addAll(currentComps);
		currentComps.clear();

		this.setSize(maxX,(index+1)*(WINDOW_ENTRY_HEIGHT+WINDOW_ENTRY_DISTANCE)+10);
//		add.setLocation(this.getWidth()/2-add.getWidth()/2,0);
		add.setLocation(3,0);
//		System.out.println(this.getBounds()+" \n   "+add.getBounds());
//		this.setVisible(true);
		if(showAnim) {
			showingAnim.perform();
		}else{
			calcLocation(pluginEntry);
			this.setVisible(true);
		}

//		System.out.println(" "+lsComps.size()+","+this.getContentPane().getComponents().length);
	}
	public void hideWindowList(){
		hidingAnim.perform();
	}
	public void calcLocation(EntryOnBar pluginEntry){

		this.setLocation(LauncherMain.launcherBall.getX()+LauncherMain.launcherBall.getWidth()+15
				,pluginEntry.getY()+LauncherMain.pluginBar.getY());
	}
}
