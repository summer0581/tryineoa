/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.tryine.oa.modules.sys.entity.User;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tryine.oa.common.persistence.DataEntity;

/**
 * 内部消息Entity
 * @author Summer
 * @version 2015-04-28
 */
public class OaMessageRecord extends DataEntity<OaMessageRecord> {
	public enum TYPE{
		noread,readed,sended
	}
	
	private static final long serialVersionUID = 1L;
	private OaMessage oaMessage;		// 通知通告ID 父类
	private User user;		// 接受人
	private String tempUsername; //接收人别名
	private String readFlag;		// 阅读标记
	private Date readDate;		// 阅读时间
	
	//以下为查询参数
	private String type;
	
	public OaMessageRecord() {
		super();
	}

	public OaMessageRecord(String id){
		super(id);
	}

	public OaMessageRecord(OaMessage oaMessage){
		this.oaMessage = oaMessage;
	}

	@Length(min=0, max=64, message="通知通告ID长度必须介于 0 和 64 之间")
	public OaMessage getOaMessage() {
		return oaMessage;
	}

	public void setOaMessage(OaMessage oaMessage) {
		this.oaMessage = oaMessage;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=1, message="阅读标记长度必须介于 0 和 1 之间")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTempUsername() {
		return tempUsername;
	}

	public void setTempUsername(String tempUsername) {
		this.tempUsername = tempUsername;
	}

	
	
	
}