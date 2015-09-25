package com.wxapi.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wxapi.process.ErrCode;
import com.wxapi.process.MsgXmlUtil;
import com.wxapi.process.OAuthScope;
import com.wxapi.process.WxApi;
import com.wxapi.service.impl.MyServiceImpl;
import com.wxapi.util.SignUtil;
import com.wxapi.vo.MsgRequest;
import com.wxapi.vo.Token;
import com.wxcms.domain.Account;
import com.wxcms.domain.AccountFans;


/**
 * 微信与开发者服务器交互接口
 * 如果是GET 请求，那么是进行 URL 、Tocken 认证；
 * 如果是POST 请求，那么是消息交互
 * 
 */

@Controller
@RequestMapping("/wxapi")
public class WxApiCtrl {
	
	@Autowired
	private MyServiceImpl myService;
	
	/**
	 * GET请求：进行URL、Tocken 认证；
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 */
	@RequestMapping(value = "/{account}/message",  method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request,@PathVariable String account) {
		Account actTmp = myService.getByAccount(account);//获取account
		if(actTmp != null){
			String tocken = actTmp.getToken();//获取token，进行验证；
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			
			// 校验成功返回  echostr，成功成为开发者；否则返回error，接入失败
			if (SignUtil.validSign(signature, tocken, timestamp, nonce)) {
				return echostr;
			}
		}
		return "error";
	}
	
	/**
	 * POST 请求：进行消息处理；
	 * */
	@RequestMapping(value = "/{account}/message", method = RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request,@PathVariable String account,HttpServletResponse response) {
		//处理用户和微信公众账号交互消息
		Account accountObj = myService.getByAccount(account);//获取account
		try {
			MsgRequest msgRequest = MsgXmlUtil.parseXml(request);//获取发送的消息
			return myService.processMsg(msgRequest,accountObj);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	//创建微信公众账号菜单
	@RequestMapping(value = "/publishMenu")
	public ModelAndView publishMenu(HttpServletRequest request,String gid) {
		JSONObject rstObj = null;
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			rstObj = myService.publishMenu(gid, actTmp.getAppid(),actTmp.getAppsecret());
			if(rstObj != null && rstObj.getInt("errcode") == 0){
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("successMsg", "创建菜单成功");
				return mv;
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		String failureMsg = "创建菜单失败，请检查菜单：可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单。";
		if(rstObj != null){
			failureMsg += ErrCode.errMsg(rstObj.getInt("errcode"));
		}
		mv.addObject("failureMsg", failureMsg);
		return mv;
	}
	
	//删除微信公众账号菜单
	@RequestMapping(value = "/deleteMenu")
	public ModelAndView deleteMenu(HttpServletRequest request) {
		JSONObject rstObj = null;
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			rstObj = myService.deleteMenu(actTmp.getAppid(),actTmp.getAppsecret());
			if(rstObj != null && rstObj.getInt("errcode") == 0){
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("successMsg", "删除菜单成功");
				return mv;
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		String failureMsg = "删除菜单失败";
		if(rstObj != null){
			failureMsg += ErrCode.errMsg(rstObj.getInt("errcode"));
		}
		mv.addObject("failureMsg", failureMsg);
		return mv;
	}
	
	//获取用户列表
	@RequestMapping(value = "/syncAccountFansList")
	public ModelAndView syncAccountFansList(){
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			boolean flag = myService.syncAccountFansList(actTmp.getAppid(),actTmp.getAppsecret());
			if(flag){
				return new ModelAndView("redirect:/accountfans/paginationEntity.html");
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", "获取用户列表失败");
		return mv;
	}
	
	//根据用户的ID更新用户信息
	@RequestMapping(value = "/syncAccountFans")
	public ModelAndView syncAccountFans(String openId){
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			AccountFans fans = myService.syncAccountFans(openId,actTmp.getAppid(),actTmp.getAppsecret(),true);
			if(fans != null){
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("failureMsg", "获取用户信息成功");
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", "获取用户信息失败");
		return mv;
	}
	
	//上传图文消息
	@RequestMapping(value = "/uploadNews")
	public  ModelAndView uploadNews(String[] newIds){
		Account actTmp = myService.getSingleAccount();//获取account
		String rstMsg = "上传图文消息失败";
		if(actTmp != null && newIds.length > 0){
			JSONObject rstObj = myService.uploadMsgNews(newIds,actTmp.getAppid(),actTmp.getAppsecret());
			if(rstObj.getInt("errcode") == 0){
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("successMsg", "上传图文消息成功");
				return mv;
			}else{
				rstMsg = ErrCode.errMsg(rstObj.getInt("errcode"));
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", rstMsg);
		return mv;
	}
	
	//群发图文消息
	@RequestMapping(value = "/sendMsgNewsAll")
	public  ModelAndView sendMsgNewsAll(String[] newIds){
		Account actTmp = myService.getSingleAccount();//获取account
		String rstMsg = "发送图文消息失败";
		if(actTmp != null && newIds.length > 0){
			JSONObject rstObj = myService.sendMsgNewsAll(newIds, actTmp.getAppid(),actTmp.getAppsecret());
			if(rstObj.getInt("errcode") == 0){
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("successMsg", "发送图文消息失败");
				return mv;
			}else{
				rstMsg = ErrCode.errMsg(rstObj.getInt("errcode"));
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", rstMsg);
		return mv;
	}
	
	//OAuth2.0 接口
	@RequestMapping(value = "/oauthsns")
	public ModelAndView oauthsns(HttpServletRequest request){
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			ModelAndView mv = new ModelAndView("common/oauthsns");
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			String state = "";
			String openidUrl = WxApi.getOAuthCodeUrl(actTmp.getAppid(), url + "/wxapi/oauthOpenid.html", OAuthScope.Base.toString(), state);
			mv.addObject("openidUrl", openidUrl);
			
			String userinfoUrl = WxApi.getOAuthCodeUrl(actTmp.getAppid(), url + "/wxapi/oauthUserinfo.html", OAuthScope.Userinfo.toString(), state);
			mv.addObject("userinfoUrl", userinfoUrl);
			return mv;
		}else{
			ModelAndView mv = new ModelAndView("common/failureMobile");
			mv.addObject("message", "微信公众账号没有此权限");
			return mv;
		}
	}
	
	//获取openid
	@RequestMapping(value = "/oauthOpenid")
	public ModelAndView oauthOpenid(String code,String state){
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			ModelAndView mv = new ModelAndView("common/oauthOpenid");
			Token token = WxApi.getOAuthToken(actTmp.getAppid(), actTmp.getAppsecret(), code);
			//业务处理
			
			mv.addObject("token", token);
			return mv;
		}else{
			ModelAndView mv = new ModelAndView("common/failureMobile");
			mv.addObject("message", "公众账号没有此权限");
			return mv;
		}
	}
	
	//获取userinfo
	@RequestMapping(value = "/oauthUserinfo")
	public ModelAndView oauthUserinfo(String code,String state){
		Account actTmp = myService.getSingleAccount();//获取account
		if(actTmp != null){
			ModelAndView mv = new ModelAndView("common/oauthUserinfo");
			JSONObject userinfo = WxApi.getOAuthUserinfo(actTmp.getAppid(), actTmp.getAppsecret(), code);
			//业务处理
			
			mv.addObject("userinfo", userinfo);
			return mv;
		}else{
			ModelAndView mv = new ModelAndView("common/failureMobile");
			mv.addObject("message", "公众账号没有此权限");
			return mv;
		}
	}
	
}



