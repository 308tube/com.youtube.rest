package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;

/**
 * This class is used to manage computer parts inventory.
 * 
 * @author 308tube
 */
@Path("/v1/inventory") //removed * wildcard to make this more compatible with tomcat
public class V1_inventory {

	/**
	 * This method will return all computer parts that are listed
	 * in PC_PARTS table.
	 * 
	 * Note: This is a good method for a tutorial but probably should never
	 * has a method that returns everything from a database.  There should be
	 * built in limits.
	 * 
	 * @return - JSON array string
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;	
		
		try {
			conn = Oracle308tube.Oracle308tubeConn().getConnection();
			query = conn.prepareStatement("select * " +
											"from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			 if (conn != null) conn.close();
		}
		
		return rb;
	}
	
}
