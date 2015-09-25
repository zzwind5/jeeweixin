package com.cms.service;

import java.util.List;
import com.core.page.Pagination;
import com.cms.domain.SysUser;


public interface SysUserService {

	public SysUser getById(String id);
	
	public SysUser getLoginUser(SysUser user);

	public List<SysUser> list(SysUser searchEntity);

	public Pagination<SysUser> listPage(SysUser searchEntity ,Pagination<SysUser> page);

	public void add(SysUser entity);

	public void update(SysUser entity);

	public void delete(SysUser entity);



}