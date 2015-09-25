package com.cms.mapper;

import java.util.List;
import com.core.page.Pagination;
import com.cms.domain.SysUser;


public interface SysUserDao {

	public SysUser getById(String id);
	
	public SysUser getLoginUser(SysUser user);

	public List<SysUser> list(SysUser searchEntity);

	public Integer getPageTotalCount(SysUser searchEntity);

	public List<SysUser> listPage(SysUser searchEntity , Pagination<SysUser> page);

	public void add(SysUser entity);

	public void update(SysUser entity);

	public void delete(SysUser entity);



}