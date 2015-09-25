package com.ztest.wxapi;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.core.junit.JUnit4HttpBase;
import com.ztest.MyTest;

public class UrlTokenTest extends JUnit4HttpBase{
	private Logger logger = Logger.getLogger(MyTest.class);  
	
	@Test
    public void run() throws Exception {
		logger.info("### url token start ");
		MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse(); 
        
        request.setParameter("signature", "mysignature");
        request.setParameter("timestamp", "0123456");
        request.setParameter("nonce", "mynonce");
        request.setParameter("echostr", "echostr");
        
        request.setMethod("GET");
        request.setRequestURI("/wxapi/zainanjing6/message");  
        
        this.excuteAction(request, response);
        System.out.println(response.getContentAsString());
        
        logger.info("### url token end ");
    }  
	
}

