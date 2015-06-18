/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

import com.google.common.collect.Lists;
import com.tryine.oa.common.persistence.DataEntity;
import com.tryine.oa.common.utils.Collections3;
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 内部消息Entity
 * @author Summer
 * @version 2015-04-28
 */
public class OaMessage extends DataEntity<OaMessage> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String url;		// 链接地址
	private String tempUsername;//临时用户名
	private List<OaMessageRecord> oaMessageRecordList = Lists.newArrayList();		// 子表列表
	
	private boolean isSelf;//是否为自己的数据
	private String readFlag;	// 本人阅读状态
	private String type; //查询类别 noread,readed,sended
	private boolean isAnonymous;//是否匿名
	
	public OaMessage() {
		super();
	}

	public OaMessage(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标题长度必须介于 0 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getReceivedUserids() {
		return Collections3.extractToString(oaMessageRecordList, "user.id", ",") ;
	}

	public void setReceivedUserids(String receivedUserids) {
		this.oaMessageRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(receivedUserids, ",")){
			OaMessageRecord entity = new OaMessageRecord();
			entity.setId(IdGen.uuid());
			entity.setOaMessage(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.oaMessageRecordList.add(entity);
		}
	}
	
	public String getReceivedUsernames() {
		return Collections3.extractToString(oaMessageRecordList, "user.id", ",") ;
	}

	public void setReceivedUsernames(String receivedUsernames) {
		
	}
	
	@Length(min=0, max=400, message="链接地址长度必须介于 0 和 400 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<OaMessageRecord> getOaMessageRecordList() {
		return oaMessageRecordList;
	}

	public void setOaMessageRecordList(List<OaMessageRecord> oaMessageRecordList) {
		this.oaMessageRecordList = oaMessageRecordList;
	}

	public boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
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