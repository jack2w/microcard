/**
 * 
 */
package com.microcard.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class HttpDefaultClient {

	protected static Logger msgLog = Logger.getMsgLogger();
	//private static Logger operLog = Logger.getOperLogger();

    protected final URL url;
    
    protected String scheme;
    
/*    private String host;
    
    private int port;*/
    
    protected boolean ssl;
    

    public HttpDefaultClient(URI uri) throws MalformedURLException, NoSuchAlgorithmException, KeyManagementException {
        this.url = uri.toURL();
        
        scheme = uri.getScheme() == null? "http" : uri.getScheme();
/*        host = uri.getHost() == null? "localhost" : uri.getHost();
        port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
        	msgLog.error("send wrong url " + uri.toASCIIString());
            throw new IllegalArgumentException("url is illegal,should begin with http:// or https:// , now is " + uri.toASCIIString());
        }*/

        ssl = "https".equalsIgnoreCase(scheme);
        
    	if(ssl) {
            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS"); //或SSL
            TrustManager[] xtmArray = FakeTrustManagerFactory.getTrustManagers();
            sslContext.init(null, xtmArray, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory()); 	
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){

   			@Override
   			public boolean verify(String arg0, SSLSession arg1) {
   				//always verifys true
   				return true;
   			}});
       	}   
    }
    
    public HttpDefaultClient(String uri) throws URISyntaxException, MalformedURLException, KeyManagementException, NoSuchAlgorithmException {
    	this(new URI(uri));
    }	
    
    public String doGet() throws KeyManagementException, NoSuchAlgorithmException, IOException {
    	
    	HttpsURLConnection urlCon = (HttpsURLConnection)url.openConnection();
        urlCon.setRequestMethod("GET");
        urlCon.setDoInput(true);
        urlCon.connect();      
        msgLog.info("doGet() " + url.toString());
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(
        		urlCon.getInputStream()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        try{
            reader.close();
           
        }catch(Exception e) {
        	//do nothing
        }
        try{
        	urlCon.disconnect();       	
           
        }catch(Exception e) {
        	//do nothing
        }
        msgLog.info("received msg: "+ result.toString());
        return result.toString();  
    	
    }
	
    public String doPost(String content) throws IOException {
    	

    	HttpsURLConnection urlCon = (HttpsURLConnection)url.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setRequestMethod("POST");
        urlCon.setUseCaches(false);
        urlCon.setDoInput(true);
        byte[] bytes = null;
        if(content != null && content.length() > 0) {
        	bytes = content.getBytes("UTF-8");
            urlCon.setRequestProperty("Content-Length",String.valueOf(bytes.length));
        }else {
        	urlCon.setRequestProperty("Content-Length","0");
        }
        // 配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        //connection.setRequestProperty("Content-Type",
        //        "application/x-www-form-urlencoded");
        // 连接，从url.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        urlCon.connect();      
        msgLog.info("doPost() " + url.toString() + " content: " + content);
        if(bytes != null) 
           urlCon.getOutputStream().write(bytes);

        urlCon.getOutputStream().flush();
        urlCon.getOutputStream().close();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(
        		urlCon.getInputStream()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        try{
            reader.close();
           
        }catch(Exception e) {
        	//do nothing
        }
        try{
        	urlCon.disconnect();       	
           
        }catch(Exception e) {
        	//do nothing
        }
        msgLog.info("received msg: "+ result.toString());
        return result.toString();
    }
}
