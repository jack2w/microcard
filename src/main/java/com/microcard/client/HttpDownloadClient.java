/**
 * 
 */
package com.microcard.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author jack
 *
 */
public class HttpDownloadClient extends HttpDefaultClient {

	private String filename;
	/**
	 * @param uri
	 * @throws URISyntaxException
	 * @throws MalformedURLException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public HttpDownloadClient(String uri,String file) throws URISyntaxException,
			MalformedURLException, KeyManagementException,
			NoSuchAlgorithmException {
		super(uri);
		this.filename = file;
	}

	/* (non-Javadoc)
	 * @see com.microcard.client.HttpDefaultClient#doGet()
	 */
	@Override
	public String doGet() throws KeyManagementException,
			NoSuchAlgorithmException, IOException {
		
    	HttpsURLConnection urlCon = (HttpsURLConnection)url.openConnection();
        urlCon.setRequestMethod("GET");
        urlCon.setDoInput(true);
        urlCon.connect();      
        msgLog.info("doGet() " + url.toString());
        
        File file = new File(filename);
        FileOutputStream stream = new FileOutputStream(file);
        InputStream input = urlCon.getInputStream();
        byte[] buffer = new byte[1000];
        int len = 0;
        len = input.read(buffer);
        while(len > 0) {
        	stream.write(buffer, 0, len);
        	len = input.read(buffer);
        }
        stream.close();
        try{
        	urlCon.disconnect();       	
           
        }catch(Exception e) {
        	//do nothing
        }
        return null;
	}

	/* (non-Javadoc)
	 * @see com.microcard.client.HttpDefaultClient#doPost(java.lang.String)
	 */
	@Override
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
        
        File file = new File(filename);
        FileOutputStream stream = new FileOutputStream(file);
        InputStream input = urlCon.getInputStream();
        byte[] buffer = new byte[1000];
        int len = 0;
        len = input.read(buffer);
        while(len > 0) {
        	stream.write(buffer, 0, len);
        	len = input.read(buffer);
        }
        stream.close();
        
        try{
        	urlCon.disconnect();       	
           
        }catch(Exception e) {
        	//do nothing
        }
        
        return null;
	}

}
