/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tryine.oa.common.persistence.FlowEntity;


/**
 * 调休申请Entity
 * @author Summer
 * @version 2015-05-11
 */
public class OaTiaoxiuapply extends FlowEntity<OaTiaoxiuapply> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String post;		// 岗位
	private String isPositive;		// 是否转正
	private Date restStarttime;		// 休息开始时间
	private Date restEndtime;		// 休息结束时间
	private String restHours;		// 休息总小时
	private Date jiabanStarttime;		// 加班开始时间
	private Date jiabanEndtime;		// 加班结束时间
	private String jiabanHours;		// 加班总小时
	private String postAgent;		// 岗位代理
	private String reason;		// 调休原因
	private String directLeaderIdea;		// 直接上级意见
	private String branchLeaderIdea;		// 分公司总经理意见
	private String humanResourceIdea;		// 人资部意见
	private String generalManagerIdea;		// 集团总经理意见
	private String chairManIdea;		// 董事长意见
	
	public OaTiaoxiuapply() {
		super();
	}

	public OaTiaoxiuapply(String id){
		super(id);
	}

	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="岗位长度必须介于 0 和 64 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=1, message="是否转正长度必须介于 0 和 1 之间")
	public String getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(String isPositive) {
		this.isPositive = isPositive;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRestStarttime() {
		return restStarttime;
	}

	public void setRestStarttime(Date restStarttime) {
		this.restStarttime = restStarttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRestEndtime() {
		return restEndtime;
	}

	public void setRestEndtime(Date restEndtime) {
		this.restEndtime = restEndtime;
	}
	
	@Length(min=0, max=11, message="休息总小时长度必须介于 0 和 11 之间")
	public String getRestHours() {
		return restHours;
	}

	public void setRestHours(String restHours) {
		this.restHours = restHours;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJiabanStarttime() {
		return jiabanStarttime;
	}

	public void setJiabanStarttime(Date jiabanStarttime) {
		this.jiabanStarttime = jiabanStarttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJiabanEndtime() {
		return jiabanEndtime;
	}

	public void setJiabanEndtime(Date jiabanEndtime) {
		this.jiabanEndtime = jiabanEndtime;
	}
	
	@Length(min=0, max=11, message="加班总小时长度必须介于 0 和 11 之间")
	public String getJiabanHours() {
		return jiabanHours;
	}

	public void setJiabanHours(String jiabanHours) {
		this.jiabanHours = jiabanHours;
	}
	
	@Length(min=0, max=64, message="岗位代理长度必须介于 0 和 64 之间")
	public String getPostAgent() {
		return postAgent;
	}

	public void setPostAgent(String postAgent) {
		this.postAgent = postAgent;
	}
	
	@Length(min=0, max=2000, message="调休原因长度必须介于 0 和 2000 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=2000, message="直接上级意见长度必须介于 0 和 2000 之间")
	public String getDirectLeaderIdea() {
		return directLeaderIdea;
	}

	public void setDirectLeaderIdea(String directLeaderIdea) {
		this.directLeaderIdea = directLeaderIdea;
	}
	
	@Length(min=0, max=2000, message="人资部意见长度必须介于 0 和 2000 之间")
	public String getHumanResourceIdea() {
		return humanResourceIdea;
	}

	public void setHumanResourceIdea(String humanResourceIdea) {
		this.humanResourceIdea = humanResourceIdea;
	}
	
	@Length(min=0, max=2000, message="总经理意见长度必须介于 0 和 2000 之间")
	public String getGeneralManagerIdea() {
		return generalManagerIdea;
	}

	public void setGeneralManagerIdea(String generalManagerIdea) {
		this.generalManagerIdea = generalManagerIdea;
	}
	
	@Length(min=0, max=2000, message="董事长意见长度必须介于 0 和 2000 之间")
	public String getChairManIdea() {
		return chairManIdea;
	}

	public void setChairManIdea(String chairManIdea) {
		this.chairManIdea = chairManIdea;
	}
	@Length(min=0, max=2000, message="分公司总经理意见长度必须介于 0 和 2000 之间")
	public String getBranchLeaderIdea() {
		return branchLeaderIdea;
	}

	public void setBranchLeaderIdea(String branchLeaderIdea) {
		this.branchLeaderIdea = branchLeaderIdea;
	}
	
}