package com.youtube.dao;

import javax.naming.*;
import javax.sql.*;

/**
 * This class returns the Oracle database connect object from
 * a CentOS Oracle Express Virtual Machine
 * 
 * The method and variable in this class are static to save resources
 * You only need one instance of this class running.
 * 
 * This was explained in Part 3 of the Java Rest Tutorial Series on YouTube
 * 
 * @author 308tube
 *
 */
public class Oracle308tube {

	private static DataSource Oracle308tube = null; //hold the database object
	private static Context context = null; //used to lookup the database connection in weblogic
	
	/**
	 * This is a public method that will return the 308tube database connection.
	 * 
	 * @return Database object
	 * @throws Exception
	 */
	public static DataSource Oracle308tubeConn() throws Exception {
		
		/**
		 * check to see if the database object is already defined...
		 * if it is, then return the connection, no need to look it up again.
		 */
		if (Oracle308tube != null) {
			return Oracle308tube;
		}
		
		try {
			
			/**
			 * This only needs to run one time to get the database object.
			 * context is used to lookup the database object in weblogic
			 * Oracle308tube will hold the database object
			 */
			if (context == null) {
				context = new InitialContext();
			}
			
			Oracle308tube = (DataSource) context.lookup("308tubeOracle");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return Oracle308tube;
		
	}
	
}
