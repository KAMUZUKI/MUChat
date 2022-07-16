package com.mu.common;

import java.io.Serializable;

/**
 * 用户类
 * @author MUZUKI
 *
 */
public class User implements Serializable{
	private int id;
	private int age;
 	private int deptno;
	private String Name;
	private String PassWords;
	private String Type;	//注册或是登陆
	private String Image;
	private String deptname;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public User(){
		
	}
	
	public User(String Name,String PassWords){
		this.Name = Name;
		this.PassWords = PassWords;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassWords() {
		return PassWords;
	}

	public void setPassWords(String passWords) {
		PassWords = passWords;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	
}
