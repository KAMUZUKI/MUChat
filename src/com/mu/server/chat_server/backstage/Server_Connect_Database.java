package com.mu.server.chat_server.backstage;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mu.common.User;

/**
 * �������������ݿ�ͨ�ŵĺ�̨��
 * @author Administrator
 *
 */
public class Server_Connect_Database {
	
	private ResultSet rs = null;
	private DatabaseManage databaseManage = null;
	
	/**
	 * ����Ƿ��½�ɹ��ķ���
	 * @param user �û�����
	 * @return �����Ƿ��½�ɹ�
	 */
	public boolean CheckLogin(User user){
		boolean b = false;
		databaseManage = new DatabaseManage();
		rs = databaseManage.GetUser(user.getName());
		try {
			rs.next();
			String Password = rs.getString(2);
			if(Password.equals(user.getPassWords())){
				b =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}finally{
			databaseManage.close();
		}
		return b;
	}
	
	
	/**
	 * ����Ƿ��ظ���½�ķ���
	 * @param user �û�����
	 * @return �Ƿ��ظ���½,��½���ķ���true,���򷵻�false
	 */
	public boolean Check_IsLogin(User user){
		boolean b = false;
		databaseManage = new DatabaseManage();
		rs = databaseManage.GetUser(user.getName());
		try {
			rs.next();
			int IsLogin = rs.getInt(3);
			if(IsLogin==0){
				b =false;
			}else if(IsLogin==1){
				b = true;
			}
		} catch (SQLException e) {
			//�����ûע������׳�SQLException
			//������������Ϊû�е�½����������֤�Ƿ��½�ɹ�
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}finally{
			databaseManage.close();
		}
		return b;
	}
	
	
	/**
	 * ���سɹ��޸ĵ�½���
	 * @param user �û�����
	 */
	public boolean  Update_IsLogin(User user,int isLogin){
		databaseManage = new DatabaseManage();
		return databaseManage.Update_IsLogin(user,isLogin);
	}
	
}
