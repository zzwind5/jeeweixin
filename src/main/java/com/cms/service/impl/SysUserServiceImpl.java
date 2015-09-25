package com.cms.service.impl;

import java.util.List;
import com.core.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.cms.domain.SysUser;
import com.cms.service.SysUserService;
import com.cms.mapper.SysUserDao;


@Service
public class SysUserServiceImpl implements SysUserService{

	@Autowired
	private SysUserDao entityDao;

	public SysUser getById(String id){
		return entityDao.getById(id);
	}

	public SysUser getLoginUser(SysUser user){
		return entityDao.getLoginUser(user);
	}
	
	public List<SysUser> list(SysUser searchEntity){
		return entityDao.list(searchEntity);
	}

	public Pagination<SysUser> listPage(SysUser searchEntity ,Pagination<SysUser> page){
		Integer totalItemsCount = entityDao.getPageTotalCount(searchEntity);
		List<SysUser> items = entityDao.listPage(searchEntity,page);
		page.setTotalItemsCount(totalItemsCount);
		page.setItems(items);
		return page;
	}

	public void add(SysUser entity){
		entityDao.add(entity);
	}

	public void update(SysUser entity){
		entityDao.update(entity);
	}

	public void delete(SysUser entity){
		entityDao.delete(entity);
	}



}