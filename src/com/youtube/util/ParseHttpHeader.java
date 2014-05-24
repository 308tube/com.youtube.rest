package com.youtube.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;

public class ParseHttpHeader {

	/**
	 * This method will parse the http header and put it into a json object
	 * We are using json because the header content is quite dynamic in what 
	 * content is in the header
	 * 
	 * @param hsr
	 * @return
	 * @throws Exception
	 */
	public static JSONObject parseHSR(HttpServletRequest hsr) throws Exception {
		
		JSONObject header_profile = new JSONObject();
		
		try {
			Enumeration<String> headerNames  = hsr.getHeaderNames();
			
			//loop through the header and save the values into a json object
			while (headerNames.hasMoreElements()) {
				//get header name and value
				String headerName = headerNames.nextElement();
				String headerValue = hsr.getHeader(headerName);
				//System.out.print("Header: " + headerName + " Value: " + headerValue); System.out.println("");
				
				//put in json object for later reference
				header_profile.put(headerName, headerValue);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		//System.out.println("parseHttpHeader: "+header_profile.toString());
		return header_profile;
	}
	
}
