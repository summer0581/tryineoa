/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tryine.oa.common.persistence.DataEntity;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 意见反馈Entity
 * @author Summer
 * @version 2015-05-05
 */
public class OaFeedbackReceived extends DataEntity<OaFeedbackReceived> {
	
	private static final long serialVersionUID = 1L;
	private OaFeedback feedback;		// 意见id 父类
	private User user;		// 接收人员
	private String content;		// 回复内容
	private Date backTime;		// 回复时间
	private String isRead;		// 是否阅读
	
	
	private boolean isSendMessage;//是否发送内部消息
	private boolean isSelf;//是否是自己的数据
	
	public OaFeedbackReceived() {
		super();
	}

	public OaFeedbackReceived(String id){
		super(id);
	}

	public OaFeedbackReceived(OaFeedback feedback){
		this.feedback = feedback;
	}

	public OaFeedback getFeedback() {
		return feedback;
	}

	public void setFeedback(OaFeedback feedback) {
		this.feedback = feedback;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=2000, message="回复内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	
	@Length(min=0, max=1, message="是否阅读长度必须介于 0 和 1 之间")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public boolean getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(boolean isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	public boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	
	
}