package com.mu.server.chat_server.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mu.dao.DBHelper;

/**
 * ҵ���
 * @author MUZUKI
 */
public class EmployeeImpl {
	//����Ա����Ϣ
	public int updateEmployee(int id,String name,int age,String username,String pic,int deptno) {
		int result=0;
		DBHelper db=new DBHelper();
		Connection con=db.getConnection();
		try {
			con.setAutoCommit(false);
			
			String sql="update COM_PERSON set NAME=?,AGE=?,USERNAME=?,IMAGE=?,DEPTNO=? where ID=?";
			
			PreparedStatement pstat=con.prepareStatement(sql);
			
			pstat.setString(1, name);
			pstat.setInt(2, age);
			pstat.setString(3, username);
			pstat.setString(4, pic);
			pstat.setInt(5,deptno);
			pstat.setInt(6,id);
			result=pstat.executeUpdate();
	
			con.commit();
		} catch (Exception e) {
			result=0;
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//����Ա������
	public int updateEmployee(int id,String pwd) {
		int result=0;
		DBHelper db=new DBHelper();
		Connection con=db.getConnection();
		try {
			con.setAutoCommit(false);
			
			String sql="update COM_PERSON set PASSWORD=? where ID=?";
			
			PreparedStatement pstat=con.prepareStatement(sql);
			pstat.setString(1, pwd);
			pstat.setInt(2, id);
			result=pstat.executeUpdate();
	
			con.commit();
		} catch (Exception e) {
			result=0;
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//���Ա��
	public long regStudent(int id,String name,int age,String username,int deptno,String pic){
		//1.����DBHelperȡ����ѧ�� +1
		DBHelper db=new DBHelper();
		Connection con= db.getConnection();
		long l=0;
		try {
			//2.���Ա����
			String sql="insert into COM_PERSON(ID,NAME,DEPTNO,USERNAME,PASSWORD,ISLOGIN,IMAGE,AGE) values(?,?,?,?,?,?,?,?)";
			
			con.setAutoCommit(false);
			PreparedStatement pStatement=con.prepareStatement(sql);
			pStatement.setInt(1,id);
			pStatement.setString(2, name);
			pStatement.setInt(3, deptno);
			pStatement.setString(4, username);
			pStatement.setString(5, "202cb962ac59075b964b07152d234b70");
			pStatement.setString(6, "0");
			pStatement.setString(7, pic);
			pStatement.setInt(8, age);
			int tmpInt = pStatement.executeUpdate();
			System.out.println(tmpInt);
			if (tmpInt!=1) {
				l=0;
			}else {
				l=id;
				System.out.println(l);
			}
			con.commit();
		} catch (Exception e) {
			l=0;
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return l;
	}
	
	public static void main(String[] args)  {
		EmployeeImpl ei=new EmployeeImpl();
//		List<Map<String, Object>> list=sbi.findStudents("101", null, null, null, false, 1, 10);
//		System.out.println(list);
//		
//		list=sbi.findStudents(null, "��", "23", "24",true, 1, 10);
//		System.out.println(list);
		
		System.out.println("\n\n\n\n\n");
		PageBean pageBean=ei.pageSearch(null, null,null, null,null, true, 1, 10);
		System.out.println("�ܼ�¼��" + pageBean.getTotal()+",��ҳ����" + pageBean.getTotalPages()+",��ǰҳ" + pageBean.getPagesize()+"ÿҳ"+pageBean.getPagesize()+"��");
		List<Map<String, Object>> list=pageBean.getDataset();
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	public PageBean pageSearch(String id,String name,String deptname,String minAge,String maxAge,boolean isAsc,int pageno,int pagesize) {
		PageBean pageBean=new PageBean();
		
		List<Map<String, Object>> dataSetList=this.findEmployees(id, name,deptname, minAge, maxAge, isAsc, pageno, pagesize);
		long total=this.countStudent(id,name,minAge,maxAge);
		
		pageBean.setTotal(total);
		pageBean.setDataset(dataSetList);
		pageBean.setPageno(pageno);
		pageBean.setPagesize(pagesize);
		
		return pageBean;
	}
	
	public long countStudent(String id,String name,String minAge,String maxAge) {
		List<Object> params=new ArrayList<>();
		String sql="select count(*) from COM_PERSON where 1=1";
		if(id!=null && !"".equals(id)) {
			sql+=" and ID=? ";
			params.add(id);
		}
		if(name!=null && !"".equals(name)) {
			sql+=" and NAME like ? ";
			params.add("%" + name + "%");
		}
		if(minAge!=null && !"".equals(minAge)) {
			sql+=" and AGE>=? ";
			params.add(minAge);
		}
		if(maxAge!=null && !"".equals(maxAge)) {
			sql+=" and AGE<=? ";
			params.add(maxAge);
		}
		
		System.out.println("��ִ�еķ�ҳ��䣺" + sql);
		System.out.println("��Ӧ�Ĳ���Ϊ��" + params);
		
		DBHelper db=new DBHelper();
		long result =new Double(db.selectAggreation(sql, params.toArray())).longValue();
		return result;
	}
	
	/**
	 * ���ݸ���������ҳ��ѯԱ��
	 * 
	 */	
	public List<Map<String, Object>> findEmployees(String id,String name,String deptname,String minAge,String maxAge,boolean isAsc,int pageno,int pagesize) {
		List<Object> params=new ArrayList<>();
		String sql=" select ID,NAME,DEPTNAME,DEPTNO,USERNAME,PASSWORD,ISLOGIN,IMAGE,AGE from("
				+ " select a.*,rownum rn"
				+ " from "
					+ " (select ID,NAME,DEPTNAME,COM_PERSON.DEPTNO,USERNAME,PASSWORD,ISLOGIN,IMAGE,AGE from COM_PERSON,COM_DEPT where COM_DEPT.DEPTNO=COM_PERSON.DEPTNO";
		if(id!=null && !"".equals(id)) {
			sql+=" and ID=? ";
			params.add(id);
		}
		if(name!=null && !"".equals(name)) {
			sql+=" and NAME like ? ";
			params.add("%" + name + "%");
		}
		if(deptname!=null && !"".equals(deptname)) {
			sql+=" and DEPTNAME=? ";
			params.add(deptname);
		}
		if(minAge!=null && !"".equals(minAge)) {
			sql+=" and AGE>=? ";
			params.add(minAge);
		}
		if(maxAge!=null && !"".equals(maxAge)) {
			sql+=" and AGE<=? ";
			params.add(maxAge);
		}
		if(isAsc) {
			sql+=" order by ID asc ";
		} else {
			sql+=" order by ID desc ";
		}
		sql+=" )a where rownum<=? )where rn>? ";
		
		int max=pageno*pagesize;
		int min=(pageno-1)*pagesize;
		params.add(max);
		params.add(min);
		
		System.out.println("��ִ�еķ�ҳ��䣺" + sql);
		System.out.println("��Ӧ�Ĳ���Ϊ��" + params);
		
		DBHelper db=new DBHelper();
		return db.select(sql, params.toArray());
	}

	private int sum(int[] result) {
		int total = 0;
		if (result == null || result.length <= 0) {
			return 0;
		}
		for (int i = 0; i < result.length; i++) {
			total++;
		}
		return total;
	}
}
