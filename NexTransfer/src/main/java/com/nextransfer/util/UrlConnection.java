package com.nextransfer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Iterator;

/**
 * URL 연결 클래스
 */
public class UrlConnection
{
    public UrlConnection() {}
    
    /**
     * URL로 연결하여 결과 값을 가져온다.
     * 
     * @param strUrl 경로
     * @return 결과값
     */
    public static String connection(String strUrl)
    {
        java.net.URL url = null;;
        java.net.URLConnection httpConn = null;
        java.io.BufferedReader in = null;
        StringBuffer buf = new StringBuffer();

        try
        {
            url = new java.net.URL(strUrl);

            httpConn = url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);

            InputStream is = null;

            is = httpConn.getInputStream();

            in = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8 * 1024);

            String line = null;
            while ((line = in.readLine()) != null)
            {
                buf.append(line + "\n");
            }
        }
        catch(MalformedURLException e)
        {
            System.out.println("UrlConnection urlConnection MalformedURLException : " + e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("UrlConnection urlConnection IOException : " + e.getMessage());
        }
        finally
        {
            try
            {
                if(in != null)
                {
                    in.close();
                }
            }
            catch(Exception e) {}
        }

        return buf.toString();
    }
    
    /**
     * URL로 연결하여 결과 값을 가져온다.(POST 방식)
     * 
     * @param strUrl 경로
     * @param paramMap 파라미터맵
     * @return 결과값
     */
    //public static String connectionByPost(String strUrl, java.util.Map<?, ?> paramMap)
    public static String connectionByPost(String strUrl, java.util.Map paramMap)
    {
        StringBuffer buf = new StringBuffer();
        java.io.OutputStreamWriter osw = null;

        try
        {
            java.net.URL url = new java.net.URL(strUrl);
            java.net.URLConnection urlc = url.openConnection();
            urlc.setDoOutput(true);
            osw = new java.io.OutputStreamWriter(urlc.getOutputStream());
            osw.write(makeParameters(paramMap));
            osw.flush();
            osw.close();

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(urlc.getInputStream(), "UTF-8"));
            String sRes = null;
            while((sRes = br.readLine()) != null)
            {
                buf.append(sRes + "\n");
            }
        }
        catch(IOException e)
        {
            System.out.println("UrlConnection urlConnectionByPost IOException : " + e.getMessage());
        }
        finally
        {
            try
            {
                if(osw != null)
                {
                    osw.close();
                }
            }
            catch(Exception e) {}
        }

        return buf.toString();
    }

    //private static String makeParameters(java.util.Map<?, ?> paramMap)
    private static String makeParameters(java.util.Map paramMap)
    {
        String paramName = null;
        String paramValue = null;
        StringBuffer buf = new StringBuffer();

        if(paramMap != null)
        {
        	//Iterator<?> iterator = paramMap.entrySet().iterator();
        	Iterator iterator = paramMap.entrySet().iterator();
        	while(iterator.hasNext())
        	{
        		//java.util.Map.Entry<?, ?> entry = (java.util.Map.Entry<?, ?>)iterator.next();
        		java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        		        		
        		paramName = entry.getKey().toString();
                paramValue = entry.getValue().toString();
                //StringUtil.printfE(paramName + " = " + paramValue);
                buf.append(paramName+"=");
                buf.append(paramValue+"&");
        	}  
        }

        return buf.toString();
    }
}