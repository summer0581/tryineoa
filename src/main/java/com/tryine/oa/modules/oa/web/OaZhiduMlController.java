/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.oa.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tryine.oa.common.config.Global;
import com.tryine.oa.common.web.BaseController;
import com.tryine.oa.common.utils.StringUtils;
import com.tryine.oa.modules.oa.entity.OaZhiduContent;
import com.tryine.oa.modules.oa.entity.OaZhiduMl;
import com.tryine.oa.modules.oa.service.OaZhiduMlService;
import com.tryine.oa.modules.sys.entity.User;

/**
 * 规章制度目录维护Controller
 * @author Summer
 * @version 2015-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaZhiduMl")
public class OaZhiduMlController extends BaseController {

	@Autowired
	private OaZhiduMlService oaZhiduMlService;
	
	@ModelAttribute
	public OaZhiduMl get(@RequestParam(required=false) String id) {
		OaZhiduMl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaZhiduMlService.get(id);
		}
		if (entity == null){
			entity = new OaZhiduMl();
		}
		return entity;
	}
	
	@RequestMapping(value = {"oaZhiduMlIndex"})
	public String index(OaZhiduContent oaZhiduContent, Model model) {
		return "modules/oa/oaZhiduMlIndex";
	}
	
	@RequiresPermissions("oa:oaZhiduMl:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaZhiduMl oaZhiduMl, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OaZhiduMl> list = oaZhiduMlService.findList(oaZhiduMl); 
		model.addAttribute("list", list);
		return "modules/oa/oaZhiduMlList";
	}

	@RequiresPermissions("oa:oaZhiduMl:view")
	@RequestMapping(value = "form")
	public String form(OaZhiduMl oaZhiduMl, Model model) {
		if (oaZhiduMl.getParent()!=null && StringUtils.isNotBlank(oaZhiduMl.getParent().getId())){
			oaZhiduMl.setParent(oaZhiduMlService.get(oaZhiduMl.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(oaZhiduMl.getId())){
				OaZhiduMl oaZhiduMlChild = new OaZhiduMl();
				oaZhiduMlChild.setParent(new OaZhiduMl(oaZhiduMl.getParent().getId()));
				List<OaZhiduMl> list = oaZhiduMlService.findList(oaZhiduMl); 
				if (list.size() > 0){
					oaZhiduMl.setSort(list.get(list.size()-1).getSort());
					if (oaZhiduMl.getSort() != null){
						oaZhiduMl.setSort(oaZhiduMl.getSort() + 30);
					}
				}
			}
		}
		if (oaZhiduMl.getSort() == null){
			oaZhiduMl.setSort(30);
		}
		model.addAttribute("oaZhiduMl", oaZhiduMl);
		return "modules/oa/oaZhiduMlForm";
	}

	@RequiresPermissions("oa:oaZhiduMl:edit")
	@RequestMapping(value = "save")
	public String save(OaZhiduMl oaZhiduMl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaZhiduMl)){
			return form(oaZhiduMl, model);
		}
		oaZhiduMlService.save(oaZhiduMl);
		addMessage(redirectAttributes, "保存规章制度目录维护成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaZhiduMl/?repage";
	}
	
	@RequiresPermissions("oa:oaZhiduMl:edit")
	@RequestMapping(value = "delete")
	public String delete(OaZhiduMl oaZhiduMl, RedirectAttributes redirectAttributes) {
		oaZhiduMlService.delete(oaZhiduMl);
		addMessage(redirectAttributes, "删除规章制度目录维护成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaZhiduMl/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<OaZhiduMl> list = oaZhiduMlService.findList(new OaZhiduMl());
		for (int i=0; i<list.size(); i++){
			OaZhiduMl e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}