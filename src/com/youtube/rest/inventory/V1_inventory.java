package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;

@Path("/v1/inventory/*")
public class V1_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		
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
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			 if (conn != null) conn.close();
		}
		
		return returnString;
	}
	
}
