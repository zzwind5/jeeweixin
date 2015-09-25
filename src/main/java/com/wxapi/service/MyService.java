package com.wxapi.service;

import net.sf.json.JSONObject;

import com.wxapi.vo.MsgRequest;
import com.wxcms.domain.Account;
import com.wxcms.domain.AccountFans;

/**
 * 
 */

public interface MyService {
	
	//消息处理
	public String processMsg(MsgRequest msgRequest,Account account);

	//消息处理
	public String processMsg(MsgRequest msgRequest,String appId, String appSecret);
	
	//发布菜单
	public JSONObject publishMenu(String gid,String appId, String appSecret);
	
	//删除菜单
	public JSONObject deleteMenu(String appId, String appSecret);
	
	//获取用户列表
	public boolean syncAccountFansList(String appId,String appSecret);
	
	//获取单个用户信息
	public AccountFans syncAccountFans(String openId, String appId, String appSecret, boolean merge);
	
	//上传图文消息
	public JSONObject uploadMsgNews(String[] newIds,String appId,String appSecret);
	
	//群发消息
	public JSONObject sendMsgNewsAll(String[] newIds,String appId,String appSecret);
	
	
	//wxcms method
	
	//获取Account
	public Account getByAccount(String account);
	
	//获取唯一的 account
	public Account getSingleAccount();
	
	//根据openid 获取粉丝，如果没有，同步粉丝
	public AccountFans getFansByOpenId(String openid,String appId,String appSecret);
	
}
