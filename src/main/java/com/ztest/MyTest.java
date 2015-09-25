package com.ztest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.junit.JUnit4ClassBase;
import com.wxapi.vo.MsgRequest;
import com.wxcms.domain.Account;
import com.wxcms.service.AccountService;

public class MyTest extends JUnit4ClassBase{
	private Logger logger = Logger.getLogger(MyTest.class);  
	
	@Autowired
	private AccountService entityService;
	
    @Test  
    public void testUserShow() throws Exception{ 
        try{  
        	Account act = entityService.getById("1");
        	logger.info(act.getAppid());
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    

	
	public static void main(String[] args){
		String xml = "<xml><ToUserName><![CDATA[gh_02606721ab8e]]></ToUserName>"+
					"<FromUserName><![CDATA[oUig6t-5P5rZuWQZpp6SRO7odoDg]]></FromUserName>"+
					"<CreateTime>1407424506</CreateTime>"+
					"<MsgType><![CDATA[text]]></MsgType>"+
					"<Content><![CDATA[1]]></Content>"+
					"<MsgId>6044842225059613150</MsgId>"+
					"</xml>";
		
		InputStream  in_nocode = new ByteArrayInputStream(xml.getBytes());   
		
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(in_nocode);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			
			MsgRequest msgReq = new MsgRequest();
			
			// 遍历节点，封装成对象
			for (Element e : elementList){
				String name = e.getName();
				String text = e.getText();
				
				if("MsgType".equals(name)){
					msgReq.setMsgType(text);
				}else if("MsgId".equals(name)){
					msgReq.setMsgId(text);
				}else if("MsgId".equals(name)){
					msgReq.setMsgId(text);
				}else if("FromUserName".equals(name)){
					msgReq.setFromUserName(text);
				}else if("ToUserName".equals(name)){
					msgReq.setToUserName(text);
				}else if("CreateTime".equals(name)){
					msgReq.setCreateTime(text);
					
				}else if("Content".equals(name)){//文本消息
					msgReq.setContent(text);
				}else if("PicUrl".equals(name)){//图片消息
					msgReq.setPicUrl(text);
				}else if("Location_X".equals(name)){//地理位置消息
					msgReq.setLocation_X(text);
				}else if("Location_Y".equals(name)){
					msgReq.setLocation_Y(text);
				}else if("Scale".equals(name)){
					msgReq.setScale(text);
				}else if("Label".equals(name)){
					msgReq.setLabel(text);
				}else if("Event".equals(name)){//事件消息
					msgReq.setEvent(text);
				}else if("EventKey".equals(name)){
					msgReq.setEventKey(text);
				}
			}
			
			System.out.println(msgReq.getFromUserName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
}
