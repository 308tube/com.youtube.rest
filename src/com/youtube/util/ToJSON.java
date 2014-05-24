package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;

import org.owasp.esapi.ESAPI;

/**
 * This utility will convert a database data into JSON format.
 * Note:  this java class requires the ESAPI 1.4.4 jar file
 * ESAPI is used to encode data
 * 
 * @author 308tube
 */
public class ToJSON {

	/**
	 * This will convert database records into a JSON Array
	 * Simply pass in a ResultSet from a database connection and it
	 * loop return a JSON array.
	 * 
	 * It important to check to make sure that all DataType that are
	 * being used is properly encoding.
	 * 
	 * varchar is currently the only dataType that is being encode by ESAPI
	 * 
	 * @param rs - database ResultSet
	 * @return - JSON array
	 * @throws Exception
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {

        JSONArray json = new JSONArray(); //JSON array that will be returned
        String temp = null;

        try {

        	 //we will need the column names, this will save the table meta-data like column nmae.
             java.sql.ResultSetMetaData rsmd = rs.getMetaData();

             //loop through the ResultSet
             while( rs.next() ) {
            	 
            	 //figure out how many columns there are
                 int numColumns = rsmd.getColumnCount();
                 //each row in the ResultSet will be converted to a JSON Object
                 JSONObject obj = new JSONObject();

                 //loop through all the columns and place them into the JSON Object
                 for (int i=1; i<numColumns+1; i++) {

                     String column_name = rsmd.getColumnName(i);

                     if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    	 obj.put(column_name, rs.getArray(column_name));
                    	 /*Debug*/ System.out.println("ToJson: ARRAY");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                    	 /*Debug*/ System.out.println("ToJson: BIGINT");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    	 obj.put(column_name, rs.getBoolean(column_name));
                    	 /*Debug*/ System.out.println("ToJson: BOOLEAN");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    	 obj.put(column_name, rs.getBlob(column_name));
                    	 /*Debug*/ System.out.println("ToJson: BLOB");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    	 obj.put(column_name, rs.getDouble(column_name)); 
                    	 /*Debug*/ System.out.println("ToJson: DOUBLE");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    	 obj.put(column_name, rs.getFloat(column_name));
                    	 /*Debug*/ System.out.println("ToJson: FLOAT");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    	 obj.put(column_name, rs.getInt(column_name));
                    	 /*Debug*/ System.out.println("ToJson: INTEGER");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    	 obj.put(column_name, rs.getNString(column_name));
                    	 /*Debug*/ System.out.println("ToJson: NVARCHAR");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    	 
                    	 temp = rs.getString(column_name); //saving column data to temp variable
                    	 temp = ESAPI.encoder().canonicalize(temp); //decoding data to base state
                    	 temp = ESAPI.encoder().encodeForHTML(temp); //encoding to be browser safe
                    	 obj.put(column_name, temp); //putting data into JSON object
                    	 
                    	 //obj.put(column_name, rs.getString(column_name));
                    	 // /*Debug*/ System.out.println("ToJson: VARCHAR");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                    	 /*Debug*/ System.out.println("ToJson: TINYINT");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                    	 /*Debug*/ System.out.println("ToJson: SMALLINT");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    	 obj.put(column_name, rs.getDate(column_name));
                    	 /*Debug*/ System.out.println("ToJson: DATE");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    	 obj.put(column_name, rs.getTimestamp(column_name));
                    	 /*Debug*/ System.out.println("ToJson: TIMESTAMP");
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
                    	 obj.put(column_name, rs.getBigDecimal(column_name));
                    	 // /*Debug*/ System.out.println("ToJson: NUMERIC");
                      }
                     else {
                    	 obj.put(column_name, rs.getObject(column_name));
                    	 /*Debug*/ System.out.println("ToJson: Object "+column_name);
                     } 

                    }//end foreach
                 
                 json.put(obj);

             }//end while

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json; //return JSON array
	}
}
