/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tryine.oa.common.persistence.FlowEntity;
import com.tryine.oa.modules.sys.entity.Office;

/**
 * 出差申请Entity
 * @author Summer
 * @version 2015-04-16
 */
public class OaTravelapply extends FlowEntity<OaTravelapply> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private Office office;		// 部门
	private String post;		// 岗位
	private String telephone;		// 联系电话
	private Date outTime;		// 外出时间
	private Date plantobacktime;		// 预计返还时间
	private String outPlace;		// 外出地点
	private String customerInfo;		// 客户姓名
	private String customerTelephone;		// 客户电话
	private String joinPeople;		// 随行人员
	private String directLeaderIdea;		// 直接上级意见
	private String branchLeaderIdea;		// 分公司总经理意见
	private String generalManagerIdea;		// 集团总经理意见
	private String humanResourceIdea;		// 人资部意见
	private String chairManIdea;		// 董事长意见
	
	private String outReason;		// 外出事由
	
	private Date startOutTime;		// 外出起始时间
	private Date endOutTime;		// 外出结束时间
	private Date startPlantobacktime;		// 预计返还起始时间
	private Date endPlantobacktime;		// 预计返还结束时间
	
	public OaTravelapply() {
		super();
	}

	public OaTravelapply(String id){
		super(id);
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

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlantobacktime() {
		return plantobacktime;
	}

	public void setPlantobacktime(Date plantobacktime) {
		this.plantobacktime = plantobacktime;
	}
	
	@Length(min=0, max=300, message="外出地点长度必须介于 0 和 300 之间")
	public String getOutPlace() {
		return outPlace;
	}

	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}
	
	@Length(min=0, max=200, message="客户姓名长度必须介于 0 和 200 之间")
	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}
	
	
	@Length(min=0, max=64, message="客户电话长度必须介于 0 和64 之间")
	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	@Length(min=0, max=500, message="随行人员长度必须介于 0 和 500 之间")
	public String getJoinPeople() {
		return joinPeople;
	}

	public void setJoinPeople(String joinPeople) {
		this.joinPeople = joinPeople;
	}
	
	@Length(min=0, max=2000, message="集团总经理意见长度必须介于 0 和 2000 之间")
	public String getGeneralManagerIdea() {
		return generalManagerIdea;
	}

	public void setGeneralManagerIdea(String generalManagerIdea) {
		this.generalManagerIdea = generalManagerIdea;
	}
	@Length(min=0, max=2000, message="分公司总经理意见长度必须介于 0 和 2000 之间")
	public String getBranchLeaderIdea() {
		return branchLeaderIdea;
	}

	public void setBranchLeaderIdea(String branchLeaderIdea) {
		this.branchLeaderIdea = branchLeaderIdea;
	}

	@Length(min=0, max=2000, message="外出事由长度必须介于 0 和 2000 之间")
	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public Date getStartOutTime() {
		return startOutTime;
	}

	public void setStartOutTime(Date startOutTime) {
		this.startOutTime = startOutTime;
	}

	public Date getEndOutTime() {
		return endOutTime;
	}

	public void setEndOutTime(Date endOutTime) {
		this.endOutTime = endOutTime;
	}

	public Date getStartPlantobacktime() {
		return startPlantobacktime;
	}

	public void setStartPlantobacktime(Date startPlantobacktime) {
		this.startPlantobacktime = startPlantobacktime;
	}

	public Date getEndPlantobacktime() {
		return endPlantobacktime;
	}

	public void setEndPlantobacktime(Date endPlantobacktime) {
		this.endPlantobacktime = endPlantobacktime;
	}
	@Length(min=0, max=2000, message="直接上级意见长度必须介于 0 和 2000 之间")
	public String getDirectLeaderIdea() {
		return directLeaderIdea;
	}

	public void setDirectLeaderIdea(String directLeaderIdea) {
		this.directLeaderIdea = directLeaderIdea;
	}
	@Length(min=0, max=2000, message="人资总监意见长度必须介于 0 和 2000 之间")
	public String getHumanResourceIdea() {
		return humanResourceIdea;
	}

	public void setHumanResourceIdea(String humanResourceIdea) {
		this.humanResourceIdea = humanResourceIdea;
	}
	@Length(min=0, max=2000, message="董事长意见长度必须介于 0 和 2000 之间")
	public String getChairManIdea() {
		return chairManIdea;
	}

	public void setChairManIdea(String chairManIdea) {
		this.chairManIdea = chairManIdea;
	}
	
	
	
}