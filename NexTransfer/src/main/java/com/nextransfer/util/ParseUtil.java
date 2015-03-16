package com.nextransfer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtil
{
	DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat formatter3 = new SimpleDateFormat("yy:MM:dd");
	Date convertedDate = null;
	String dateStr = null;
	int dateInt = 0;

	// String 형을 Date형으로 변환 후 다시 String 형으로 리턴
	public String ParseToString(String sDate)
	{
		try
		{
			convertedDate = (Date) formatter.parse(sDate);
			dateStr = formatter2.format(convertedDate);
			return dateStr;
		}
		catch ( Exception e )
		{
			return "";
		}
		
	}

	public Date StringToDate(String sDate) throws ParseException
	{
		convertedDate = (Date) formatter2.parse(sDate);

		return convertedDate;
	}

	public String DateToString(Date date)
	{

		dateStr = formatter2.format(date);
		return dateStr;
	}

	// String 형을 Date형으로 변환 후 다시 String 형으로 리턴
	public int ParseToInt(String sDate) throws ParseException
	{

		convertedDate = (Date) formatter.parse(sDate);
		dateInt = Integer.parseInt(formatter2.format(convertedDate));

		// convertedDate = formatter2.format(convertedDate);
		return dateInt;
	}

	// String 형을 Date형으로 변환 후 다시 String 형으로 리턴
	public String ParseToStringTime(String sDate) throws ParseException
	{

		convertedDate = (Date) formatter.parse(sDate);
		dateStr = formatter3.format(convertedDate);
		return dateStr;
	}

	public String newStringBySymbol(String oldString, String symbol)
	{
		String sNew = "";

		sNew = oldString.substring(0, 4) + symbol + oldString.substring(4, 6) + symbol + oldString.substring(6, 8);
		
		return sNew;
	}
}
