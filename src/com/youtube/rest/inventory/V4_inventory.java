package com.youtube.rest.inventory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("/v4/inventory")
public class V4_inventory {
//System.out.println("");
	
	/**
	 * This is a example of a jsonp resource call.
	 * To use jsonp with with jquery ajax jsonp, you will need to use
	 * 1. http GET method
	 * 2. a "callback" parameter in the url path
	 * 3. all data must be transmitted via url path
	 * 
	 * @param callback
	 * @param data
	 * @param clientRequest
	 * @return
	 * @throws Exception
	 */
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces({"application/x-javascript", MediaType.APPLICATION_JSON})
	public JSONWithPadding jsonpPost(
				@QueryParam("callback") String callback,
				@QueryParam("data") String data,
				@Context HttpServletRequest clientRequest)
				throws Exception {
		
		String returnString = null;
		
		try {
			
			//assuming the query parameter is in json format
			JSONObject obj = new JSONObject(data);
			System.out.println("obj: " + obj);
			
			//reflect back to user as a demo
			returnString = obj.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			JSONObject errorObj = new JSONObject();
			errorObj.put("code", "500");
			errorObj.put("message", "Internal Server Error");
			errorObj.put("error", e.getMessage());
			returnString = errorObj.toString();
			return new JSONWithPadding(returnString, callback);
		}
		return new JSONWithPadding (returnString, callback);
	}
	
}
