package com.nextransfer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * General Exception Process Class
 * servlet-context.xml 설정 참조 * 
 * @author wimy
 *
 */
@Service
public class ExceptionResolver implements HandlerExceptionResolver
{
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	
	private String _view = null;
	 
	 
	public void setView(String view) 
	{
		this._view = view;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception)
	{
		//exception.printStackTrace();
		logger.error(exception.getMessage(), exception);
		this.setView("/common/error");
		request.setAttribute("exception", exception);
		return new ModelAndView(this._view);
	}

}
