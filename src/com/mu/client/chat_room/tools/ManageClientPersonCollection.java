package com.mu.client.chat_room.tools;

import java.util.HashMap;
import java.util.Set;

import com.mu.client.chat_room.view.ClientFrame;

/**
 * ��������������ļ���
 * @author Administrator
 *
 */
public class ManageClientPersonCollection {
	private static HashMap<String, ClientFrame> hm = new HashMap<String, ClientFrame>();
	
	/**
	 * �����ַ����ҵ���Ӧ�ĸ����������
	 * @param str ����Sender Getter
	 * @param cf �����������
	 */
	public static  void addClientPersonCollection(String str,ClientFrame cf){
		hm.put(str, cf);
	}
	
	/**
	 * �����ַ������ض�Ӧ���������
	 * @param str ����Sender Getter
	 * @return ��Ӧ���������
	 */
	public static ClientFrame getClientPerson(String str){
		return hm.get(str);
	}
	
	/**
	 * �����ַ����鿴��Ӧ����������Ƿ��Ѿ�����
	 * @param str
	 * @return
	 */
	public static boolean isExist(String str){
		boolean b = false;
		if(hm.get(str)!=null){
			b = true;
		}
		return b;
	}
	
	/**
	 * �����ַ����Ƴ���Ӧ���������
	 * @param str
	 */
	public static void removeClientPerson(String str){
		hm.remove(str);
	}
	
	/**
	 * �����������ַ����ļ���
	 * @return	����
	 */
	public static Set<String> getClientPersonSet(){
		return hm.keySet();
	}
}
