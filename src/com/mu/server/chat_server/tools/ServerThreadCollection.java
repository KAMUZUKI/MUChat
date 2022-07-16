package com.mu.server.chat_server.tools;

import java.util.HashMap;
import java.util.Iterator;

import com.mu.common.User;
import com.mu.server.chat_server.backstage.ServerConClient;
import com.mu.server.chat_server.backstage.Server_Connect_Database;

/**
 * ����������ͻ���ͨ�ŵ��̼߳�����
 * @author Administrator
 *	Server_Connect_Client_Thread_Collection
 */
public class ServerThreadCollection {

	private static HashMap<String, ServerConClient> hm = new HashMap<String, ServerConClient>();
	
	/**
	 * ��ӷ���������ͻ���ͨ�ŵ��̼߳��ϵķ���
	 * @param Name �û���
	 * @param serverConClient ����������ͻ���ͨ�ŵ��߳�
	 */
	public static void addServerConnectClientThreadCollection(String Name, ServerConClient serverConClient){
		hm.put(Name, serverConClient);
	}
	
	/**
	 * �����û������ط���������ͻ���ͨ�ŵ��̵߳ķ���
	 * @param Name �û���
	 * @return ���ط���������ͻ���ͨ�ŵ��߳�
	 */
	public static ServerConClient getServerContinueConnetClient(String Name){
		return hm.get(Name);
	}
	
	/**
	 * �����û����Ƴ�����������ͻ���ͨ�ŵ��̵߳ķ���
	 * ͬʱ��������Ϊû�е�½��״̬
	 * @param Name
	 */
	public static void RemoveServerContinueConnetClient(String Name){
		Server_Connect_Database connect_Database = new Server_Connect_Database();
		User u = new User();
		u.setName(Name);
		//����û�е�½��״̬
		connect_Database.Update_IsLogin(u, 0);
		hm.remove(Name);
	}
	
	/**
	 * �������Ϸ��������û�
	 * @return ���������û����ַ���
	 */
	public static String GetOnline(){
		String Online = "";
		Iterator<String> it  = hm.keySet().iterator();
		while(it.hasNext()){
			Online += it.next().toString()+" ";
		}
		return Online;
	}
}
