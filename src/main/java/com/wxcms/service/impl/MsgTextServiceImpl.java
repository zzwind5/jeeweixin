package com.wxcms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxapi.process.MsgType;
import com.wxcms.domain.MsgBase;
import com.wxcms.domain.MsgText;
import com.wxcms.mapper.MsgBaseDao;
import com.wxcms.mapper.MsgTextDao;
import com.wxcms.service.MsgTextService;


@Service
public class MsgTextServiceImpl implements MsgTextService{

	@Autowired
	private MsgTextDao entityDao;
	
	@Autowired
	private MsgBaseDao baseDao;

	public MsgText getById(String id){
		return entityDao.getById(id);
	}

	public List<MsgText> listForPage(MsgText searchEntity){
		return entityDao.listForPage(searchEntity);
	}

	public void add(MsgText entity){
		
		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setCreatetime(new Date());
		base.setMsgtype(MsgType.Text.toString());
		baseDao.add(base);
		
		entity.setBaseId(base.getId());
		entityDao.add(entity);
	}

	public void update(MsgText entity){
		MsgBase base = baseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		baseDao.updateInputcode(base);
		entityDao.update(entity);
	}

	public void delete(MsgText entity){
		MsgBase base = new MsgBase();
		base.setId(entity.getBaseId());
		entityDao.delete(entity);
		baseDao.delete(entity);
	}

	//根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputCode){
		return entityDao.getRandomMsg(inputCode);
	}
	public MsgText getRandomMsg2(){
		return entityDao.getRandomMsg2();
	}
}

