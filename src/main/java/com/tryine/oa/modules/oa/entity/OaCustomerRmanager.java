/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tryine.oa.common.persistence.DataEntity;
import com.tryine.oa.common.utils.Collections3;
import com.tryine.oa.common.utils.IdGen;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.sys.entity.User;



/**
 * 客户信息管理Entity
 * @author Summer
 * @version 2015-05-04
 */
public class OaCustomerRmanager extends DataEntity<OaCustomerRmanager> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户姓名
	private String zhujijc;		// 助记简称
	private String files;		// 附件
	private Date hfTime;		// 下次回访时间
	private String sex;		// 性别
	private String zhonglei;		// 客户种类
	private String xinyongdj;		// 客户信用等级
	private String hangye;		// 客户行业
	private String guanxidj;		// 客户关系等级
	private String jieduan;		// 客户阶段
	private String laiyuan;		// 客户来源
	private String jianjie;		// 客户简介
	private String quyu;		// 客户区域
	private String province;		// 省份
	private String city;		// 城市
	private String email;		// 电子邮箱
	private String telephone;		// 电话
	private String fax;		// 传真
	private String website;		// 网址
	private String address;		// 地址
	private String gongxiang;		// 客户共享
	private String tuanduiliulan;		// 团队浏览
	private String jiazhipg;		// 客户价值评估
	private String guimo;		// 客户人员规模
	private String type;		// 客户类别(企业或个人)
	private String isOpenSea="1";	//是否是公海客户
	private String ownerNames; //拥有者名字集
	private List<OaCustomerRelation> oaCustomerRelationList = Lists.newArrayList();		// 子表列表
	private List<OaCustomerReadShareRelation> oaCustomerReadShareRelationList = Lists.newArrayList();		// 子表列表
	private List<OaCustomerWriteShareRelation> oaCustomerWriteShareRelationList = Lists.newArrayList();		// 子表列表

	private String oaCustomerRmanagerReadNames;//共享只读人员名字集合(from回显)
	private String oaCustomerRmanagerWriteNames;//共享可写人员名字集合(from回显)
	
	
	
	public List<OaCustomerReadShareRelation> getOaCustomerReadShareRelationList() {
		return oaCustomerReadShareRelationList;
	}

	public void setOaCustomerReadShareRelationList(
			List<OaCustomerReadShareRelation> oaCustomerReadShareRelationList) {
		this.oaCustomerReadShareRelationList = oaCustomerReadShareRelationList;
	}

	public List<OaCustomerWriteShareRelation> getOaCustomerWriteShareRelationList() {
		return oaCustomerWriteShareRelationList;
	}

	public void setOaCustomerWriteShareRelationList(
			List<OaCustomerWriteShareRelation> oaCustomerWriteShareRelationList) {
		this.oaCustomerWriteShareRelationList = oaCustomerWriteShareRelationList;
	}

	public OaCustomerRmanager() {
		super();
	}

	public OaCustomerRmanager(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户姓名长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="助记简称长度必须介于 0 和 64 之间")
	public String getZhujijc() {
		return zhujijc;
	}

	public void setZhujijc(String zhujijc) {
		this.zhujijc = zhujijc;
	}
	
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHfTime() {
		return hfTime;
	}

	public void setHfTime(Date hfTime) {
		this.hfTime = hfTime;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=64, message="客户种类长度必须介于 0 和 64 之间")
	public String getZhonglei() {
		return zhonglei;
	}

	public void setZhonglei(String zhonglei) {
		this.zhonglei = zhonglei;
	}
	
	@Length(min=0, max=64, message="客户信用等级长度必须介于 0 和 64 之间")
	public String getXinyongdj() {
		return xinyongdj;
	}

	public void setXinyongdj(String xinyongdj) {
		this.xinyongdj = xinyongdj;
	}
	
	@Length(min=0, max=64, message="客户行业长度必须介于 0 和 64 之间")
	public String getHangye() {
		return hangye;
	}

	public void setHangye(String hangye) {
		this.hangye = hangye;
	}
	
	@Length(min=0, max=64, message="客户关系等级长度必须介于 0 和 64 之间")
	public String getGuanxidj() {
		return guanxidj;
	}

	public void setGuanxidj(String guanxidj) {
		this.guanxidj = guanxidj;
	}
	
	@Length(min=0, max=64, message="客户阶段长度必须介于 0 和 64 之间")
	public String getJieduan() {
		return jieduan;
	}

	public void setJieduan(String jieduan) {
		this.jieduan = jieduan;
	}
	
	@Length(min=0, max=64, message="客户来源长度必须介于 0 和 64 之间")
	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	
	@Length(min=0, max=500, message="客户简介长度必须介于 0 和 500 之间")
	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	
	@Length(min=0, max=64, message="客户区域长度必须介于 0 和 64 之间")
	public String getQuyu() {
		return quyu;
	}

	public void setQuyu(String quyu) {
		this.quyu = quyu;
	}
	
	@Length(min=0, max=64, message="省份长度必须介于 0 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="城市长度必须介于 0 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="电子邮箱长度必须介于 0 和 64 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=64, message="电话长度必须介于 0 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=64, message="传真长度必须介于 0 和 64 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=64, message="网址长度必须介于 0 和 64 之间")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Length(min=0, max=64, message="地址长度必须介于 0 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=64, message="客户共享长度必须介于 0 和 64 之间")
	public String getGongxiang() {
		return gongxiang;
	}

	public void setGongxiang(String gongxiang) {
		this.gongxiang = gongxiang;
	}
	
	@Length(min=0, max=64, message="团队浏览长度必须介于 0 和 64 之间")
	public String getTuanduiliulan() {
		return tuanduiliulan;
	}

	public void setTuanduiliulan(String tuanduiliulan) {
		this.tuanduiliulan = tuanduiliulan;
	}
	
	@Length(min=0, max=64, message="客户价值评估长度必须介于 0 和 64 之间")
	public String getJiazhipg() {
		return jiazhipg;
	}

	public void setJiazhipg(String jiazhipg) {
		this.jiazhipg = jiazhipg;
	}
	
	@Length(min=0, max=64, message="客户人员规模长度必须介于 0 和 64 之间")
	public String getGuimo() {
		return guimo;
	}

	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}
	
	@Length(min=0, max=64, message="客户类别(企业或个人)长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<OaCustomerRelation> getOaCustomerRelationList() {
		return oaCustomerRelationList;
	}

	public void setOaCustomerRelationList(List<OaCustomerRelation> oaCustomerRelationList) {
		this.oaCustomerRelationList = oaCustomerRelationList;
	}
	
	public String getIsOpenSea() {
		return isOpenSea;
	}

	public void setIsOpenSea(String isOpenSea) {
		this.isOpenSea = isOpenSea;
	}
	
	public String getOwnerNames() {
		return ownerNames;
	}

	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	public String getOaCustomerRmanagerReadNames() {
		return oaCustomerRmanagerReadNames;
	}

	public void setOaCustomerRmanagerReadNames(String oaCustomerRmanagerReadNames) {
		this.oaCustomerRmanagerReadNames = oaCustomerRmanagerReadNames;
	}

	public String getOaCustomerRmanagerWriteNames() {
		return oaCustomerRmanagerWriteNames;
	}

	public void setOaCustomerRmanagerWriteNames(String oaCustomerRmanagerWriteNames) {
		this.oaCustomerRmanagerWriteNames = oaCustomerRmanagerWriteNames;
	}
	
	public String getOaCustomerRmanagerReadIds() {
		return Collections3.extractToString(oaCustomerReadShareRelationList, "readUser.id", ",") ;
	}
	
	/**
	 * 设置共享只读人员ID
	 * @return
	 */
	public void setOaCustomerRmanagerReadIds(String ids) {
		this.oaCustomerReadShareRelationList = Lists.newArrayList();
		for (String id : StringUtils.split(ids, ",")){
			OaCustomerReadShareRelation entity = new OaCustomerReadShareRelation();
			entity.setId(IdGen.uuid());
			entity.setOaCustomerRead(this);
			entity.setReadUser(new User(id));
			this.oaCustomerReadShareRelationList.add(entity);
		}
	}
	
	public String getOaCustomerRmanagerWriteIds() {
		return Collections3.extractToString(oaCustomerWriteShareRelationList, "writeUser.id", ",") ;
	}
	
	/**
	 * 设置共享可写人员ID
	 * @return
	 */
	public void setOaCustomerRmanagerWriteIds(String ids) {
		this.oaCustomerWriteShareRelationList = Lists.newArrayList();
		for (String id : StringUtils.split(ids, ",")){
			OaCustomerWriteShareRelation entity = new OaCustomerWriteShareRelation();
			entity.setId(IdGen.uuid());
			entity.setOaCustomerWrite(this);
			entity.setWriteUser(new User(id));
			this.oaCustomerWriteShareRelationList.add(entity);
		}
	}
	
}