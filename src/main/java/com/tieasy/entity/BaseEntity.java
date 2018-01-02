package com.tieasy.entity;

import java.io.Serializable;

/**
 * 基础实体类
 * @author ko
 *
 */
public class BaseEntity implements Serializable {

	private int offset;
	private int pagesize;
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	
	
}
