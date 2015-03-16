package com.nextransfer.dao;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.nextransfer.dao.common.GeneralDaoImpl;

/**
 * nextransfer 공통 (신규)
 * @author hance
 *
 */
@Repository
public class CommonDao extends GeneralDaoImpl
{
	/**
	 * 목록을 가져옴(페이징)
	 * @param HashMap
	 * @return List
	 */
	public List<HashMap> selectByList(String xmlSql, HashMap search, int sPage, int rowPage)
	{
		int startPage = (sPage - 1) * rowPage;

		RowBounds rowBounds = new RowBounds(startPage, rowPage);
		
		List<HashMap> list = super.getSqlSession().selectList(xmlSql, search, rowBounds);
		return list;
	}
	
	/**
	 * 목록을 가져옴(페이징X)
	 * @param HashMap
	 * @return List
	 */
	public List<HashMap> selectByList(String xmlSql, HashMap search)
	{
		List<HashMap> list = super.getSqlSession().selectList(xmlSql, search);
		return list;
	}
}
