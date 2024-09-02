package cs1.rest1;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class managerOP {

	

	    public static String showTeamMembers(String managerUsername) throws Exception {
	        Connection c = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        StringBuilder resultBuilder = new StringBuilder();

	        try {
	            // Connect to the database
	            c = database.connect();

	            // Prepare SQL query
	            String sql = "SELECT username,firstname,lastname,email,dateOfjoining FROM userdetails WHERE managername = ?";
	            pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, managerUsername);

	            // Execute the query
	            rs = pstmt.executeQuery();

	            // Build the HTML table
	            resultBuilder.append("<!DOCTYPE html>");
	            resultBuilder.append("<html>");
	            resultBuilder.append("<head>");
	            resultBuilder.append("<style>");
	            resultBuilder.append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }");
	            resultBuilder.append("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
	            resultBuilder.append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
	            resultBuilder.append("th { background-color: #4CAF50; color: white; }");
	            resultBuilder.append("tr:nth-child(even) { background-color: #f2f2f2; }");
	            resultBuilder.append("tr:hover { background-color: #e0e0e0; }");
	            resultBuilder.append("</style>");
	            resultBuilder.append("</head>");
	            resultBuilder.append("<body>");
	            resultBuilder.append("<table>");
	            resultBuilder.append("<tr>");
	            resultBuilder.append("<th>Username</th>");
	            resultBuilder.append("<th>Firstname</th>");
	            resultBuilder.append("<th>Lastname</th>");
	            resultBuilder.append("<th>Email</th>");
	            resultBuilder.append("<th>DateOFJoinig</th>");
	            resultBuilder.append("</tr>");

	            // Append rows to the table
	            while (rs.next()) {
	                String username = rs.getString("username");
	                String firstname = rs.getString("firstname");
	                String lastname = rs.getString("lastname");
	                String email = rs.getString("email");
	                String doj = rs.getString("dateOfjoining");

	                resultBuilder.append("<tr>");
	                resultBuilder.append("<td>").append(username).append("</td>");
	                resultBuilder.append("<td>").append(firstname).append("</td>");
	                resultBuilder.append("<td>").append(lastname).append("</td>");
	                resultBuilder.append("<td>").append(email).append("</td>");
	                resultBuilder.append("<td>").append(doj).append("</td>");
	                resultBuilder.append("</tr>");
	            }

	            // Close the table and HTML tags
	            resultBuilder.append("</table>");
	            resultBuilder.append("</body>");
	            resultBuilder.append("</html>");

	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new Exception("Database error occurred while retrieving team members", e);
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

	        // Return the HTML table as a string
	        return resultBuilder.toString();
	    }

	    
	        public static String getUserDropdownWithNoManager() throws Exception {
	            Connection c = database.connect();
	            PreparedStatement ps = null;
	            ResultSet rs = null;
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
	                "<form action='addtheusertoteam' method='post'>" +
	                "<select name='users'>"
	            );

	            try {
	                // Prepare SQL query to get usernames where managername is NULL
	                String sql = "SELECT username FROM userdetails WHERE managername IS NULL  AND userType = 'member'";
	                ps = c.prepareStatement(sql);

	                // Execute the query
	                rs = ps.executeQuery();

	                // Append options to the dropdown
	                while (rs.next()) {
	                    String username = rs.getString("username");
	                    dropDown.append("<option value='").append(username).append("'>").append(username).append("</option>");
	                }

	            } catch (SQLException e) {
	                e.printStackTrace();
	                throw new SQLException("Error occurred while fetching users with no manager.", e);
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

	            // Close the HTML tags
	            dropDown.append(
	                "</select>" +
	                "<input type='submit' value='Submit'>" +
	                "</form>" +
	                "</body>" +
	                "</html>"
	            );

	            // Return the HTML dropdown
	            return dropDown.toString();
	        }




	        public static String addTheUserToTeam(String user, String managerUsername) throws Exception {
	            Connection c = null;
	            PreparedStatement ps = null;

	            try {
	                // Connect to the database
	                c = database.connect();

	                // Prepare SQL query to update managername for the user
	                String sql = "UPDATE userdetails SET managername = ? WHERE username = ?";
	                ps = c.prepareStatement(sql);
	                ps.setString(1, managerUsername); // Set the manager username
	                ps.setString(2, user);            // Set the user to be updated

	                // Execute the update
	                int rowsAffected = ps.executeUpdate();

	                // Check if update was successful
	                if (rowsAffected > 0) {
	                    return "User successfully added to the team.";
	                } else {
	                    return "No user found with the specified username.";
	                }

	            } catch (SQLException e) {
	                e.printStackTrace();
	                return "Database error occurred while adding user to the team.";
	            } finally {
	                // Close resources
	                try {
	                    if (ps != null) ps.close();
	                    if (c != null) c.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        public static String getUserDropdownOfTeamMembers( String managerUsername) throws Exception {
	            PreparedStatement ps = null;
	            ResultSet rs = null;
	            Connection c=null;
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
	                "<form action='removetheuserfromteam' method='post'>" +
	                "<select name='users'>"
	            );

	            try {
	            	c=database.connect();
	                // Prepare SQL query to get usernames where managerid matches the given managerUsername
	                String sql = "SELECT username FROM userdetails WHERE managername = ?";
	                ps = c.prepareStatement(sql);
	                ps.setString(1, managerUsername); // Set the managerUsername parameter

	                // Execute the query
	                rs = ps.executeQuery();

	                // Append options to the dropdown
	                while (rs.next()) {
	                    String username = rs.getString("username");
	                    dropDown.append("<option value='").append(username).append("'>").append(username).append("</option>");
	                }

	            } catch (SQLException e) {
	                e.printStackTrace();
	                throw new RuntimeException("Error occurred while fetching team members.", e);
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

	            // Close the HTML tags
	            dropDown.append(
	                "</select>" +
	                "<input type='submit' value='Submit'>" +
	                "</form>" +
	                "</body>" +
	                "</html>"
	            );

	            // Return the HTML dropdown
	            return dropDown.toString();
	        }

	        public static String removeTheUserFromTeam(String user, String managerUsername) throws Exception {
	            PreparedStatement ps = null;
	            Connection c=null;
	            String resultMessage = "User removed from team successfully.";

	            try {
	            	c=database.connect();
	                // Prepare SQL query to set managername to NULL for the given user
	                String sql = "UPDATE userdetails SET managername = NULL WHERE username = ? AND managername = ?";
	                ps = c.prepareStatement(sql);
	                ps.setString(1, user); // Set the username parameter
	                ps.setString(2, managerUsername); // Set the managerUsername parameter

	                // Execute the update statement
	                int rowsAffected = ps.executeUpdate();
	                
	                if (rowsAffected == 0) {
	                    resultMessage = "No user found with the given details or user is not managed by the specified manager.";
	                }

	            } catch (SQLException e) {
	                e.printStackTrace();
	                resultMessage = "Error occurred while removing user from the team.";
	            } finally {
	                // Close resources
	                try {
	                    if (ps != null) ps.close();
	                    if (c != null) c.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }

	            // Return result message
	            return resultMessage;
	        }


			public static String editteammemberdetails(String managerusername) throws Exception {
				// TODO Auto-generated method stub
				 Connection c = null;
				    PreparedStatement ps = null;
				    ResultSet rs = null;
				    StringWriter writer = new StringWriter();
				    PrintWriter out = new PrintWriter(writer);

				    try {
				        // Connect to the database
				        c = database.connect();

				        // Prepare SQL query to get all user details
				        String sql = "SELECT firstname, lastname, email, username FROM userdetails where managername=?";
				        ps = c.prepareStatement(sql);
				        ps.setString(1, managerusername);
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


			public static String getUserDropdownOfTeamMembers1(String managerusername) throws Exception {
				 PreparedStatement ps = null;
		            ResultSet rs = null;
		            Connection c=null;
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
		                "<form action='assignmanager' method='post'>" +
		                "<select name='teammember'>"
		            );

		            try {
		            	c=database.connect();
		                // Prepare SQL query to get usernames where managerid matches the given managerUsername
		                String sql = "SELECT username FROM userdetails WHERE managername = ?";
		                ps = c.prepareStatement(sql);
		                ps.setString(1, managerusername); // Set the managerUsername parameter

		                // Execute the query
		                rs = ps.executeQuery();

		                // Append options to the dropdown
		                while (rs.next()) {
		                    String username = rs.getString("username");
		                    dropDown.append("<option value='").append(username).append("'>").append(username).append("</option>");
		                }

		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException("Error occurred while fetching team members.", e);
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

		            // Close the HTML tags
		            dropDown.append(
		                "</select>" +
		                "<input type='submit' value='Submit'>" +
		                "</form>" +
		                "</body>" +
		                "</html>"
		            );

		            // Return the HTML dropdown
		            return dropDown.toString();
			}
			

	


}
