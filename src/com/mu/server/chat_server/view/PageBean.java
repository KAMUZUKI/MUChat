package com.mu.server.chat_server.view;

import java.util.List;
import java.util.Map;

/**
 * 用于查询分页的类
 * @author MUZUKI
 *
 */
public class PageBean {
	private long total;
	private int pageno=1;
	private int pagesize=10;
	
	private List<Map<String,Object>> dataset;
	
	/**
	 * 新增一个计算当前总共有多少页的方法
	 * @param total
	 * @param pageno
	 * @param pagesize
	 * @param dataset
	 */
	
	public int getTotalPages() {
		return (int)(total%pagesize==0 ? total/pagesize:total/pagesize+1);
	}
	
	public PageBean() {
		super();
	}
	
	public PageBean(int total, int pageno, int pagesize, List<Map<String, Object>> dataset) {
		super();
		this.total = total;
		this.pageno = pageno;
		this.pagesize = pagesize;
		this.dataset = dataset;
	}
	
	public long getTotal() {
		return total;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public List<Map<String, Object>> getDataset() {
		return dataset;
	}

	public void setDataset(List<Map<String, Object>> dataset) {
		this.dataset = dataset;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PageBean [total=" + total + ", pageno=" + pageno + ", pagesize=" + pagesize + ", dataset=" + dataset
				+ "]";
	}
}
