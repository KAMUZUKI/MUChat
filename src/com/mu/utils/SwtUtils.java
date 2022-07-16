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
 * ���ڹ�����
 * @author MUZUKI
 *
 */
public class SwtUtils {
	/**
	 * ��ɴ��ھ���
	 * 
	 * @param display
	 * @param shell
	 */

	public static void centerShell(Display display, Shell shell) {
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds(); // ��ʾ����Ļ��С
		Rectangle shellBounds = shell.getBounds(); // ���ڴ�С
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);
		
		
	}
	
	/**
	 * ��Ϣ���ڶ���
	 * 
	 * @param shell
	 */
	public static void dithering(JFrame frame) {
		final Point location = frame.getLocation(); // ��¼�ʼʱshell��λ��
		final int amplitude = 6; // �����ķ���
		final long _times = 1 * 1000; // ������ʱ��
		final long startTime = System.currentTimeMillis();
		int rx =location.x;
        int ry =location.y;
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //������Ч��
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
