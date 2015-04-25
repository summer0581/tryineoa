/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import com.tryine.oa.modules.sys.entity.Office;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tryine.oa.common.persistence.DataEntity;

/**
 * 请假申请流程Entity
 * @author Summer
 * @version 2015-04-13
 */
public class OaLeaveapply extends DataEntity<OaLeaveapply> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String name;		// 姓名
	private Office office;		// 部门
	private String post;		// 岗位
	private String telephone;		// 联系电话
	private String isPositive;		// 是否转正
	private Date starttime;		// 开始时间
	private Date endtime;		// 结束时间
	private String postAgent;		// 岗位代理
	private String reason;		// 请假理由
	private String directLeaderIdea;		// 直接上级意见
	private String humanResourceIdea;		// 人资部意见
	private String generalManagerIdea;		// 总经理意见
	private String chairManIdea;		// 董事长意见
	
	public OaLeaveapply() {
		super();
	}

	public OaLeaveapply(String id){
		super(id);
	}

	@Length(min=0, max=20, message="类型长度必须介于 0 和 20 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=64, message="岗位长度必须介于 0 和 64 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=1, message="是否转正长度必须介于 0 和 1 之间")
	public String getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(String isPositive) {
		this.isPositive = isPositive;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@Length(min=0, max=64, message="岗位代理长度必须介于 0 和 64 之间")
	public String getPostAgent() {
		return postAgent;
	}

	public void setPostAgent(String postAgent) {
		this.postAgent = postAgent;
	}
	
	@Length(min=0, max=4000, message="请假理由长度必须介于 0 和 4000 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=4000, message="直接上级意见长度必须介于 0 和 4000 之间")
	public String getDirectLeaderIdea() {
		return directLeaderIdea;
	}

	public void setDirectLeaderIdea(String directLeaderIdea) {
		this.directLeaderIdea = directLeaderIdea;
	}
	
	@Length(min=0, max=4000, message="人资部意见长度必须介于 0 和 4000 之间")
	public String getHumanResourceIdea() {
		return humanResourceIdea;
	}

	public void setHumanResourceIdea(String humanResourceIdea) {
		this.humanResourceIdea = humanResourceIdea;
	}
	
	@Length(min=0, max=4000, message="总经理意见长度必须介于 0 和 4000 之间")
	public String getGeneralManagerIdea() {
		return generalManagerIdea;
	}

	public void setGeneralManagerIdea(String generalManagerIdea) {
		this.generalManagerIdea = generalManagerIdea;
	}
	
	@Length(min=0, max=4000, message="董事长意见长度必须介于 0 和 4000 之间")
	public String getChairManIdea() {
		return chairManIdea;
	}

	public void setChairManIdea(String chairManIdea) {
		this.chairManIdea = chairManIdea;
	}
	
}