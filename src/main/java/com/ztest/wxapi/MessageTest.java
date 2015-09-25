package com.ztest.wxapi;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.core.junit.JUnit4HttpBase;
import com.ztest.MyTest;

public class MessageTest extends JUnit4HttpBase{
	private Logger logger = Logger.getLogger(MyTest.class);  
	
	@Test
    public void run() throws Exception {
		logger.info("### message start ");
		MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse(); 
        
        request.setContent(getRequestTextMsg().getBytes());
        
        request.setMethod("POST");
        request.setRequestURI("/wxapi/zainanjing6/message");  
        
        this.excuteAction(request, response);
        System.out.println(response.getContentAsString());
        
        logger.info("### message end ");
    }  
	
	private String getRequestTextMsg(){
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[zainanjing6]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[brainqi]]></FromUserName> ");
		sb.append("<CreateTime>1348831860</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[hello]]></Content>");
		sb.append("<MsgId>1234567890123456</MsgId>");
		sb.append("</xml>");
		return sb.toString();
	}
	
}

