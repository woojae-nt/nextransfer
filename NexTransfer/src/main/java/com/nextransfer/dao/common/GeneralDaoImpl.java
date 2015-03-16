package com.nextransfer.dao.common;

import java.util.*;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 범용 DAO
 * @author wimy
 *
 */
public class GeneralDaoImpl extends SqlSessionDaoSupport implements GeneralDao
{
	/**
	 * 테스트용 public 다른 클래스는 private을 사용할 것
	 */
	public static final Logger logger = LoggerFactory.getLogger(GeneralDaoImpl.class);
	
	/**
	 * 1x1 레코드 결과를 가져옵니다.
	 * @param sqlMapID
	 * @param obj
	 * @return
	 */
	public Object selectOne(String sqlMapID, Object obj)
	{
		Object result = getSqlSession().selectOne(sqlMapID, obj);
		return result;
	}
	

	/**
	 * 검색 레코드 수 
	 * search.paging.totalCount 세팅 포함
	 */
	@Override
	public int getCountBySearch(String sqlMapID, HashMap search)
	{
		int iCount = 0;
		iCount = getSqlSession().selectOne(sqlMapID, search);
		//search.getPaging().setTotalCount(iCount);
		return iCount;
	}

	/**
	 * 한 건의 모델을 가져옵니다.
	 * 없을 경우 null을 반환합니다.
	 */
	@Override
	public Object selectByKey(String sqlMapID, Object key)
	{
		List<?> list = getSqlSession().selectList(sqlMapID, key);
		if(list.size() == 0)
			return null;
		else
			return list.get(0);
			
	}

	
	/**
	 * 목록 가져오기
	 * 	- 호출될 때 카운트 쿼리도 같이 실행. search.paging.totalCount에 매핑
	 *  - sqlMapID가 맞아야 함.
	 *  
	 *  @param sqlMapID
	 *  @param search  
	 */
	@Override
	public List<?> selectBySearch(String sqlMapID, HashMap search)
	{
		return this.selectBySearch(sqlMapID, search, "totalCount");
	}
	
	/**
	 * 
	 * @param sqlMapID
	 * @param search
	 * @param countQueryID 카운트 쿼리 id를 넣을 경우 해당 쿼리를 실행하여  search.paging.totalCount에 결과값 매핑. 빈 값을 넣으면 카운트 쿼리를 실행하지 않음
	 * @return
	 */
	public List<?> selectBySearch(String sqlMapID, HashMap search, String countQueryID)
	{
		if(!StringUtils.isEmpty(countQueryID))
		{
			try
			{
				String mapID = sqlMapID.substring(0, sqlMapID.indexOf(".") + 1) +  countQueryID;
				int iCount = this.getCountBySearch(mapID, search);
				//search.getPaging().setTotalCount(iCount);
			}
			catch(Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			
		}
			
		return (List<?>)getSqlSession().selectList(sqlMapID, search);
	}
	
	@Override
	public int insert(String sqlMapID, HashMap model)
	{
		return getSqlSession().insert(sqlMapID, model);
	}

	@Override
	public int update(String sqlMapID, HashMap model)
	{
		return getSqlSession().update(sqlMapID, model);
	}

	@Override
	public int delete(String sqlMapID, Object key)
	{
		return getSqlSession().delete(sqlMapID, key);
	}

}
