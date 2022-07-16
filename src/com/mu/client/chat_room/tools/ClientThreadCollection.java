package com.mu.client.chat_room.tools;

import java.util.HashMap;

import com.mu.client.chat_room.backstage.ClientConServer;


/**
 * �ͻ�����������˼���ͨ�ŵ��̼߳�����
 * @author Administrator
 * Client_Connect_Server_Thread_Collection
 */
public class ClientThreadCollection {
	private	static HashMap<String, ClientConServer> hm = new HashMap<String, ClientConServer>();
	
	/**
	 * ��ӿͻ�����������˼���ͨ�ŵ��̵߳ķ���
	 * @param Name �û���
	 * @param clientConServer �ͻ�����������˼���ͨ�ŵ��߳�
	 */
	public static void addClientThreadCollection(String Name,ClientConServer clientConServer ){
		hm.put(Name, clientConServer);
	}
	
	/**
	 * �����û������ؿͻ�����������˼���ͨ�ŵ��̵߳ķ���
	 * @param Name �û���
	 * @return	���ؿͻ�����������˼���ͨ�ŵ��߳�
	 */
	public static ClientConServer getClientThreadCollection(String Name){
		return hm.get(Name);
	}
}
