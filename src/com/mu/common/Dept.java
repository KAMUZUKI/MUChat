package com.mu.common;

import java.util.ArrayList;
import java.util.List;

import com.mu.view.TreeView.Itree;

/**
 * 用于显示TreeView的部门节点
 * @author MUZUKI
 *
 */
public class Dept implements Itree{
    private String name;
    private List children = new ArrayList();
    
    public Dept(){
    }
    public Dept(String name){
        this.name = name;
    }
    public List getChildren() {
        return children;
    }
    public void setChildren(List children) {
        this.children = children;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
