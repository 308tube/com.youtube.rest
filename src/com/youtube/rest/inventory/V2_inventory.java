package com.youtube.rest.inventory;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Schema308tube;

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
			
			if(brand == null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}
			
			Schema308tube dao = new Schema308tube();
			
			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand() throws Exception {
		
		return Response.status(400).entity("Error: please specify brand for this search").build();
	}
	*/
	
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(
				@PathParam("brand") String brand) 
				throws Exception {
		
		String returnString = null;
		
		JSONArray json = new JSONArray();
		
		try {
			
			Schema308tube dao = new Schema308tube();
			
			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}
