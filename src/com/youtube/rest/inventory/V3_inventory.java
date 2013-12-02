package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.youtube.dao.Schema308tube;

@Path("/v3/inventory")
public class V3_inventory {

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts2(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Schema308tube dao = new Schema308tube();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println( "jsonData: " + partsData.toString() );
			
			int http_code = dao.insertIntoPC_PARTS(partsData.optString("PC_PARTS_TITLE"), 
														partsData.optString("PC_PARTS_CODE"), 
														partsData.optString("PC_PARTS_MAKER"), 
														partsData.optString("PC_PARTS_AVAIL"), 
														partsData.optString("PC_PARTS_DESC") );
			
			if( http_code == 200 ) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been entered successfully, Version 3");
				returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(500).entity("Unable to enter Item").build();
			}
			
			System.out.println( "returnString: " + returnString );
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
}
