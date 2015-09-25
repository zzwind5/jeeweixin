package com.cms.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.domain.SysUser;
import com.cms.service.SysUserService;
import com.core.util.PasswordUtil;
import com.core.util.SessionUser;
import com.core.util.SessionUtil;

@Controller
public class CmsCtrl {
	
	@Autowired
	private SysUserService userService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request){
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/tologin")
	public ModelAndView tologin(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("user", new SysUser());
		return mv;
	}
	
	@RequestMapping(value = "/dologin")
	public ModelAndView dologin(HttpServletRequest request ,SysUser user){
		String password=user.getPassword();
		user.setPassword(PasswordUtil.encryptPassword(password));
		SysUser loginUser = userService.getLoginUser(user);
		if(loginUser != null){
			SessionUser su = new SessionUser();
			su.setRealname(loginUser.getRealname());
			su.setEnable(loginUser.getEnable());
			su.setId(loginUser.getId());
			su.setLoginname(loginUser.getLoginname());
			su.setRuleid(loginUser.getRuleid());
			SessionUtil.setSessionUser(request, su);
			return new ModelAndView("redirect:/index.html");
		}else{
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("user",user);
			mv.addObject("errorFlag", "");
			return mv;
		}
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request){
		SessionUtil.removeSessionUser(request);
		return new ModelAndView("redirect:/tologin.html");
	}
	
}



