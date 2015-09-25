package com.core.mapper;

import java.util.List;

import com.core.domain.OrmVO;

public interface OrmDao {

	public List<String> listTables(OrmVO vo);
	public List<OrmVO> listTableCols(OrmVO vo);
	
}
