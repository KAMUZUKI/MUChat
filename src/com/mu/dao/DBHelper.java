package com.mu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据访问层
 * @author MUZUKI
 *
 */
public class DBHelper {

	// 1.对于老版本的不支持SPI机制的驱动，使用静态块加载驱动，驱动只加载一次
	static {
		try {
			System.out.println("加载驱动");
			Class.forName(DbProperties.getInstance().getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 2.将获取数据库联接封装成一个方法
	public Connection getConnection() {
		// 1.使用单例模式，读取属性文件， 值=p.get(键)
		DbProperties p = DbProperties.getInstance();
		Connection con = null;
		try {
			con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("user"), p.getProperty("password"));
			// 另一个重载方法，参数不同，如使用下面的方法的话，则 db.getProperties 中要修改 username->user
			// con=DriverManager.getConnection(p.getProperty("url"),p)
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// 增删改统称为更新，可以用一个方法来完成，不同部分在于SQL语句，还有语句中 ? 的问题
	// Object...values 代表一个动态数组,可以空,也可以是任意长度的值,另外,动态数组只能在参数列表最后一个参数
	public int doUpdate(String sql, Object... values) {
		int result = 0;
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			// 调用设置参数的方法
			setParams(pstmt, values);
			// 发出更新操作
			result = pstmt.executeUpdate();
			// 标准的JDBC是隐式事务提交,所以这里不需要提交事务代码
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 设置参数
	private void setParams(PreparedStatement pstmt, Object... values) throws SQLException {
		// 循环参数列表 values,给pstmt中的 ? 设置参数值
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				// 所有参数都当成object处理
				pstmt.setObject(i + 1, values[i]);
				System.out.println(values[i].toString());
			}
		}
	}

	// 聚合函数查询
	public double selectAggreation(String sql, Object... values) {
		double result = 0;
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			// 调用设置参数的方法
			setParams(pstmt, values);
			// 发出查询操作
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询,返回值:List<Map<String,Object>>
	public List<Map<String, Object>> select(String sql, Object... values) {
		System.out.println("打印"+sql);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try (
				Connection con = getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				) {
			// 调用设置参数的方法
			setParams(pstmt, values);
			// 发出查询操作
			ResultSet rs = pstmt.executeQuery();
			// 根据rs获取结果集里的元数据,取所有的字段名,用于Map的键
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();// 结果集中总的列数
			// 存好列名
			List<String> columnName = new ArrayList<String>();
			for (int i = 0; i < cc; i++) {
				columnName.add(rsmd.getColumnName(i + 1));
			}
			// 循环结果集,取出每一行
			while (rs.next()) {
				// 循环所有的列,一列一列的取值,存到Map中
				Map<String, Object> map = new HashMap<String, Object>();
				// 循环所有的列
				for (int i = 0; i < columnName.size(); i++) {
					String cn = columnName.get(i);// 取到列名
					Object value = rs.getObject(cn);// 根据列名取值
					// 存到Map中
					map.put(cn, value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//更新状态
	public void updateStatus(String name,int b) {
		String sql = "update COM_PERSON set ISLOGIN=? where USERNAME=?";
		if (b==1) {
			doUpdate(sql,1,name);
		}else {
			doUpdate(sql,0,name);
		}
	}
}
