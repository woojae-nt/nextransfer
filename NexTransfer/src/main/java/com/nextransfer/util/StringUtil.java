package com.nextransfer.util;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nhncorp.lucy.security.xss.XssFilter;

public class StringUtil
{
	private static Logger logger = Logger.getLogger(StringUtil.class);
	
	private static long dummy = 0l;
	static DecimalFormat df = new DecimalFormat("#,##0");
	
	public static String getDateToString()
	{
		return getDateToString("yyyy-MM-dd");
	}
	
	public static String getDateToString(String pattern)
	{
		return getDateToString(new Date(), pattern);
	}
	
	public static String getDateToString(Date date, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date getDateFromString(String sDate, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			return sdf.parse(sDate);
		}
		catch ( ParseException e )
		{
			return null;
		}
	}
	
	
	public static String getRandomString(int length) throws Exception
    {
        try
		{
			String s = null;
			Random random = new Random();
			long l = random.nextLong();
			l %= 0x3b9aca00L;
			s = (new DecimalFormat("000000000")).format(Math.abs(l));
			return s;
		}
		catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
    }

	/**
	 * 문자열 오른쪽 패딩
	 * @param value 적용시킬 문자열
	 * @param width 패딩 길이
	 * @param paddingChar 채울 문자
	 * @return 
	 */
	public static String padRight(String value, int width, char paddingChar)
    {
		try
		{
			if(value.length() >= width)
				return value;
			
			String sReturn = value;
			while(sReturn.length() < width)
				sReturn = sReturn + String.valueOf(paddingChar);
			
			return sReturn;
		}
		catch(Exception e)
		{
			return "";
		}
    }
	
	/**
	 * 문자열 왼쪽 패딩
	 * @param value 적용시킬 문자열
	 * @param width 패딩 길이
	 * @param paddingChar 채울 문자
	 * @return 왼쪽으로 패딩된 문자열 
	 */
	public static String padLeft(String value, int width, char paddingChar)
	{
		try
		{
			if(value.length() >= width)
				return value;
			
			String sReturn = value;
			while(sReturn.length() < width)
				sReturn = String.valueOf(paddingChar) + sReturn;
			
			return sReturn;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	/**
	 * 시간 값을 이용하여 20자리 고유 문자열 반환
	 * @return 숫자형태의 문자
	 */
	public static synchronized String getUniqueNumber()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //17 characters
		String sReturn = sdf.format(Calendar.getInstance().getTime());
		try
		{
			++dummy;
		}
		catch(Exception e)
		{
			dummy = 0;
		}
		sReturn += padLeft(String.valueOf(dummy % 1000), 3, '0');
		
		return sReturn;
	}
	
	/**
	 * XSS 문자열 정리 - HTML Escape 포함
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) 
	{
		return cleanXSS(value, false);
	}
	
	/**
	 * XSS 문자열 정리 - 선택적
	 * @param value
	 * @param isOnlyScript - true : 스크립트 문자열만 제거 
	 * @return
	 */
	public static String cleanXSS(String value, boolean isOnlyScript)
	{
		if(StringUtils.isEmpty(value))
			return value;
		
		String sRtn = value;
		sRtn = sRtn.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
		sRtn = sRtn.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
		sRtn = sRtn.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
		sRtn = sRtn.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
		sRtn = sRtn.replaceAll("<script>", "");
		sRtn = sRtn.replaceAll("</script>", "");
		sRtn = sRtn.replaceAll("onmouseover", "");
		sRtn = sRtn.replaceAll("prompt", "");
		//sRtn = sRtn.replaceAll("../", "");
		//sRtn = sRtn.replaceAll("./", "");	
		sRtn = sRtn.replaceAll("'", "");
		sRtn = sRtn.replaceAll(";", "");
		sRtn = sRtn.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		sRtn = sRtn.replaceAll("(?i:s\\*c\\*r\\*i\\*p\\*t)", "");
		sRtn = sRtn.replaceAll("alert", "");
		sRtn = sRtn.replaceAll("(?i:a\\*l\\*e\\*r\\*t)", "");
		sRtn = sRtn.replaceAll("location\\.href", "");
		sRtn = sRtn.replaceAll("(?i:l\\*o\\*c\\*a\\*t\\*i\\*o\\*n)", "");
		
		if(!isOnlyScript)
		{
//			sRtn = sRtn.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//			sRtn = sRtn.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//			sRtn = sRtn.replaceAll("'", "& #39;");
//			sRtn = sRtn.replaceAll("eval\\((.*)\\)", "");
		}
		
		return sRtn;
	}
	
	/**
	 * XSS 파서 - HTML 태그 불가능, script 태그 불가능
	 * @param value
	 * @return
	 */
	public static String lucyXSS(String value)
	{
		return lucyXSS(value, true, false);
	}
	
	/**
	 * XSS 파서
	 * @param value
	 * @param disableHTMLTag - true: html 태그 사용 불가능, false: html 태그 사용 가능
	 * @return
	 */
	public static String lucyXSS(String value, boolean disableHTMLTag)
	{
		return lucyXSS(value, disableHTMLTag, false);
	}
	
	/**
	 * XSS 파서  
	 * @param value
	 * @param disableHTMLTag - true: html 태그 사용 불가능, false: html 태그 사용 가능
	 * @param disableXSSParse - true:XSS 파싱 안함, false: XSS 파싱 사용
	 * @return
	 */
	public static String lucyXSS(String value, boolean disableHTMLTag, boolean disableXSSParse)
	{
		if(disableXSSParse)
			return value;
		
		if(StringUtils.isEmpty(value))
			return value;
		
		try
		{
			String sParser = "../lucy-xss-enableTag.xml";
			if(disableHTMLTag)
				sParser = "../lucy-xss-disableTag.xml";
 			XssFilter filter = XssFilter.getInstance(sParser);
			
 			//변경되는 태그 앞에 생성되는 주석 문자열
			//<!-- Not Allowed Attribute Filtered -->
			//<!-- Not Allowed Tag Filtered --><
 			String sRtn = filter.doFilter(value);
			sRtn = sRtn.replaceAll("<!-- Not Allowed Attribute Filtered -->", "");
			sRtn = sRtn.replaceAll("<!-- Not Allowed Tag Filtered -->", "");
			
			return sRtn;
		}
		catch (Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			return value;
		}
	}

	public static String getDateYMD(String value)
	{
		String yy = value.substring(0,4);
		int mm = Integer.parseInt(value.substring(4,6));
		int dd = Integer.parseInt(value.substring(6,8));
//		String dd = value.substring(6,8);
		return yy+"년"+mm+"월"+dd+"일";
	}

	public static String getIp()throws Exception{

		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.getHostAddress();
		return ip;
	}
	
	public static String getPort(HttpServletRequest req)throws Exception{
		return Integer.toString(req.getServerPort());
	}
	
	public static String getHostName()throws Exception{
		InetAddress ia = InetAddress.getLocalHost();
		String host_name = ia.getCanonicalHostName();
		return host_name;
	}	
	
	public static String getServerName(HttpServletRequest req)throws Exception{
		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.getHostAddress();
		String server_nm = req.getServerName();
		if(!ip.equals("172.21.12.155") && !ip.equals("172.21.12.154"))server_nm=ip;
		return server_nm;
	}
	
	public static String nullCheck(String value){
		if(null == value){
			value = "-";
		}else{
			if( "".equals(value) || "null".equals(value) || "0".equals(value) || "0.00".equals(value) )
				value ="-";
		}
		
		return value;
		
	}
	
	public static String nullCheckDecimalFormat(String value){
		if(null == value || "null".equals(value))
			value = "0";
		
		value = df.format(Long.parseLong(value));
		
		if( "".equals(value) || "0".equals(value) || "0.00".equals(value) )
			value ="-";
		
		return value;
	}
		
	public static String nullCheck2(String value){
		if(null == value){
			value = "";
		}else{
			if("null".equals(value))
				value ="";
		}
		return value;
	}	
	
	public static String nullCheck(String value ,String addText){
		if(null == value){
			value = "-";
		}else{
			if(!("".equals(value) || "null".equals(value) || "0".equals(value)))
				value = value + addText;
			else
				value = "-";
		}

		return value;
		
	}
	
	public static String nullCheck(String value ,String addText, String addText2){
		if(null == value){
			value = "-";
		}else{
			if(!("".equals(value) || "null".equals(value) || "0".equals(value)))
				value = addText + value + addText2;
			else
				value = "-";
		}

		return value;
		
	}

	public static String nullCheckNum(String value){
		String arr[] = null;
		if(null == value){
			value = "";
		}else{
			if(!("".equals(value) || "null".equals(value) || "0".equals(value))){
				arr = value.split("[.]");
				if(arr.length == 2){
					if(arr[1].length() < 2){
						arr[1] = arr[1] + "0";
					}
					value = arr[0] + "." + arr[1];
				}else{
					value = value + ".00";
				}
			}else{
				value = "";
			}
		}

		return value;
		
	}
	
	public static String htmlRemove(String value){
		if(null == value){
			value = "-";
		}else{
			value = value.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		}
		
		return value;
	}
	
	 /**
	  * 숫자에 천단위마다 콤마 넣기
	  * @param int
	  * @return String
	  * */
	 public static String toNumFormat(Object object) {
		 if(object != null){
			 DecimalFormat df = new DecimalFormat("#,###");
			 return df.format(object);
		 }else{
			 return null;
		 }
	 }
}
