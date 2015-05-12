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
 * 加班申请Entity
 * @author Summer
 * @version 2015-05-11
 */
public class OaJiabanapply extends FlowEntity<OaJiabanapply> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private Office office;		// 部门
	private String post;		// 岗位
	private Date starttime;		// 开始时间
	private Date endtime;		// 结束时间
	private String hours;		// 总小时
	private String shiduan;		// 加班时段
	private String address;		// 加班地点
	private String reason;		// 加班事由
	private String directLeaderIdea;		// 直接上级意见
	private String humanResourceIdea;		// 人资部意见
	private String generalManagerIdea;		// 总经理意见
	private String chairManIdea;		// 董事长意见
	private Date beginStarttime;		// 开始 开始时间
	private Date endStarttime;		// 结束 开始时间
	private Date beginEndtime;		// 开始 结束时间
	private Date endEndtime;		// 结束 结束时间
	
	public OaJiabanapply() {
		super();
	}

	public OaJiabanapply(String id){
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
	
	@Length(min=0, max=11, message="总小时长度必须介于 0 和 11 之间")
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	
	@Length(min=0, max=64, message="加班时段长度必须介于 0 和 64 之间")
	public String getShiduan() {
		return shiduan;
	}

	public void setShiduan(String shiduan) {
		this.shiduan = shiduan;
	}
	
	@Length(min=0, max=200, message="加班地点长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=4000, message="加班事由长度必须介于 0 和 4000 之间")
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
	
	public Date getBeginStarttime() {
		return beginStarttime;
	}

	public void setBeginStarttime(Date beginStarttime) {
		this.beginStarttime = beginStarttime;
	}
	
	public Date getEndStarttime() {
		return endStarttime;
	}

	public void setEndStarttime(Date endStarttime) {
		this.endStarttime = endStarttime;
	}
		
	public Date getBeginEndtime() {
		return beginEndtime;
	}

	public void setBeginEndtime(Date beginEndtime) {
		this.beginEndtime = beginEndtime;
	}
	
	public Date getEndEndtime() {
		return endEndtime;
	}

	public void setEndEndtime(Date endEndtime) {
		this.endEndtime = endEndtime;
	}
		
}