package com.nextransfer.dao.extension;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Override <code>org.apache.ibatis.datasource.pooled.PooledDataSource</code> for Encryption
 * @author wimy
 *
 */
public class BasicDataSourceExt extends PooledDataSource
{

	private static final Logger logger = LoggerFactory.getLogger(BasicDataSourceExt.class);
	
	@Override
	public void setUsername(String username)
	{
		try
		{
			super.setUsername(username);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	 

	@Override
	public void setPassword(String password)
	{
		try
		{
			super.setPassword(password);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void setUrl(String url)
	{
		try
		{
			super.setUrl(url);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
		
}
