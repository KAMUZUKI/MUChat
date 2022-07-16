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
 * ���ݷ��ʲ�
 * @author MUZUKI
 *
 */
public class DBHelper {

	// 1.�����ϰ汾�Ĳ�֧��SPI���Ƶ�������ʹ�þ�̬���������������ֻ����һ��
	static {
		try {
			System.out.println("��������");
			Class.forName(DbProperties.getInstance().getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 2.����ȡ���ݿ����ӷ�װ��һ������
	public Connection getConnection() {
		// 1.ʹ�õ���ģʽ����ȡ�����ļ��� ֵ=p.get(��)
		DbProperties p = DbProperties.getInstance();
		Connection con = null;
		try {
			con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("user"), p.getProperty("password"));
			// ��һ�����ط�����������ͬ����ʹ������ķ����Ļ����� db.getProperties ��Ҫ�޸� username->user
			// con=DriverManager.getConnection(p.getProperty("url"),p)
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// ��ɾ��ͳ��Ϊ���£�������һ����������ɣ���ͬ��������SQL��䣬��������� ? ������
	// Object...values ����һ����̬����,���Կ�,Ҳ���������ⳤ�ȵ�ֵ,����,��̬����ֻ���ڲ����б����һ������
	public int doUpdate(String sql, Object... values) {
		int result = 0;
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			// �������ò����ķ���
			setParams(pstmt, values);
			// �������²���
			result = pstmt.executeUpdate();
			// ��׼��JDBC����ʽ�����ύ,�������ﲻ��Ҫ�ύ�������
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ���ò���
	private void setParams(PreparedStatement pstmt, Object... values) throws SQLException {
		// ѭ�������б� values,��pstmt�е� ? ���ò���ֵ
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				// ���в���������object����
				pstmt.setObject(i + 1, values[i]);
				System.out.println(values[i].toString());
			}
		}
	}

	// �ۺϺ�����ѯ
	public double selectAggreation(String sql, Object... values) {
		double result = 0;
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			// �������ò����ķ���
			setParams(pstmt, values);
			// ������ѯ����
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ��ѯ,����ֵ:List<Map<String,Object>>
	public List<Map<String, Object>> select(String sql, Object... values) {
		System.out.println("��ӡ"+sql);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try (
				Connection con = getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				) {
			// �������ò����ķ���
			setParams(pstmt, values);
			// ������ѯ����
			ResultSet rs = pstmt.executeQuery();
			// ����rs��ȡ��������Ԫ����,ȡ���е��ֶ���,����Map�ļ�
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();// ��������ܵ�����
			// �������
			List<String> columnName = new ArrayList<String>();
			for (int i = 0; i < cc; i++) {
				columnName.add(rsmd.getColumnName(i + 1));
			}
			// ѭ�������,ȡ��ÿһ��
			while (rs.next()) {
				// ѭ�����е���,һ��һ�е�ȡֵ,�浽Map��
				Map<String, Object> map = new HashMap<String, Object>();
				// ѭ�����е���
				for (int i = 0; i < columnName.size(); i++) {
					String cn = columnName.get(i);// ȡ������
					Object value = rs.getObject(cn);// ��������ȡֵ
					// �浽Map��
					map.put(cn, value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//����״̬
	public void updateStatus(String name,int b) {
		String sql = "update COM_PERSON set ISLOGIN=? where USERNAME=?";
		if (b==1) {
			doUpdate(sql,1,name);
		}else {
			doUpdate(sql,0,name);
		}
	}
}
