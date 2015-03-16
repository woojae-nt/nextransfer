package com.nextransfer.dao.common;

import java.util.HashMap;
import java.util.List;
/**
 * dao 클래스 일반화를 위한 인터페이스
 * 적용될 모델
 * @author wimy
 *
 */
public interface GeneralDao
{
	int getCountBySearch(String sqlMapID, HashMap model);
	
	Object selectByKey(String sqlMapID, Object key);
	
	//List<?> selectByObjects(String sqlMapID, Object...objects);
	
	List<?> selectBySearch(String sqlMapID, HashMap model);
	
	int insert(String sqlMapID, HashMap model);
	
	int update(String sqlMapID, HashMap model);
	
	int delete(String sqlMapID, Object key);
}
