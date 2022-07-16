package com.mu.view.TreeView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.swt.graphics.Image;

import com.mu.dao.DBHelper;
import com.mu.common.Dept;
import com.mu.common.Person;

/**
 * TreeView生成工厂
 * @author MUZUKI
 *
 */
public class Factory {
    public static List<Dept> createTree(){
    	
        //friendList.setChildren(persons);
    	ArrayList tree = new ArrayList();
        DBHelper dbHelper=new DBHelper();
		String sql="SELECT ID,NAME,DEPTNAME,USERNAME,IMAGE,ISLOGIN from COM_PERSON,COM_DEPT WHERE COM_DEPT.DEPTNO=COM_PERSON.DEPTNO ORDER BY COM_PERSON.DEPTNO";
		List<Map<String,Object>> list =  dbHelper.select(sql);
		Set<String> deptSet=new HashSet<>();
		
		//对list按部门进行分组，减小查数据库压力
		Map<String,List<Map<String,Object>>> groupByDeptnameMap =
				list.stream().collect(Collectors.groupingBy(e -> (String)e.get("DEPTNAME")));
		
		// 分组添加至Tree中
        Dept dept = null;
		for (Map.Entry<String, List<Map<String,Object>>> entry : groupByDeptnameMap.entrySet()) {
			String key = entry.getKey();
			dept = new Dept(key);
			
			//按部门分的list
			List<Map<String,Object>> tmplist = entry.getValue();
			
			ArrayList<Person> persons = new ArrayList<>();
			for (int i = 0; i < tmplist.size(); i++) {
				Map<String,Object> map= tmplist.get(i);
				Person person = new Person();
				//ID
				person.setId(Integer.parseInt(map.get("ID").toString()));
				//名字
				person.setName(map.get("NAME").toString());
				//登录状态
				person.setStatus(map.get("ISLOGIN").toString());
				//用户名
				if (map.get("USERNAME")!=null) {
					person.setUsername(map.get("USERNAME").toString());
				}
				//头像
				if (map.get("IMAGE")!=null) {
					person.setImage(map.get("IMAGE").toString());
				}
				deptSet.add(map.get("DEPTNAME").toString());
				//添加person节点
				persons.add(person);
			}
			dept.setChildren(persons);
			tree.add(dept);
		}
        return tree;
    }
}
