package com.mu.view.TreeView;

import java.util.List;

/**
 * �ڵ����
 * @author MUZUKI
 *
 */
public interface Itree {
    public String getName();
    public void setName(String name);
    public void setChildren(List Children);
    public List getChildren();
}
