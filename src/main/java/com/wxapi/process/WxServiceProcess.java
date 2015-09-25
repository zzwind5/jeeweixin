package com.wxapi.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.wxapi.vo.Article;
import com.wxapi.vo.MsgRequest;
import com.wxapi.vo.MsgResponseNews;
import com.wxapi.vo.MsgResponseText;
import com.wxcms.domain.AccountFans;
import com.wxcms.domain.MsgNews;
import com.wxcms.domain.MsgText;

/**
 * 微信 服务处理，消息转换等
 * 
 */

public class WxServiceProcess {
	
	//获取 MsgResponseText 对象
	public static MsgResponseText getMsgResponseText(MsgRequest msgRequest,MsgText msgText){
		if(msgText != null){
			MsgResponseText reponseText = new MsgResponseText();
			reponseText.setToUserName(msgRequest.getFromUserName());
			reponseText.setFromUserName(msgRequest.getToUserName());
			reponseText.setMsgType(MsgType.Text.toString());
			reponseText.setCreateTime(new Date().getTime());
			reponseText.setContent(msgText.getContent());
			return reponseText;
		}else{
			return null;
		}
	}
	
	//获取 MsgResponseNews 对象
	public static MsgResponseNews getMsgResponseNews(MsgRequest msgRequest,List<MsgNews> msgNews){
		if(msgNews != null && msgNews.size() > 0){
			MsgResponseNews responseNews = new MsgResponseNews();
			responseNews.setToUserName(msgRequest.getFromUserName());
			responseNews.setFromUserName(msgRequest.getToUserName());
			responseNews.setMsgType(MsgType.News.toString());
			responseNews.setCreateTime(new Date().getTime());
			responseNews.setArticleCount(msgNews.size());
			List<Article> articles = new ArrayList<Article>(msgNews.size());
			for(MsgNews n : msgNews){
				Article a = new Article();
				a.setTitle(n.getTitle());
				a.setPicUrl(n.getPicpath());
				if(StringUtils.isEmpty(n.getFromurl())){
					a.setUrl(n.getUrl());
				}else{
					a.setUrl(n.getFromurl());
				}
				a.setDescription(n.getBrief());
				articles.add(a);
			}
			responseNews.setArticles(articles);
			return responseNews;
		}else{
			return null;
		}
	}
	
	//发布菜单
	public static JSONObject publishMenus(String menus,String appId,String appSecret){
		String token = WxApi.getToken(appId,appSecret).getAccessToken();
		String url = WxApi.getMenuCreateUrl(token);
		return WxApi.httpsRequest(url, HttpMethod.POST, menus);
	}
	
	//删除菜单
	public static JSONObject deleteMenu(String appId, String appSecret){
		String token = WxApi.getToken(appId,appSecret).getAccessToken();
		String url = WxApi.getMenuDeleteUrl(token);
		return WxApi.httpsRequest(url, HttpMethod.POST, null);
	}
	
	//根据openId获取粉丝信息
	public static AccountFans syncAccountFans(String openId,String appId,String appSecret){
		String token = WxApi.getToken(appId,appSecret).getAccessToken();
		String url = WxApi.getFansInfoUrl(token, openId);
		JSONObject jsonObj = WxApi.httpsRequest(url, "GET", null);
		if (null != jsonObj) {
			if(jsonObj.containsKey("errcode")){
				int errorCode = jsonObj.getInt("errcode");
				System.out.println(String.format("获取用户信息失败 errcode:{} errmsg:{}", errorCode, ErrCode.errMsg(errorCode)));
			}else{
				AccountFans fans = new AccountFans();
				fans.setSubscribeStatus(new Integer(jsonObj.getInt("subscribe")));// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				fans.setOpenId(jsonObj.getString("openid"));// 用户的标识
				fans.setGender(jsonObj.getInt("sex"));// 用户的性别（1是男性，2是女性，0是未知）
				fans.setNickname(jsonObj.getString("nickname"));// 昵称
				fans.setLanguage(jsonObj.getString("language"));// 用户的语言，简体中文为zh_CN
				fans.setCity(jsonObj.getString("city"));// 用户所在城市
				fans.setProvince(jsonObj.getString("province"));// 用户所在省份
				fans.setCountry(jsonObj.getString("country"));// 用户所在国家
				fans.setHeadimgurl(jsonObj.getString("headimgurl"));// 用户头像
				fans.setSubscribeTime(jsonObj.getString("subscribe_time"));// 用户关注时间
				fans.setRemark(jsonObj.getString("remark"));
				return fans;
			}
		}
		return null;
	}
	
	//上传图文消息
	public static JSONObject uploadNews(List<MsgNews> msgNewsList,String appId,String appSecret){
		JSONObject rstObj = new JSONObject();
		try{
			JSONArray jsonArr = new JSONArray();
			for(MsgNews news : msgNewsList){
				JSONObject jsonObj = new JSONObject();
				JSONObject mediaObj = WxApi.uploadMedia(MediaType.Image.toString(),news.getPicdir(), appId, appSecret);
				String mediaId = mediaObj.getString("media_id");
				jsonObj.put("thumb_media_id", mediaId);
				if(news.getAuthor() != null){
					jsonObj.put("author", news.getAuthor());
				}else{
					jsonObj.put("author", "");
				}
				if(news.getTitle() != null){
					jsonObj.put("title", news.getTitle());
				}else{
					jsonObj.put("title", "");
				}
				if(news.getFromurl() != null){
					jsonObj.put("content_source_url", news.getFromurl());
				}else{
					jsonObj.put("content_source_url", "");
				}
				if(news.getBrief() != null){
					jsonObj.put("digest", news.getBrief());
				}else{
					jsonObj.put("digest", "");
				}
				if(news.getShowpic() != null){
					jsonObj.put("show_cover_pic", news.getShowpic());
				}else{
					jsonObj.put("show_cover_pic", "0");
				}
				jsonObj.put("content", news.getDescription());
				jsonArr.add(jsonObj);
			}
			JSONObject postObj = new JSONObject();
			postObj.put("articles", jsonArr);
			
			String token = WxApi.getTokenUrl(appId, appSecret);
			rstObj = WxApi.httpsRequest(WxApi.getUploadNewsUrl(token), HttpMethod.POST, postObj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return rstObj;
	}
	
	//群发接口
	public static JSONObject sendAll(String mediaId,String msgType,String appId,String appSecret){
		JSONObject postObj = new JSONObject();
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", true);
		postObj.put("filter", filter);
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		postObj.put("mpnews", mpnews);
		postObj.put("msgtype", msgType);
		String token = WxApi.getTokenUrl(appId, appSecret);
		return WxApi.httpsRequest(WxApi.getUploadNewsUrl(token), HttpMethod.POST, postObj.toString());
	}
	
}



