/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.tryine.oa.common.persistence.DataEntity;

/**
 * 意见反馈Entity
 * @author Summer
 * @version 2015-05-05
 */
public class OaFeedback extends DataEntity<OaFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 意见标题
	private OaFeedbackBox box;		// 意见箱
	private String content;		// 详细内容
	private String isHidename;		// 是否匿名
	private List<OaFeedbackReceived> oaFeedbackReceivedList = Lists.newArrayList();		// 子表列表
	
	
	private int isReply;//是否已回复
	public OaFeedback() {
		super();
	}

	public OaFeedback(String id){
		super(id);
	}

	@Length(min=1, max=64, message="意见标题长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public OaFeedbackBox getBox() {
		return box;
	}

	public void setBox(OaFeedbackBox box) {
		this.box = box;
	}
	
	@Length(min=0, max=2000, message="详细内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="是否匿名长度必须介于 0 和 1 之间")
	public String getIsHidename() {
		return isHidename;
	}

	public void setIsHidename(String isHidename) {
		this.isHidename = ("1".equals(isHidename)?"1":"0");
	}
	
	public List<OaFeedbackReceived> getOaFeedbackReceivedList() {
		return oaFeedbackReceivedList;
	}

	public void setOaFeedbackReceivedList(List<OaFeedbackReceived> oaFeedbackReceivedList) {
		this.oaFeedbackReceivedList = oaFeedbackReceivedList;
	}

	public int getIsReply() {
		return isReply;
	}

	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}
	
}