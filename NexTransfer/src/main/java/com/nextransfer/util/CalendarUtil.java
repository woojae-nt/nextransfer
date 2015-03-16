package com.nextransfer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil
{
	Calendar cal = Calendar.getInstance();
	Date today = new Date();
	String returnDate = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat format2 = new SimpleDateFormat("yy.MM.dd");
	Calendar startCal;
	Calendar endCal;
	
	public String today()
	{
		String returnDate = format.format(today);
		return returnDate;
	}

	public String getDay(int i)
	{
		cal.add(Calendar.DATE, i);
		returnDate = format.format(cal.getTime());
		return returnDate;

	}

	public String getMonth(int month)
	{
		cal.add(Calendar.MONTH, month);
		returnDate = format.format(cal.getTime());
		return returnDate;

	}

	public String getYear(int year)
	{
		cal.add(Calendar.YEAR, year);
		returnDate = format.format(cal.getTime());
		return returnDate;
	}

	// YYYYMMDD 에서 i 만큼의 날짜를 뺀 값을 return
	public String addDate(String d, int i)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = formatter.parse(d);
			
			Calendar temp_cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, i);
			
			returnDate = format.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return returnDate;
	}
	
	// YY.MM.DD 로 받는 경우
	public int getMonthDifference(String a, String b)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yy.MM.dd");
		int diffDays = 0;
		
		try {
			Date startDate = formatter.parse(a);
			Date endDate = formatter.parse(b);
		
			long diff = endDate.getTime() - startDate.getTime();
			diffDays = (int)( diff / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return diffDays;
	}

	// 날짜 차이 계산 (yyyyMMdd 포멧)
	public int getDayDifference(String a, String b)		// Format: YYYYMMDD 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		int diffDays = 0;
		
		try {
			Date startDate = formatter.parse(a);
			Date endDate = formatter.parse(b);
		
			long diff = endDate.getTime() - startDate.getTime();
			diffDays = (int)( diff / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diffDays;
	}
	
	// 날짜 포멧 변환 yyyyMMdd => yyyy.MM.dd
	public String convertDateFormat(String d)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy.MM.dd");
		try {
			Date date = formatter.parse(d);
			returnDate = formatter2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return returnDate;
	}
}
