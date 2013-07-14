package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

/**
 * This java class will hold all the sql queries from episode 5 and onward.
 * V1_inventory.java and V1_status.java will not use this class for its sql code
 * since they were created before episode 5.
 * 
 * Having all sql/database code in one package makes it easier to maintain and audit
 * but increase complexity.
 * 
 * Note: we also used the extends Oracle308tube on this java class to inherit all
 * the methods in Oracle308tube.java
 * 
 * @author 308tube
 */
public class Schema308tube extends Oracle308tube {

	/**
	 * This method will search for a specific brand from the PC_PARTS table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandParts(String brand) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
											"from PC_PARTS " +
											"where UPPER(PC_PARTS_MAKER) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will search for the specific brand and the brands item number in
	 * the PC_PARTS table.
	 * 
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @param item_number - product item number
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandItemNumber(String brand, int item_number) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
											"from PC_PARTS " +
											"where UPPER(PC_PARTS_MAKER) = ? " +
											"and PC_PARTS_CODE = ?");
			
			/*
			 * protect against sql injection
			 * when you have more than one ?, it will go in chronological
			 * order.
			 */
			query.setString(1, brand.toUpperCase()); //first ?
			query.setInt(2, item_number); //second ?
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will return all PC parts.
	 * Done pre-episode 6
	 * 
	 * @return - all PC parts in json format
	 * @throws Exception
	 */
	public JSONArray queryAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
											"from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will return a time/stamp from database.
	 * Done pre-episode 6
	 * 
	 * @return time/stamp in json format
	 * @throws Exception
	 */
	public JSONArray queryCheckDbConnection() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') DATETIME " +
											"from sys.dual");
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
}
