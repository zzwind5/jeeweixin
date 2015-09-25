package com.wxcms.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core.page.Pagination;
import com.core.util.HttpRequestDeviceUtils;
import com.wxcms.domain.MsgNews;
import com.wxcms.domain.MsgNewsVO;
import com.wxcms.service.MsgNewsService;

/**
 * 微信消息查看
 * 
 */
@Controller
@RequestMapping("/wxweb")
public class WxWebCtrl {

	@Autowired
	private MsgNewsService msgNewsService;
	
	@RequestMapping(value = "/msg/newsread")
	public ModelAndView newsread(HttpServletRequest request,String id){
		ModelAndView mv = new ModelAndView("wxweb/mobileNewsRead");
		MsgNews news = msgNewsService.getById(id);
		mv.addObject("news",news);
		
		if(!HttpRequestDeviceUtils.isMobileDevice(request)){
			mv.setViewName("wxweb/pcNewsRead");
		}
		return mv;
	}
	
	@RequestMapping(value = "/msg/newsList")
	public ModelAndView pageWebNewsList(HttpServletRequest request,MsgNews searchEntity,Pagination<MsgNews> page){
		ModelAndView mv = new ModelAndView("wxweb/mobileNewsList");
		List<MsgNewsVO> pageList = msgNewsService.pageWebNewsList(searchEntity,page);
		mv.addObject("pageList", pageList);
		return mv;
	}
	
	
}
