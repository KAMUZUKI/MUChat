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
 * TreeView���ɹ���
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
		
		//��list�����Ž��з��飬��С�����ݿ�ѹ��
		Map<String,List<Map<String,Object>>> groupByDeptnameMap =
				list.stream().collect(Collectors.groupingBy(e -> (String)e.get("DEPTNAME")));
		
		// ���������Tree��
        Dept dept = null;
		for (Map.Entry<String, List<Map<String,Object>>> entry : groupByDeptnameMap.entrySet()) {
			String key = entry.getKey();
			dept = new Dept(key);
			
			//�����ŷֵ�list
			List<Map<String,Object>> tmplist = entry.getValue();
			
			ArrayList<Person> persons = new ArrayList<>();
			for (int i = 0; i < tmplist.size(); i++) {
				Map<String,Object> map= tmplist.get(i);
				Person person = new Person();
				//ID
				person.setId(Integer.parseInt(map.get("ID").toString()));
				//����
				person.setName(map.get("NAME").toString());
				//��¼״̬
				person.setStatus(map.get("ISLOGIN").toString());
				//�û���
				if (map.get("USERNAME")!=null) {
					person.setUsername(map.get("USERNAME").toString());
				}
				//ͷ��
				if (map.get("IMAGE")!=null) {
					person.setImage(map.get("IMAGE").toString());
				}
				deptSet.add(map.get("DEPTNAME").toString());
				//���person�ڵ�
				persons.add(person);
			}
			dept.setChildren(persons);
			tree.add(dept);
		}
        return tree;
    }
}
