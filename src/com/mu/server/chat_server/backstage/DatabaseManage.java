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
 * 服务器后台的数据库处理类
 * @author Administrator
 *
 */
public class DatabaseManage {
	//定义连接数据库所需要的对象
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection ct = null;
	
	public void init(){
		//加载驱动
		DBHelper db=new DBHelper();
		ct = db.getConnection();
	}
	
	public DatabaseManage(){
		this.init();
	}
	
	
	//取得用户信息
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
	
	//对是否登陆进行修改
	public boolean Update_IsLogin(User user,int isLogin){
		boolean b = true;
		try {
			ps = ct.prepareStatement("update COM_PERSON set ISLOGIN=? where USERNAME=?");
			ps.setInt(1, isLogin);
			ps.setString(2, user.getName());
			if(ps.executeUpdate()!=1)  // 执行sql语句
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
		
	//关闭数据库资源
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
