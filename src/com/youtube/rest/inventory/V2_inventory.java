package com.youtube.rest.inventory;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

@Path("/v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(
				@QueryParam("brand") String brand)
				throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
}
