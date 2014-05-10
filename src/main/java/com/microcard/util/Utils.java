/**
 * 
 */
package com.microcard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuwei
 *
 */
public class Utils {
	
	public final static String regex = "(\\.gif|\\.png|\\.jpg|\\.jpeg)$";	
	
	public final static String ENCODED_MARK = "encoded@";
	
	private final static Pattern pattern = Pattern.compile(regex);	
	
	public static boolean isImage(String filename) {
		
		if(filename == null || filename.length() < 1) return false;
		
		Matcher matcher = pattern.matcher(filename);
		
		if(matcher.find()) return true;
		
		return false;
		
	}
	
	public static String encodeFileName(String inputFileName) {
		
		if(inputFileName == null || "".equals(inputFileName)) {
			
			return null;
		}
		
		if(inputFileName.lastIndexOf('.') == -1) return Utils.encode(inputFileName);
		
        String a = inputFileName.substring(0, inputFileName.lastIndexOf('.'));
        String b = inputFileName.substring(inputFileName.lastIndexOf('.') + 1);
        return ENCODED_MARK + Utils.encode(a) + '.' + b;		
	}
	
	public static String decodeFileName(String inputFileName) {
		
		if(inputFileName == null || "".equals(inputFileName)) {
			
			return null;
		}
		
		inputFileName = inputFileName.replace(ENCODED_MARK, "");
		
		if(inputFileName.lastIndexOf('.') == -1) return Utils.decode(inputFileName);
		
        String a = inputFileName.substring(0, inputFileName.lastIndexOf('.'));
        String b = inputFileName.substring(inputFileName.lastIndexOf('.') + 1);
        return Utils.decode(a) + '.' + b;		
	}	
	
	public static boolean isEncodedFile(String inputFileName) {
		
		if(inputFileName == null || "".equals(inputFileName)) {
			
			return false;
		}		
		if(inputFileName.indexOf(ENCODED_MARK) > -1) return true;
		
		return false;
	}
	
	public static String encode(String n) {
		
		byte[] rst = Base64.encode(n.getBytes()).getBytes();
		
		for(int i = 0 ; i < rst.length ; i++) {
			if(rst[i] == '/') rst[i] = '&';
		}
		
		return new String(rst);
		
	}
	
	public static String decode(String n) {
		
		byte[] rst = n.getBytes();

		for(int i = 0 ; i < rst.length ; i++) {
			if(rst[i] == '&') rst[i] = '/';
		}		
		return new String(Base64.decode(rst));
	}	
	
	 public static String convertToHex(byte[] data) {
		    StringBuffer hexString = new StringBuffer();
		    for (int i = 0; i < data.length; i++)
		        //hexString.append(Integer.toHexString(0xFF & data[i]));
		    	hexString.append(String.format("%02X", 0xFF & data[i]));
		    return hexString.toString();
		}

}
