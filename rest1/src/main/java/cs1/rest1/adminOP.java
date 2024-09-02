package cs1.rest1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

public class adminOP {

	
	
	
	
	/*public static String checkrequests() throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();
	        

	        // Prepare SQL query to select pending requests with additional filter on requestfrom
	        String sql = "SELECT * FROM request ";
	        pstmt = c.prepareStatement(sql);
	        

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
	            "form {" +
	            "    display: inline;" +
	            "}" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<table>" +
	            "<tr>" +
	            "<th>Request ID</th>" +
	            "<th>Request From</th>" +
	            "<th>Requested Resource</th>" +
	            "<th>Request Status</th>" +
	            "<th>Action</th>" +
	            "</tr>"
	        );

	        // Append rows to the table
	        while (rs.next()) {
	            int requestId = rs.getInt("requestid");
	            String requestFrom = rs.getString("requestfrom");
	            String requestedResource = rs.getString("requestedresource");
	            String requestStatus = rs.getString("requeststatus");

	            tableBuilder.append("<tr>")
	                        .append("<td>").append(requestId).append("</td>")
	                        .append("<td>").append(requestFrom).append("</td>")
	                        .append("<td>").append(requestedResource).append("</td>")
	                        .append("<td>").append(requestStatus).append("</td>")
	                        .append("<td>")
	                        .append("<form action='/approve' method='post'>")
	                        .append("<input type='hidden' name='requestid' value='").append(requestId).append("'/>")
	                        .append("<input type='hidden' name='requestfrom' value='").append(requestFrom).append("'/>")
	                        .append("<input type='hidden' name='requestedresource' value='").append(requestedResource).append("'/>")
	                        .append("<input type='hidden' name='requeststatus' value='").append(requestStatus).append("'/>")
	                        .append("<input type='submit' value='Approve'/>")
	                        .append("</form>")
	                        .append("<form action='/reject' method='post'>")
	                        .append("<input type='hidden' name='requestid' value='").append(requestId).append("'/>")
	                        .append("<input type='hidden' name='requestfrom' value='").append(requestFrom).append("'/>")
	                        .append("<input type='hidden' name='requestedresource' value='").append(requestedResource).append("'/>")
	                        .append("<input type='hidden' name='requeststatus' value='").append(requestStatus).append("'/>")
	                        .append("<input type='submit' value='Reject'/>")
	                        .append("</form>")
	                        .append("</td>")
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
	}*/
	
	
	public static String checkrequests() throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Prepare SQL query to select all pending requests
	        String pending = "pending";
	        String sql = "SELECT * FROM request WHERE requeststatus = ?";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, pending);

	        // Execute the query
	        rs = pstmt.executeQuery();

	        // Build the HTML table with smaller size and reduced column widths
	        StringBuilder tableBuilder = new StringBuilder(
	            "<!DOCTYPE html>" +
	            "<html>" +
	            "<head>" +
	            "<style>" +
	            "body {" +
	            "    font-family: Arial, sans-serif;" +
	            "    margin: 0;" +
	            "    padding: 0;" +
	            "    background-color: #f4f4f4;" +
	            "}" +
	            "table {" +
	            "    width: 80%;" +   // Adjusted table width
	            "    margin: 20px auto;" +
	            "    border-collapse: collapse;" +
	            "    background-color: #ffffff;" +
	            "    box-shadow: 0 4px 8px rgba(0,0,0,0.1);" +
	            "}" +
	            "th, td {" +
	            "    border: 1px solid #ddd;" +
	            "    padding: 10px;" +   // Adjusted padding
	            "    text-align: left;" +
	            "}" +
	            "th {" +
	            "    background-color: #4CAF50;" +
	            "    color: white;" +
	            "}" +
	            "tr:nth-child(even) {" +
	            "    background-color: #f2f2f2;" +
	            "}" +
	            "tr:hover {" +
	            "    background-color: #e0e0e0;" +
	            "}" +
	            "form {" +
	            "    display: inline;" +
	            "}" +
	            "input[type='submit'] {" +
	            "    padding: 6px 10px;" +   // Adjusted button padding
	            "    border: none;" +
	            "    border-radius: 4px;" +
	            "    color: white;" +
	            "    cursor: pointer;" +
	            "    font-size: 12px;" +   // Adjusted font size
	            "}" +
	            "input[type='submit'].approve {" +
	            "    background-color: #4CAF50;" +
	            "}" +
	            "input[type='submit'].reject {" +
	            "    background-color: #f44336;" +
	            "}" +
	            "input[type='submit']:hover {" +
	            "    opacity: 0.8;" +
	            "}" +
	            "th:nth-child(1), td:nth-child(1) {" +
	            "    width: 10%;" +   // Reduced width for columns
	            "}" +
	            "th:nth-child(2), td:nth-child(2) {" +
	            "    width: 20%;" +
	            "}" +
	            "th:nth-child(3), td:nth-child(3) {" +
	            "    width: 30%;" +
	            "}" +
	            "th:nth-child(4), td:nth-child(4) {" +
	            "    width: 20%;" +
	            "}" +
	            "th:nth-child(5), td:nth-child(5) {" +
	            "    width: 20%;" +
	            "}" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<table>" +
	            "<tr>" +
	            "<th>Request ID</th>" +
	            "<th>Request From</th>" +
	            "<th>Requested Resource</th>" +
	            "<th>Request Status</th>" +
	            "<th>Action</th>" +
	            "</tr>"
	        );

	        // Append rows to the table
	        while (rs.next()) {
	            int requestId = rs.getInt("requestid");
	            String requestFrom = rs.getString("requestfrom");
	            String requestedResource = rs.getString("requestedresource");
	            String requestStatus = rs.getString("requeststatus");

	            tableBuilder.append("<tr>")
	                        .append("<td>").append(requestId).append("</td>")
	                        .append("<td>").append(requestFrom).append("</td>")
	                        .append("<td>").append(requestedResource).append("</td>")
	                        .append("<td>").append(requestStatus).append("</td>")
	                        .append("<td>")
	                        .append("<form action='aprroverequest' method='post'>")
	                        .append("<input type='hidden' name='requestid' value='").append(requestId).append("'/>")
	                        .append("<input type='hidden' name='requestfrom' value='").append(requestFrom).append("'/>")
	                        .append("<input type='hidden' name='requestedresource' value='").append(requestedResource).append("'/>")
	                        .append("<input type='hidden' name='requeststatus' value='").append(requestStatus).append("'/>")
	                        .append("<input type='submit' class='approve' value='Approve'/>")
	                        .append("</form>")
	                        .append("<form action='rejectrequest' method='post'>")
	                        .append("<input type='hidden' name='requestid' value='").append(requestId).append("'/>")
	                        .append("<input type='hidden' name='requestfrom' value='").append(requestFrom).append("'/>")
	                        .append("<input type='hidden' name='requestedresource' value='").append(requestedResource).append("'/>")
	                        .append("<input type='hidden' name='requeststatus' value='").append(requestStatus).append("'/>")
	                        .append("<input type='submit' class='reject' value='Reject'/>")
	                        .append("</form>")
	                        .append("</td>")
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

	public static String approveRequests(String requestfrom, String requestedresource, @Context HttpServletRequest request) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;
	    PreparedStatement pstmt3 = null;
	    PreparedStatement pstmt4 = null;
	    PreparedStatement pstmt5 = null;
	    String resultMessage = "Request updated successfully";

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Prepare SQL query to update the request status to 'approved'
	        String sql = "UPDATE request SET requeststatus = ? WHERE requestfrom = ? AND requestedresource = ? AND requeststatus = 'pending'";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, "approved");
	        pstmt.setString(2, requestfrom);
	        pstmt.setString(3, requestedresource);

	        // Execute the update
	        int rowsAffected = pstmt.executeUpdate();

	        // Check if any rows were updated
	        if (rowsAffected == 0) {
	            resultMessage = "No matching request found or request was already approved.";
	        }
	        else {
	            // Prepare SQL query to insert the approved request into the resources table
	        	
	            
	            
	            if ("manager".equalsIgnoreCase(requestedresource) || "admin".equalsIgnoreCase(requestedresource)) {
	                // Prepare SQL query to update usertype in userdetails table
	            	System.out.println("MANAGER");
	            	 HttpSession session = request.getSession();
	            	String username=session.getAttribute("username").toString();
	                String updateSql = "UPDATE userdetails SET usertype = ? , managername= ? WHERE username = ?";
	                pstmt2 = c.prepareStatement(updateSql);
	                pstmt2.setString(1, requestedresource);
	                pstmt2.setString(2,username);
	                pstmt2.setString(3, requestfrom);
	                
	                // Execute the update operation
	                pstmt2.executeUpdate();
	            }
	            
	            
	            
	            else if ("ManagerToAdmin".equals(requestedresource)) {
	                // Prepare SQL query to update usertype in userdetails table
	                String updateSql1 = "UPDATE userdetails SET usertype = ? WHERE username = ?";
	                pstmt3 = c.prepareStatement(updateSql1);
	                pstmt3.setString(1, "admin");
	                pstmt3.setString(2, requestfrom);

	                // Execute the update operation
	                pstmt3.executeUpdate();
	                
	                
	                
	                HttpSession session = request.getSession();
	                String teammember=session.getAttribute("teammember").toString();
	                String username=session.getAttribute("username").toString();
	                String updateSql2 = "UPDATE userdetails SET usertype = ?, managername = ? WHERE username = ? ";
	                pstmt4 = c.prepareStatement(updateSql2);
	                pstmt4.setString(1, "manager");
	                pstmt4.setString(2, username);
	                pstmt4.setString(3, teammember);
	                pstmt4.executeUpdate();
	                
	                String updateSql3 = "UPDATE userdetails SET managername = ? WHERE managername = ?";
	                pstmt5 = c.prepareStatement(updateSql3);

	                // Set parameters
	                pstmt5.setString(1, teammember); // New managername
	                pstmt5.setString(2, requestfrom);

	                // Execute the update operation
	                pstmt5.executeUpdate();
	                
	            }
	            else
	            {
	            	String insertSql = "INSERT INTO resources (resourcename, username) VALUES (?, ?)";
		            pstmt1 = c.prepareStatement(insertSql);
		            pstmt1.setString(1, requestedresource);
		            pstmt1.setString(2, requestfrom);

		            // Execute the insert operation
		            pstmt1.executeUpdate();
	            }
	            c.commit();
	            
	            // Commit the transaction
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        resultMessage = "Database error occurred while updating the request status.";
	    } finally {
	        // Close resources
	        try {
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Return result message
	    return resultMessage;
	}


	public static String rejectRequests(String requestfrom, String requestedresource) throws Exception {
		// TODO Auto-generated method stub
		Connection c = null;
	    PreparedStatement pstmt = null;
	    String resultMessage = "Request updated successfully";

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Prepare SQL query to update the request status to 'approved'
	        String sql = "UPDATE request SET requeststatus = ? WHERE requestfrom = ? AND requestedresource = ? AND requeststatus = 'pending'";
	        pstmt = c.prepareStatement(sql);
	        pstmt.setString(1, "rejected");
	        pstmt.setString(2, requestfrom);
	        pstmt.setString(3, requestedresource);

	        // Execute the update
	        int rowsAffected = pstmt.executeUpdate();

	        // Check if any rows were updated
	        if (rowsAffected == 0) {
	            resultMessage = "No matching request found or request was already approved.";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        resultMessage = "Database error occurred while updating the request status.";
	    } finally {
	        // Close resources
	        try {
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Return result message
	    return resultMessage;
	}

	
	public static String addResource(String resourceName) throws Exception {
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // Check if the resource already exists
	        String checkSql = "SELECT COUNT(*) FROM resourcestable WHERE resourcename = ?";
	        pstmt = c.prepareStatement(checkSql);
	        pstmt.setString(1, resourceName);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return "Resource already exists."; // Return message if resource already exists
	            }
	        }

	        // Prepare SQL query to insert a new resource
	        String insertSql = "INSERT INTO resourcestable (resourcename) VALUES (?)";
	        pstmt = c.prepareStatement(insertSql);
	        pstmt.setString(1, resourceName);

	        // Execute the insert operation
	        pstmt.executeUpdate();
	        
	        return "Resource added successfully."; // Return message upon successful addition
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Database error occurred while adding the resource.", e);
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }}

		public static String checkresourcedetails() throws Exception {
			// TODO Auto-generated method stub
			
			
			    Connection c = null;
			    PreparedStatement pstmt = null;
			    ResultSet rs = null;
			    StringBuilder resultBuilder = new StringBuilder();

			    try {
			        // Connect to the database
			        c = database.connect();

			        // Prepare SQL query
			        String sql = "SELECT d.resourcename, COALESCE(COUNT(r.username), 0) AS number_of_users "
			                   + "FROM resourcestable d "
			                   + "LEFT JOIN resources r ON d.resourcename = r.resourcename "
			                   + "GROUP BY d.resourcename";
			        pstmt = c.prepareStatement(sql);

			        // Execute the query
			        rs = pstmt.executeQuery();

			        // Build the HTML table
			        resultBuilder.append("<!DOCTYPE html>")
			                     .append("<html>")
			                     .append("<head>")
			                     .append("<style>")
			                     .append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }")
			                     .append("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }")
			                     .append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }")
			                     .append("th { background-color: #4CAF50; color: white; }")
			                     .append("tr:nth-child(even) { background-color: #f2f2f2; }")
			                     .append("tr:hover { background-color: #e0e0e0; }")
			                     .append("</style>")
			                     .append("</head>")
			                     .append("<body>")
			                     .append("<table>")
			                     .append("<tr>")
			                     .append("<th>Resource Name</th>")
			                     .append("<th>Number of Users</th>")
			                     .append("</tr>");

			        // Append rows to the table
			        while (rs.next()) {
			            String resourceName = rs.getString("resourcename");
			            int numberOfUsers = rs.getInt("number_of_users");

			            resultBuilder.append("<tr>")
			                         .append("<td>").append(resourceName).append("</td>")
			                         .append("<td>").append(numberOfUsers).append("</td>")
			                         .append("</tr>");
			        }

			        // Close the table and HTML tags
			        resultBuilder.append("</table>")
			                     .append("</body>")
			                     .append("</html>");

			        // Return the HTML table as a string
			        return resultBuilder.toString();

			    } catch (SQLException e) {
			        e.printStackTrace();
			        throw new Exception("Database error occurred while retrieving resource details", e);
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
		
		
		public static void removeResource(String resourceName) throws Exception {
		    Connection c = null;
		    PreparedStatement pstmt = null;
		    PreparedStatement pstmt1 = null;

		    try {
		        // Connect to the database
		        c = database.connect();
		        
		        // Start a transaction
		        c.setAutoCommit(false);
		        
		        // Prepare SQL query to delete the resource from the resourcestable
		        String deleteResourceSql = "DELETE FROM resourcestable WHERE resourcename = ?";
		        pstmt = c.prepareStatement(deleteResourceSql);
		        pstmt.setString(1, resourceName);
		        int rowsAffected = pstmt.executeUpdate();
		        
		        if (rowsAffected > 0) {
		            // Prepare SQL query to delete the resource from the resource table
		            String deleteFromResourceSql = "DELETE FROM resources WHERE resourcename = ?";
		            pstmt1 = c.prepareStatement(deleteFromResourceSql);
		            pstmt1.setString(1, resourceName);
		            pstmt1.executeUpdate();
		            
		            // Commit the transaction
		            c.commit();
		            System.out.println("Resource removed successfully from both tables.");
		        } else {
		            System.out.println("No resource found with the specified name.");
		            // Rollback the transaction if no resource was found in the first table
		            c.rollback();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        if (c != null) {
		            try {
		                // Rollback the transaction in case of an error
		                c.rollback();
		            } catch (SQLException rollbackEx) {
		                rollbackEx.printStackTrace();
		            }
		        }
		        throw new Exception("Database error occurred while removing the resource.", e);
		    } finally {
		        // Ensure the connection is set back to auto-commit
		        try {
		            if (c != null) {
		                c.setAutoCommit(true);
		                if (pstmt != null) pstmt.close();
		                c.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		
		
		public static String checkusersforaresource(String resource) throws Exception {
		    Connection c = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    StringBuilder resultBuilder = new StringBuilder();

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Prepare SQL query
		        String sql = "SELECT username FROM resources WHERE resourcename = ?";
		        pstmt = c.prepareStatement(sql);
		        pstmt.setString(1, resource);

		        // Execute the query
		        rs = pstmt.executeQuery();

		        // Build the HTML table
		        resultBuilder.append("<!DOCTYPE html>")
		                     .append("<html>")
		                     .append("<head>")
		                     .append("<style>")
		                     .append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }")
		                     .append("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }")
		                     .append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }")
		                     .append("th { background-color: #4CAF50; color: white; }")
		                     .append("tr:nth-child(even) { background-color: #f2f2f2; }")
		                     .append("tr:hover { background-color: #e0e0e0; }")
		                     .append("</style>")
		                     .append("</head>")
		                     .append("<body>")
		                     .append("<table>")
		                     .append("<tr>")
		                     .append("<th>Username</th>")
		                     .append("</tr>");

		        // Append rows to the table
		        while (rs.next()) {
		            String username = rs.getString("username");

		            resultBuilder.append("<tr>")
		                         .append("<td>").append(username).append("</td>")
		                         .append("</tr>");
		        }

		        // Close the table and HTML tags
		        resultBuilder.append("</table>")
		                     .append("</body>")
		                     .append("</html>");

		        // Return the HTML table as a string
		        return resultBuilder.toString();

		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new Exception("Database error occurred while retrieving users for the resource", e);
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
		
		
		
		
		public static String checkresourcesofuser(String username) throws Exception {
		    Connection c = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    StringBuilder resultBuilder = new StringBuilder();

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Prepare SQL query
		        String sql = "SELECT resourcename FROM resources WHERE username = ?";
		        pstmt = c.prepareStatement(sql);
		        pstmt.setString(1, username);

		        // Execute the query
		        rs = pstmt.executeQuery();

		        // Build the HTML table
		        resultBuilder.append("<!DOCTYPE html>")
		                     .append("<html>")
		                     .append("<head>")
		                     .append("<style>")
		                     .append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }")
		                     .append("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }")
		                     .append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }")
		                     .append("th { background-color: #4CAF50; color: white; }")
		                     .append("tr:nth-child(even) { background-color: #f2f2f2; }")
		                     .append("tr:hover { background-color: #e0e0e0; }")
		                     .append("</style>")
		                     .append("</head>")
		                     .append("<body>")
		                     .append("<table>")
		                     .append("<tr>")
		                     .append("<th>Username</th>")
		                     .append("</tr>");

		        // Append rows to the table
		        while (rs.next()) {
		            String resource = rs.getString("resourcename");

		            resultBuilder.append("<tr>")
		                         .append("<td>").append(resource).append("</td>")
		                         .append("</tr>");
		        }

		        // Close the table and HTML tags
		        resultBuilder.append("</table>")
		                     .append("</body>")
		                     .append("</html>");

		        // Return the HTML table as a string
		        return resultBuilder.toString();

		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new Exception("Database error occurred while retrieving users for the resource", e);
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

		public static String displaytheresources() throws Exception {
		    // Establish connection to the database
		    Connection c = database.connect();
		    
		    // Prepare the SQL query to get resources with user count greater than 1
		    String sql = 
		        "SELECT d.resourcename " +
		        "FROM resourcestable d " +
		        "JOIN resources r ON d.resourcename = r.resourcename " +
		        "GROUP BY d.resourcename " +
		        "HAVING COUNT(r.username) >= 1";
		    
		    PreparedStatement ps = c.prepareStatement(sql);
		    
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
		        "<form action='displaytheusers' method='post'>" +
		        "<select name='resources'>"
		    );
		    
		    while (rs.next()) {
		        String value = rs.getString("resourcename"); // Use the correct column name
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

		
		public static String displaytheusers() throws Exception {
			 Connection c = database.connect();
			    
			    // Prepare the SQL query to get users with more than one resource
			    String sql = 
			        "SELECT r.username " +
			        "FROM resources r " +
			        "GROUP BY r.username " +
			        "HAVING COUNT(r.resourcename) >= 1";
			    
			    PreparedStatement ps = c.prepareStatement(sql);
			    
			    // Execute the query
			    ResultSet rs = ps.executeQuery();
			    
			    // Build the HTML with inline CSS
			    StringBuilder html = new StringBuilder(
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
			        "<form action='displaytheresources' method='post'>" +
			        "<select name='users'>"
			    );
			    
			    while (rs.next()) {
			        String username = rs.getString("username"); // Use the correct column name
			        html.append("<option value='").append(username).append("'>").append(username).append("</option>");
			    }
			    
			    html.append(
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
			    
			    // Return the HTML
			    return html.toString();
		}

		public static String displayResources() throws Exception {
		    Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    StringBuilder dropDown = new StringBuilder();

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Prepare the SQL query to get resources with no associated users
		        String sql = 
		            "SELECT d.resourcename " +
		            "FROM resourcestable d " ;
		            

		        ps = c.prepareStatement(sql);

		        // Execute the query
		        rs = ps.executeQuery();

		        // Build the dropdown HTML with inline CSS
		        dropDown.append(
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
		            "<form action='removeresource' method='post'>" +
		            "<select name='resources'>"
		        );

		        while (rs.next()) {
		            String value = rs.getString("resourcename"); // Use the correct column name
		            dropDown.append("<option value='").append(value).append("'>").append(value).append("</option>");
		        }

		        dropDown.append(
		            "</select>" +
		            "<input type='submit' value='Submit'>" +
		            "</form>" +
		            "</body>" +
		            "</html>"
		        );

		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new Exception("Database error occurred while retrieving resources with no users", e);
		    } finally {
		        // Close resources
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (c != null) c.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    // Return the HTML dropdown
		    return dropDown.toString();
		}

		public static String displaylistofusers() throws Exception {
			{
				 Connection c = database.connect();
				    
				    // Prepare the SQL query to get users with more than one resource
				    String sql = 
				        "SELECT DISTINCT(username) " 
				    		+
				        "FROM userdetails where userType='member' ";
				        
				    
				    PreparedStatement ps = c.prepareStatement(sql);
				    
				    // Execute the query
				    ResultSet rs = ps.executeQuery();
				    
				    // Build the HTML with inline CSS
				    StringBuilder html = new StringBuilder(
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
				        "<form action='removeuser1' method='post'>" +
				        "<select name='users'>"
				    );
				    
				    while (rs.next()) {
				        String username = rs.getString("username"); // Use the correct column name
				        html.append("<option value='").append(username).append("'>").append(username).append("</option>");
				    }
				    
				    html.append(
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
				    
				    // Return the HTML
				    return html.toString();
			}
		
		}

		 public static String removeUser( String username) throws Exception {
		        PreparedStatement pstmt = null;
		        Connection c=null;
		        try {
		            // SQL Delete Statement
		            String sql = "DELETE FROM userdetails WHERE username = ?";
                    c=database.connect();
		            pstmt = c.prepareStatement(sql);
		            pstmt.setString(1, username);

		            // Execute the delete operation
		            int rowsAffected = pstmt.executeUpdate();

		            if (rowsAffected > 0) {
		                return "User removed successfully."; // User was found and deleted
		            } else {
		                return "User not found."; // No user found with the given username
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return "Error removing user."; // Error during delete operation
		        } finally {
		            // Clean up resources
		            try {
		                if (pstmt != null) pstmt.close();
		                // Connection closing is handled by the caller
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
		    }
		 
		 public static String removeUser1(String username) throws Exception {
			    PreparedStatement pstmt = null;
			    Connection c = null;
			    try {
			        // Establish connection
			        c = database.connect(); // Assuming database.connect() returns a Connection object
			        
			        // Step 1: Check if the username exists in the resources table
			        String checkSql = "SELECT COUNT(*) FROM resources WHERE username = ?";
			        pstmt = c.prepareStatement(checkSql);
			        pstmt.setString(1, username);
			        
			        // Execute the select query
			        ResultSet rs = pstmt.executeQuery();
			        if (rs.next() && rs.getInt(1) > 0) {
			            // Username exists, proceed to delete all associated rows
			            pstmt.close(); // Close previous PreparedStatement
			            
			            // Step 2: Delete all resources associated with the given username
			            String deleteSql = "DELETE FROM resources WHERE username = ?";
			            pstmt = c.prepareStatement(deleteSql);
			            pstmt.setString(1, username);

			            // Execute the delete operation
			            int rowsAffected = pstmt.executeUpdate();
			            if (rowsAffected > 0) {
			                return "Resources removed successfully."; // Resources were found and deleted
			            } else {
			                return "No resources found for the given username."; // No resources were deleted
			            }
			        } else {
			            return "Username not found in resources."; // Username does not exist in the resources table
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        return "Error processing request."; // General error message
			    } finally {
			        // Clean up resources
			        try {
			            if (pstmt != null) pstmt.close();
			            if (c != null) c.close(); // Make sure to close the connection if it was opened
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			        }
			    }
			}


		public static String removeresourcefromuser1() throws Exception {
			 Connection c = database.connect();
			    
			    // Prepare the SQL query to get users with more than one resource
			    String sql = 
			        "SELECT r.username " +
			        "FROM resources r " +
			        "GROUP BY r.username " +
			        "HAVING COUNT(r.resourcename) >= 1";
			    
			    PreparedStatement ps = c.prepareStatement(sql);
			    
			    // Execute the query
			    ResultSet rs = ps.executeQuery();
			    
			    // Build the HTML with inline CSS
			    StringBuilder html = new StringBuilder(
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
			        "<form action='removeresourcefromuser2' method='post'>" +
			        "<select name='selecteduser'>"
			    );
			    
			    while (rs.next()) {
			        String username = rs.getString("username"); // Use the correct column name
			        html.append("<option value='").append(username).append("'>").append(username).append("</option>");
			    }
			    
			    html.append(
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
			    
			    // Return the HTML
			    return html.toString();
		
		}

		public static String removeresourcefromuser2(String suser) throws Exception {
		    // Initialize variables
		    Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    StringBuilder html = new StringBuilder();

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Prepare SQL query to get resources for the specified user
		        String sql = "SELECT resourcename FROM resources WHERE username = ?";
		        ps = c.prepareStatement(sql);
		        ps.setString(1, suser);

		        // Execute the query
		        rs = ps.executeQuery();

		        // Build the HTML with inline CSS
		        html.append(
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
		            "<form action='removeresourcefromuser3' method='post'>" +
		            "<h1>Remove Resource for User: ").append(suser).append("</h1>" +
		            "<input type='hidden' name='suser' value='").append(suser).append("' />" + 
		            "<select name='sresource'>"
		        );

		        // Append resources to dropdown
		        while (rs.next()) {
		            String resourceName = rs.getString("resourcename");
		            html.append("<option value='").append(resourceName).append("'>").append(resourceName).append("</option>");
		        }

		        html.append(
		            "</select>" +
		            "<input type='submit' value='Remove Resource'>" +
		            "</form>" +
		            "</body>" +
		            "</html>"
		        );

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return "Error fetching resources.";
		    } finally {
		        // Close the resources
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (c != null) c.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }

		    // Return the HTML
		    return html.toString();
		}

		public static String removeresourcefromuser3(String sresource, String suser) throws Exception {
			
			Connection c = null;
	        PreparedStatement ps = null;
	        PreparedStatement ps1 = null;

	        try {
	            // Connect to the database
	            c = database.connect();
	            	System.out.print(suser);
	            	System.out.print(sresource);
	            // SQL query to remove the resource from the user
	            String sql = "DELETE FROM resources WHERE username = ? AND resourcename = ?";
	            ps = c.prepareStatement(sql);
	            ps.setString(1, suser);
	            ps.setString(2, sresource);

	            // Execute the update
	            int rowsAffected = ps.executeUpdate();
	            
	            String sql1 = "DELETE FROM request where requestedresource = ?";
	            ps1 = c.prepareStatement(sql1);
	            
	            ps1.setString(1, sresource);

	            // Execute the update
	           ps1.executeUpdate();

	            if (rowsAffected > 0) {
	                return "Resource removed successfully.";
	            } else {
	                return "No such resource found for the user.";
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); // Log the exception
	            return "Error removing resource.";
	        } finally {
	            // Close the resources
	            try {
	                if (ps != null) ps.close();
	                if (c != null) c.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

		public static String edituser() throws Exception {
		    Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    StringWriter writer = new StringWriter();
		    PrintWriter out = new PrintWriter(writer);

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Prepare SQL query to get all user details
		        String sql = "SELECT firstname, lastname, email, username FROM userdetails";
		        ps = c.prepareStatement(sql);

		        // Execute the query
		        rs = ps.executeQuery();

		        // Build the HTML table with editable fields
		        out.println(
		        		"<!DOCTYPE html>" +
		        	            "<html lang='en'>" +
		        	            "<head>" +
		        	            "<meta charset='UTF-8'>" +
		        	            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
		        	            "<title>Edit User Details</title>" +
		        	            "<style>" +
		        	            "body { font-family: Arial, sans-serif; margin: 20px; }" +
		        	            "table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }" +
		        	            "th, td { border: 1px solid #ddd; padding: 8px; }" +
		        	            "th { background-color: #f4f4f4; }" +
		        	            "input[type='text'] { width: 100%; padding: 8px; box-sizing: border-box; }" +
		        	            "input[type='submit'] { padding: 10px 20px; border: none; border-radius: 5px; background-color: #4CAF50; color: white; cursor: pointer; }" +
		        	            "input[type='submit']:hover { background-color: #45a049; }" +
		        	            "table {" +
		        	            "    width: 100%;" +
		        	            "    border-collapse: collapse;" +
		        	            "    margin-bottom: 20px;" +
		        	            "    table-layout: fixed;" +  // Ensures columns have equal width
		        	            "}" +
		        	            "th, td {" +
		        	            "    border: 1px solid #ddd;" +
		        	            "    padding: 12px;" +
		        	            "    text-align: left;" +
		        	            "    width: 20%; " +  // Fixed width for columns
		        	            "    box-sizing: border-box;" +  // Ensures padding is included in width
		        	            "}" +
		        	            "th {" +
		        	            "    background-color: #4CAF50;" +
		        	            "    color: white;" +
		        	            "}" +
		        	            "td input[type='text'] {" +
		        	            "    width: 100%;" +
		        	            "    padding: 8px;" +
		        	            "    box-sizing: border-box;" +
		        	            "    border: 1px solid #ddd;" +
		        	            "    border-radius: 4px;" +
		        	            "}" +
		        	            "</style>" +
		        	            "</head>" +
		        	            "<body>" +
		        	            "<h2>Edit User Details</h2>"		        	);


		        // Append user details to the table
		        while (rs.next()) {
		            String firstname = rs.getString("firstname");
		            String lastname = rs.getString("lastname");
		            String email = rs.getString("email");
		            String username = rs.getString("username");

		            out.println(
		                "<form action='updateuser' method='post'>" +
		                "<table>" +
		                "<tr>" +
		                "<td><input type='text' name='firstname_' value='" + firstname + "' /></td>" +
		                "<td><input type='text' name='lastname_' value='" + lastname + "' /></td>" +
		                "<td><input type='text' name='email_' value='" + email + "' /></td>" +
		                "<td>" +
		                "<input type='hidden' name='username_' value='" + username + "' />" +
		                username +
		                "</td>" +
		                "<td><input type='submit' value='Edit' /></td>" +
		                "</tr>" +
		                "</table>" +
		                "</form>"
		            );
		        }

		        out.println(
		            "</body>" +
		            "</html>"
		        );

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return "Error fetching user details.";
		    } finally {
		        // Close resources
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (c != null) c.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }

		    // Return the HTML
		    return writer.toString();
		}


		public static String updateuser(String firstname, String lastname, String email,String username) throws Exception {
			 Connection c = null;
			    PreparedStatement ps = null;
			    String newUsername = firstname + "." + lastname;

			    try {
			        // Connect to the database
			        c = database.connect();
               
			        // Prepare SQL query to update user details
			        String sql = "UPDATE userdetails SET firstname = ?, lastname = ?, email = ?, username = ? WHERE username = ?";
			        ps = c.prepareStatement(sql);
			        ps.setString(1, firstname);
			        ps.setString(2, lastname);
			        ps.setString(3, email);
			        ps.setString(4, newUsername);
			        ps.setString(5, username);

			        // Execute the update
			        int rowsUpdated = ps.executeUpdate();

			        if (rowsUpdated > 0) {
			            return "success"; // Update successful
			        } else {
			            return "failure"; // Update failed
			        }
			        
			    } catch (SQLException e) {
			        e.printStackTrace();
			        return "error"; // Error during update
			    } finally {
			        // Close resources
			        try {
			            if (ps != null) ps.close();
			            if (c != null) c.close();
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			        }
			    }
		}

		public static String addusers() {
		    // Initialize a StringBuilder to build the HTML content
		    StringBuilder html = new StringBuilder();

		    // Build the HTML with inline CSS
		    html.append(
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
		        "input[type='text'], input[type='email'] {" +
		        "    font-size: 16px;" +
		        "    padding: 10px;" +
		        "    margin-bottom: 10px;" +
		        "    border: 1px solid #ddd;" +
		        "    border-radius: 5px;" +
		        "    width: calc(100% - 22px);" +
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
		        "<form action='adduserbyadmin1' method='post'>" +
		        "<h1>Add New User</h1>" +
		        "<label for='firstname'>First Name:</label><br>" +
		        "<input type='text' id='firstname' name='firstname' required /><br>" +
		        "<label for='lastname'>Last Name:</label><br>" +
		        "<input type='text' id='lastname' name='lastname' required /><br>" +
		        "<label for='email'>Email Address:</label><br>" +
		        "<input type='email' id='email' name='email' required /><br>" +
		        "<input type='submit' value='Add User' />" +
		        "</form>" +
		        "</body>" +
		        "</html>"
		    );

		    // Return the HTML
		    return html.toString();
		}

		
		

		
		




}





		/*public static void processCsvFile(InputStream uploadedInputStream) {
			// TODO Auto-generated method stub
			
		        Connection connection = null;
		        PreparedStatement preparedStatement = null;

		        try {
		            // Establish the database connection
		            connection = database.connect();

		            // Prepare the SQL insert statement
		            String sql = "INSERT INTO userdetails (username, email, managername) VALUES (?, ?, ?)";
		            preparedStatement = connection.prepareStatement(sql);

		            // Create a CSV parser to read the CSV file
		            CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withHeader());

		            // Iterate over the CSV records
		            for (CSVRecord csvRecord : csvParser) {
		                String username = csvRecord.get("username");
		                String email = csvRecord.get("email");
		                String managername = csvRecord.get("managername");

		                // Set parameters and execute the insert statement
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, email);
		                preparedStatement.setString(3, managername);
		                preparedStatement.addBatch(); // Add to batch for better performance
		            }

		            // Execute the batch insert
		            preparedStatement.executeBatch();

		        } catch (SQLException e) {
		            e.printStackTrace();
		            throw new SQLException("Error occurred while inserting CSV data into the database.", e);
		        } finally {
		            // Close resources
		            try {
		                if (preparedStatement != null) preparedStatement.close();
		                if (connection != null) connection.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		}*/
			
		
		
		




			
		
		
		
		
	



		
	
	
	


