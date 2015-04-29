/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.tryine.oa.common.persistence.DataEntity;

/**
 * 规章制度内容维护Entity
 * @author Summer
 * @version 2015-04-27
 */
public class OaZhiduContent extends DataEntity<OaZhiduContent> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String content;		// 制度内容
	private OaZhiduMl oaZhiduMl;		// 所属目录
	private String files;		// 附件
	private String sort;		// 排序
	
	public OaZhiduContent() {
		super();
	}

	public OaZhiduContent(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public OaZhiduMl getOaZhiduMl() {
		return oaZhiduMl;
	}

	public void setOaZhiduMl(OaZhiduMl oaZhiduMl) {
		this.oaZhiduMl = oaZhiduMl;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}