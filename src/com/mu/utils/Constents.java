package com.mu.utils;

import java.applet.AudioClip;

import com.mu.client.chat_room.backstage.ClienManage;
import com.mu.common.User;
import com.mu.server.chat_server.view.Server_Frame;

/**
 * 静态存储对象
 * @author MUZUKI
 *
 */
public class Constents {
	public static String uname = "";
	
	public static String password = "";
	
	public static User user = new User();
	
	public static ClienManage cm; //后台处理对象
	
	public static String online;
	
	public static AudioClip audioClip;
	
}
