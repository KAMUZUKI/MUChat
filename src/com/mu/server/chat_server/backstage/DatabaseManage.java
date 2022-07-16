package com.mu.server.chat_server.backstage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mu.common.User;
import com.mu.dao.DBHelper;
import com.mu.dao.DbProperties;

/**
 * ��������̨�����ݿ⴦����
 * @author Administrator
 *
 */
public class DatabaseManage {
	//�����������ݿ�����Ҫ�Ķ���
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection ct = null;
	
	public void init(){
		//��������
		DBHelper db=new DBHelper();
		ct = db.getConnection();
	}
	
	public DatabaseManage(){
		this.init();
	}
	
	
	//ȡ���û���Ϣ
	public ResultSet GetUser(String Name){
		try {
			ps = ct.prepareStatement("select USERNAME,PASSWORD,ISLOGIN from COM_PERSON where USERNAME=?");
			ps.setString(1, Name);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//���Ƿ��½�����޸�
	public boolean Update_IsLogin(User user,int isLogin){
		boolean b = true;
		try {
			ps = ct.prepareStatement("update COM_PERSON set ISLOGIN=? where USERNAME=?");
			ps.setInt(1, isLogin);
			ps.setString(2, user.getName());
			if(ps.executeUpdate()!=1)  // ִ��sql���
			{
				b=false;
			}
		} catch (SQLException e) {
			b = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
		
	//�ر����ݿ���Դ
	public void close()
	{
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(ct!=null) ct.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
