package fancybox.lib.ui;

import fancybox.core.launcher.LauncherMain;
import fancybox.core.window.WindowManager;
import fancybox.plugin.FBPlugin;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a specific implement of Button for FancyBox platform.
 * Use this button to make a unified style.
 * @author Rock Chin
 * @create 2021/01/13
 */
public class FBButton extends JButton {
    private final static Color halfTrans=new Color(0,0,0,100)
            ,defaultBg=new Color(0, 135, 203);
    protected FBPlugin plugin;
    public FBButton(FBPlugin plugin,Icon icon){
        super(icon);
        init(plugin);
    }
    public FBButton(FBPlugin plugin,String text){
        super(text);
        init(plugin);
    }
    public FBButton(FBPlugin plugin,String text,Icon icon){
        super(text, icon);
        init(plugin);
    }
    private void init(FBPlugin plugin){
        this.plugin=plugin;
        Color bg=LauncherMain.windowManager.indexFirstFBWindowByPlugin(plugin).getForeground();
        this.setBackground(bg!=null?bg:defaultBg);
        this.removeMouseListener(getMouseListeners()[0]);
    }
    /**
     * paint the graph of this button
     * @param g
     */
    public void paint(Graphics g){
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(halfTrans);
        g.fillRoundRect(5,5,this.getWidth()-5,this.getHeight()-5,10,10);
        g.setColor(getBackground());
        g.fillRoundRect(0,0,this.getWidth()-2,this.getHeight()-2,10,10);


        //计算图标和文字总长，然后置于正中，图标和文字间隔3pixel

        //图标不是null则加上图标宽度和3像素
        int contextWidth=this.getIcon()==null?0:this.getIcon().getIconWidth()+3;
        //以本btn字体初始化fontmetrics
        FontMetrics fontMetrics=g.getFontMetrics(this.getFont());

        contextWidth+=fontMetrics.stringWidth(this.getText());
        //绘制起点x
        int drawIcon_x=this.getWidth()/2-contextWidth/2;
        int drawText_x=this.getIcon()==null?this.getWidth()/2-fontMetrics.stringWidth(this.getText())/2
                :drawIcon_x+this.getIcon().getIconWidth()+5;

        //分别计算icon和text的绘制起点y
        int drawIcon_y=this.getHeight()/2-(this.getIcon()==null?0:this.getIcon().getIconHeight()/2);
        int drawText_y=this.getHeight()/2+(int)(fontMetrics.getHeight()*0.26);

        //绘制icon和text
        g.setFont(this.getFont());
        g.setColor(this.getForeground());
        if(this.getIcon()!=null){
            this.getIcon().paintIcon(this,g,drawIcon_x,drawIcon_y);
        }
//		g.drawImage((Image) this.getIcon(),drawIcon_x,drawIcon_y,this);
        g.drawString(this.getText(),drawText_x,drawText_y);
    }
}
