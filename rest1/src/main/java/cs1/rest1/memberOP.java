package cs1.rest1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class memberOP {
	
	
	public static String requestadded(String username, String requestName) throws Exception {
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            // Connect to the database
            c = database.connect();

            if(requestName.equals("manager") || requestName.equals("admin")) {
            String checkSql1 = "SELECT COUNT(*) FROM request WHERE requestfrom = ? AND requestedresource IN ('admin', 'manager') AND requeststatus != 'rejected'";
            pstmt = c.prepareStatement(checkSql1);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // User has already made a request for 'admin' or 'manager'
                return "N";
            }

            // Reset PreparedStatement for INSERT operation
            pstmt.close();}
            
            
            
            // Check if the request already exists
            String checkSql = "SELECT COUNT(*) FROM request WHERE requestfrom = ? AND requestedresource = ? and requeststatus!='rejected'";
            pstmt = c.prepareStatement(checkSql);
            pstmt.setString(1, username);
            pstmt.setString(2, requestName);

            rs1 = pstmt.executeQuery();
            if (rs1.next() && rs1.getInt(1) > 0) {
                // Request already exists
                return "N";
            }

            // Reset PreparedStatement for INSERT operation
            pstmt.close();

            // SQL Insert Statement
            String insertSql = "INSERT INTO request (requestfrom, requestedresource, requeststatus) VALUES (?, ?, ?)";
            pstmt = c.prepareStatement(insertSql);
            pstmt.setString(1, username);
            pstmt.setString(2, requestName);
            pstmt.setString(3, "pending");

            // Execute the insert operation
            pstmt.executeUpdate();
            return "Y";

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
            // Return error message
            return "An error occurred while adding the request.";
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

	
        
        
		
	/*public static String checkResources(String username) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Prepare SQL query to select resources for the given username
	        String sql = "SELECT ResourceName FROM Resources WHERE Username = ?";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, username);

	        // Execute the query
	        rs = pstmt.executeQuery();

	        // Use StringBuilder to build the result string
	        StringBuilder resources = new StringBuilder();
	        while (rs.next()) {
	            String resourceName = rs.getString("ResourceName");
	            if (resources.length() > 0) {
	                resources.append(", ");
	            }
	            resources.append(resourceName);
	        }
	        
	        System.out.println(resources);
	        // Return the resources as a comma-separated string
	        return resources.toString();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Database error occurred while checking resources", e);
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	

}*/
	
	public static String checkResources(String username) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Prepare SQL query to select resources for the given username
	        String sql = "SELECT ResourceName FROM Resources WHERE Username = ?";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, username);

	        // Execute the query
	        rs = pstmt.executeQuery();

	        // Build the HTML table
	        StringBuilder tableBuilder = new StringBuilder(
	            "<!DOCTYPE html>" +
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "table {" +
	            "    width: 80%;" +
	            "    border-collapse: collapse;" +
	            "    margin: 20px auto;" +
	            "    font-family: Arial, sans-serif;" +
	            "}" +
	            "th, td {" +
	            "    border: 1px solid #ddd;" +
	            "    padding: 8px;" +
	            "    text-align: left;" +
	            "}" +
	            "th {" +
	            "    background-color: #f2f2f2;" +
	            "}" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<table>" +
	            "<tr>" +
	            "<th>Resource Name</th>" +
	            "</tr>"
	        );

	        // Append rows to the table
	        while (rs.next()) {
	            String resourceName = rs.getString("ResourceName");
	            tableBuilder.append("<tr>")
	                        .append("<td>").append(resourceName).append("</td>")
	                        .append("</tr>");
	        }

	        // Close the table and HTML tags
	        tableBuilder.append(
	            "</table>" +
	            "</body>" +
	            "</html>"
	        );

	        // Return the HTML table as a string
	        return tableBuilder.toString();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Database error occurred while checking resources", e);
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}






	/*public static String getnotavaibleResources(String username) throws Exception {
		// TODO Auto-generated method stub
		// Establish connection to the database
        Connection c = database.connect();
        
        // Prepare the SQL query to exclude the specified resource name
        String sql = "SELECT Resourcename FROM Resources WHERE Username != ?";
        PreparedStatement ps = c.prepareStatement(sql);
        
        // Set the parameter for the excluded resource name
        ps.setString(1, username);
        
        // Execute the query
        ResultSet rs = ps.executeQuery();
        
        // Build the dropdown HTML
        StringBuilder dropDown = new StringBuilder("<form action='requestp' method='post'><select name='resources'>");
        while (rs.next()) {
            String value = rs.getString(1);
            dropDown.append("<option value='").append(value).append("'>").append(value).append("</option>");
        }
        dropDown.append("</select><input type='submit'></form>");
        
        // Close the resources
        rs.close();
        ps.close();
        c.close();
        
        // Return the HTML dropdown
        return dropDown.toString();
	}*/
	
	public static String getnotavaibleResources(String username) throws Exception {
	    // Establish connection to the database
	    Connection c = database.connect();
	    
	    // Prepare the SQL query to exclude resources associated with the given username
	    String sql = "SELECT d.resourcename FROM resourcestable d WHERE NOT EXISTS ( SELECT 1  FROM resources r WHERE r.resourcename = d.resourcename AND r.username = ?)";
	    PreparedStatement ps = c.prepareStatement(sql);
	    
	    // Set the parameter for the excluded username
	    ps.setString(1, username);
	    
	    // Execute the query
	    ResultSet rs = ps.executeQuery();
	    
	    // Build the dropdown HTML with inline CSS
	    StringBuilder dropDown = new StringBuilder(
	        "<!DOCTYPE html>" +
	        "<html>" +
	        "<head>" +
	        "<style>" +
	        "body {" +
	        "    display: flex;" +
	        "    justify-content: center;" +
	        "    align-items: center;" +
	        "    height: 100vh;" +
	        "    margin: 0;" +
	        "    font-family: Arial, sans-serif;" +
	        "}" +
	        "form {" +
	        "    text-align: center;" +
	        "    border: 1px solid #ccc;" +
	        "    padding: 20px;" +
	        "    border-radius: 5px;" +
	        "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
	        "    background-color: #f9f9f9;" +
	        "}" +
	        "select {" +
	        "    font-size: 16px;" +
	        "    padding: 10px;" +
	        "    margin-bottom: 10px;" +
	        "    border: 1px solid #ddd;" +
	        "    border-radius: 5px;" +
	        "}" +
	        "input[type='submit'] {" +
	        "    font-size: 16px;" +
	        "    padding: 10px 20px;" +
	        "    border: none;" +
	        "    border-radius: 5px;" +
	        "    background-color: #4CAF50;" +
	        "    color: white;" +
	        "    cursor: pointer;" +
	        "}" +
	        "input[type='submit']:hover {" +
	        "    background-color: #45a049;" +
	        "}" +
	        "</style>" +
	        "</head>" +
	        "<body>" +
	        "<form action='resourcerequestsent' method='post'>" +
	        "<select name='resources'>"
	    );
	    
	    while (rs.next()) {
	        String value = rs.getString("Resourcename"); // Use column name for better clarity
	        dropDown.append("<option value='").append(value).append("'>").append(value).append("</option>");
	    }
	    
	    dropDown.append(
	        "</select>" +
	        "<input type='submit' value='Submit'>" +
	        "</form>" +
	        "</body>" +
	        "</html>"
	    );
	    
	    // Close the resources
	    rs.close();
	    ps.close();
	    c.close();
	    
	    // Return the HTML dropdown
	    return dropDown.toString();
	}





	public static String getapprovals(String username) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();
	        

	        // Prepare SQL query to select pending requests with additional filter on requestfrom
	        String sql = "SELECT requestid,requestedresource,requeststatus FROM request WHERE requestfrom = ?";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, username);
	       

	        // Execute the query
	        rs = pstmt.executeQuery();

	        // Build the HTML table
	        StringBuilder tableBuilder = new StringBuilder(
	            "<!DOCTYPE html>" +
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "table {" +
	            "    width: 80%;" +
	            "    border-collapse: collapse;" +
	            "    margin: 20px auto;" +
	            "    font-family: Arial, sans-serif;" +
	            "}" +
	            "th, td {" +
	            "    border: 1px solid #ddd;" +
	            "    padding: 8px;" +
	            "    text-align: left;" +
	            "}" +
	            "th {" +
	            "    background-color: #f2f2f2;" +
	            "}" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<table>" +
	            "<tr>" +
	            "<th>Request ID</th>" +
	           
	            "<th>Requested Resource</th>" +
	            "<th>Request Status</th>" +
	            "</tr>"
	        );

	        // Append rows to the table
	        while (rs.next()) {
	            int requestId = rs.getInt("requestid");
	            
	            String requestedResource = rs.getString("requestedresource");
	            String requestStatus = rs.getString("requeststatus");

	            tableBuilder.append("<tr>")
	                        .append("<td>").append(requestId).append("</td>")
	                        
	                        .append("<td>").append(requestedResource).append("</td>")
	                        .append("<td>").append(requestStatus).append("</td>")
	                        .append("</tr>");
	        }

	        // Close the table and HTML tags
	        tableBuilder.append(
	            "</table>" +
	            "</body>" +
	            "</html>"
	        );

	        // Return the HTML table as a string
	        return tableBuilder.toString();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Database error occurred while retrieving pending requests", e);
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}





	





	public static String checkavaibaleresources(String username) throws Exception {
		// TODO Auto-generated method stub
        Connection c = database.connect();
	    
	    // Prepare the SQL query to include resources associated with the given username
	    String sql = "SELECT Resourcename FROM Resources WHERE Username = ?";
	    PreparedStatement ps = c.prepareStatement(sql);
	    
	    // Set the parameter for the excluded username
	    ps.setString(1, username);
	    
	    // Execute the query
	    ResultSet rs = ps.executeQuery();
	    
	    // Build the dropdown HTML with inline CSS
	    StringBuilder dropDown = new StringBuilder(
	        "<!DOCTYPE html>" +
	        "<html>" +
	        "<head>" +
	        "<style>" +
	        "body {" +
	        "    display: flex;" +
	        "    justify-content: center;" +
	        "    align-items: center;" +
	        "    height: 100vh;" +
	        "    margin: 0;" +
	        "    font-family: Arial, sans-serif;" +
	        "}" +
	        "form {" +
	        "    text-align: center;" +
	        "    border: 1px solid #ccc;" +
	        "    padding: 20px;" +
	        "    border-radius: 5px;" +
	        "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
	        "    background-color: #f9f9f9;" +
	        "}" +
	        "select {" +
	        "    font-size: 16px;" +
	        "    padding: 10px;" +
	        "    margin-bottom: 10px;" +
	        "    border: 1px solid #ddd;" +
	        "    border-radius: 5px;" +
	        "}" +
	        "input[type='submit'] {" +
	        "    font-size: 16px;" +
	        "    padding: 10px 20px;" +
	        "    border: none;" +
	        "    border-radius: 5px;" +
	        "    background-color: #4CAF50;" +
	        "    color: white;" +
	        "    cursor: pointer;" +
	        "}" +
	        "input[type='submit']:hover {" +
	        "    background-color: #45a049;" +
	        "}" +
	        "</style>" +
	        "</head>" +
	        "<body>" +
	        "<form action='resourceremoverequestsent' method='post'>" +
	        "<select name='resources'>"
	    );
	    
	    while (rs.next()) {
	        String value = rs.getString("Resourcename"); // Use column name for better clarity
	        dropDown.append("<option value='").append(value).append("'>").append(value).append("</option>");
	    }
	    
	    dropDown.append(
	        "</select>" +
	        "<input type='submit' value='Submit'>" +
	        "</form>" +
	        "</body>" +
	        "</html>"
	    );
	    
	    // Close the resources
	    rs.close();
	    ps.close();
	    c.close();
	    
	    // Return the HTML dropdown
	    return dropDown.toString();
	}





	public static void removeResources(String username, String resource) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Start a transaction
	        c.setAutoCommit(false);

	        try {
	            // SQL Delete Statement for resources table
	            String deleteResourcesSql = "DELETE FROM resources WHERE Username = ? AND ResourceName = ?";
	            pstmt = c.prepareStatement(deleteResourcesSql);
	            pstmt.setString(1, username);
	            pstmt.setString(2, resource);

	            // Execute the delete operation for resources table
	            int rowsAffectedResources = pstmt.executeUpdate();
	            
	            if (rowsAffectedResources > 0) {
	                System.out.println("Resource removed successfully from resources table.");
	            } else {
	                System.out.println("No matching resource found in resources table for the user.");
	            }
	            
	            // Close previous PreparedStatement
	            pstmt.close();

	            // SQL Delete Statement for requests table
	            String deleteRequestsSql = "DELETE FROM request WHERE requestfrom = ? AND requestedresource = ?";
	            pstmt = c.prepareStatement(deleteRequestsSql);
	            pstmt.setString(1, username);
	            pstmt.setString(2, resource);

	            // Execute the delete operation for requests table
	            int rowsAffectedRequests = pstmt.executeUpdate();
	            
	            if (rowsAffectedRequests > 0) {
	                System.out.println("Resource removed successfully from requests table.");
	            } else {
	                System.out.println("No matching resource found in requests table for the user.");
	            }
	            
	            // Commit the transaction
	            c.commit();

	        } catch (SQLException e) {
	            // Rollback transaction in case of error
	            c.rollback();
	            e.printStackTrace(); // Print stack trace for debugging
	            throw new Exception("Database error occurred while removing resource", e);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace for debugging
	        throw new Exception("Database connection error occurred", e);
	    } finally {
	        // Clean up resources
	        try {
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}






	public static String getManager(String username) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();
	        
	        // Prepare SQL query to select manager name based on username
	        String sql = "SELECT managername FROM userdetails WHERE username = ?";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, username);

	        // Execute the query
	        rs = pstmt.executeQuery();

	        // Build the HTML table
	        StringBuilder tableBuilder = new StringBuilder(
	            "<!DOCTYPE html>" +
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "table {" +
	            "    width: 50%;" +
	            "    border-collapse: collapse;" +
	            "    margin: 20px auto;" +
	            "    font-family: Arial, sans-serif;" +
	            "}" +
	            "th, td {" +
	            "    border: 1px solid #ddd;" +
	            "    padding: 8px;" +
	            "    text-align: left;" +
	            "}" +
	            "th {" +
	            "    background-color: #f2f2f2;" +
	            "}" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<table>" +
	            "<tr>" +
	            "<th>Username</th>" +
	            "<th>Manager</th>" +
	            "</tr>"
	        );

	        // Append rows to the table
	        if (rs.next()) {
	            String managerName = rs.getString("managername");

	            tableBuilder.append("<tr>")
	                        .append("<td>").append(username).append("</td>")
	                        .append("<td>").append(managerName).append("</td>")
	                        .append("</tr>");
	        } else {
	            // If no result is found, display a message
	            tableBuilder.append("<tr>")
	                        .append("<td colspan='2'>No manager found for the specified username.</td>")
	                        .append("</tr>");
	        }

	        // Close the table and HTML tags
	        tableBuilder.append(
	            "</table>" +
	            "</body>" +
	            "</html>"
	        );

	        // Return the HTML table as a string
	        return tableBuilder.toString();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Database error occurred while retrieving manager information", e);
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}





}
