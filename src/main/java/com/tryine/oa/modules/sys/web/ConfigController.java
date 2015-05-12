/**
 * Copyright &copy; 2012-2014 <a href="">TryineOA</a> All rights reserved.
 */
package com.tryine.oa.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tryine.oa.common.utils.CacheUtils;
import com.tryine.oa.common.web.BaseController;

/**
 * 配置Controller
 * @author Summer
 * @version 2015-5-7
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/config")
public class ConfigController extends BaseController {
	
	@RequestMapping(value = {"form", ""})
	public String form( HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/configForm";
	}
	
	@RequestMapping(value = {"clearAllCache"})
	public String clearAllCache( HttpServletRequest request, HttpServletResponse response, Model model) {
		CacheUtils.getCacheManager().clearAll();
		return "redirect:" + adminPath + "/sys/config/form?repage";
	}

}
