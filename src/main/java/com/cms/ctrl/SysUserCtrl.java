package com.cms.ctrl;

import java.util.List;
import com.core.page.Pagination;
import com.core.util.PasswordUtil;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cms.domain.SysUser;
import com.cms.service.SysUserService;


@Controller
@RequestMapping("/cms/sysUser")
public class SysUserCtrl{

	@Autowired
	private SysUserService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id){
		ModelAndView mv = new ModelAndView("cms/sysUser");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/list")
	public  ModelAndView list(SysUser searchEntity){
		ModelAndView mv = new ModelAndView("cms/sysUserList");
		List<SysUser> list = entityService.list(searchEntity);
		mv.addObject("pageList",list);
		mv.addObject("cur_nav", "user");
		return mv;
	}

	@RequestMapping(value = "/listPage")
	public  ModelAndView listPage(SysUser searchEntity , Pagination<SysUser> page){
		ModelAndView mv = new ModelAndView("cms/sysUserPage");
		page = entityService.listPage(searchEntity,page);
		mv.addObject("pagination",page);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(SysUser entity){
		ModelAndView mv = new ModelAndView("cms/sysUserMerge");
		if(entity != null && entity.getId() != null){
			mv.addObject("entity",entityService.getById(entity.getId().toString()));
		}else{
			mv.addObject("entity",new SysUser());
		}
		mv.addObject("cur_nav", "user");
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(SysUser entity){
		if(entity.getId() == null){
			entity.setPassword(PasswordUtil.encryptPassword("123456"));
			entityService.add(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/cms/sysUser/list.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(SysUser entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/cms/sysUser/list.html");
	}



}