package com.mu.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.mu.view.TreeView.Itree;

/**
 * 用于显示TreeView的员工节点 子节点
 * @author MUZUKI
 *
 */
public class Person implements Itree{
    private int id;
    private String name;
    private String username;
    private String image;
    private String status;
    
    private List children = new ArrayList();
    
    public Person(){
    }
    public Person(String name){
        this.name = name;
    }
    public List getChildren() {
        return children;
    }
    public void setChildren(List children) {
        this.children = children;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		this.status = string;
	}
    
}
