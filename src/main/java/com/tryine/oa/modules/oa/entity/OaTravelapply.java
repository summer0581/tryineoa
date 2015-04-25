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
 * 出差申请Entity
 * @author Summer
 * @version 2015-04-16
 */
public class OaTravelapply extends DataEntity<OaTravelapply> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private Office office;		// 部门
	private String post;		// 岗位
	private String telephone;		// 联系电话
	private Date outTime;		// 外出时间
	private Date plantobacktime;		// 预计返还时间
	private String outPlace;		// 外出地点
	private String customerInfo;		// 客户姓名及电话
	private String joinPeople;		// 随行人员
	private String directleaderIdea;		// 直接上级意见
	private String generalManagerIdea;		// 分公司总经理意见
	private String outReason;		// 外出事由
	
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
	
	@Length(min=0, max=500, message="客户姓名及电话长度必须介于 0 和 500 之间")
	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}
	
	@Length(min=0, max=500, message="随行人员长度必须介于 0 和 500 之间")
	public String getJoinPeople() {
		return joinPeople;
	}

	public void setJoinPeople(String joinPeople) {
		this.joinPeople = joinPeople;
	}
	
	@Length(min=0, max=4000, message="直接上级意见长度必须介于 0 和 4000 之间")
	public String getDirectleaderIdea() {
		return directleaderIdea;
	}

	public void setDirectleaderIdea(String directleaderIdea) {
		this.directleaderIdea = directleaderIdea;
	}
	
	@Length(min=0, max=4000, message="分公司总经理意见长度必须介于 0 和 4000 之间")
	public String getGeneralManagerIdea() {
		return generalManagerIdea;
	}

	public void setGeneralManagerIdea(String generalManagerIdea) {
		this.generalManagerIdea = generalManagerIdea;
	}
	
	@Length(min=0, max=4000, message="外出事由长度必须介于 0 和 4000 之间")
	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}
	
}