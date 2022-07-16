package com.mu.utils;

import java.awt.Point;

import javax.swing.JFrame;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 窗口工具类
 * @author MUZUKI
 *
 */
public class SwtUtils {
	/**
	 * 完成窗口居中
	 * 
	 * @param display
	 * @param shell
	 */

	public static void centerShell(Display display, Shell shell) {
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds(); // 显示的屏幕大小
		Rectangle shellBounds = shell.getBounds(); // 窗口大小
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);
		
		
	}
	
	/**
	 * 信息窗口抖动
	 * 
	 * @param shell
	 */
	public static void dithering(JFrame frame) {
		final Point location = frame.getLocation(); // 记录最开始时shell的位置
		final int amplitude = 6; // 抖动的幅度
		final long _times = 1 * 1000; // 抖动的时间
		final long startTime = System.currentTimeMillis();
		int rx =location.x;
        int ry =location.y;
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //设置震动效果
                frame.setLocation(rx+10, ry-10);
                frame.setLocation(rx, ry);
                frame.setLocation(rx-10, ry+10);
                frame.setLocation(rx, ry);
                System.out.println(rx + "  " + ry);
                if (System.currentTimeMillis() - startTime > _times) {
					timer.cancel();
				}
            }
		}, new Date(), 100);
        
	}
}
