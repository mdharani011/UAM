package cs1.rest1;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import java.sql.Date;

public class User {

    // Fields for registration
     String firstname;
     String lastname;
     String email;
     String password;
     String confirmPassword;
     String newPassword;

    // Fields for login
    String username;
   String loginPassword;
   
    // Additional fields
    String usertype;
     Date dateOfJoining;

    // Default constructor
    public User() {}

    // Constructor for registration
    public User(String firstname, String lastname, String email, String password, String confirmPassword) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Constructor for login
    public User(String username, String loginPassword) {
        this.username = username;
        this.loginPassword = loginPassword;
    }

    

   public User(String username2, String newPassword, String confirmPassword2) {
		// TODO Auto-generated constructor stub
	   this.username = username;
       this.newPassword = newPassword;
       this.confirmPassword = confirmPassword;
	}

	/* public void login(@Context HttpServletResponse response, @Context HttpServletResponse request ) throws Exception {
    	Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Connect to the database
            c = database.connect();

            // Ensure password and username are set
            if (loginPassword == null || username == null) {
                throw new IllegalArgumentException("Username or password cannot be null");
            }
            

            // Encrypt the provided password to compare with the stored encrypted password
            String encryptedPassword = encrypt(loginPassword, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");

            // Query to get the username for the given encrypted password
            String sql = "SELECT username FROM userdetails WHERE passwd = ?";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, encryptedPassword);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String retrievedUsername = rs.getString("username");
                
                
                // Store user information in the session
               
                if (username.equals(retrievedUsername)) {
                	
                	 HttpSession session = ((HttpServletRequest) request).getSession();
                    // Store user information in the session
                    session.setAttribute("username", username);
                    
                	String userType=sqlOP.findUserType(username,c);
                	session.setAttribute("userType", userType);
                	
                	if(userType.equals("admin"))
                    response.sendRedirect("/rest1/admin.html?username=" + URLEncoder.encode(username, "UTF-8"));
                	else if (userType.equals("member"))
                		response.sendRedirect("/rest1/member.html?username=" + URLEncoder.encode(username, "UTF-8"));
                	else
                		response.sendRedirect("/rest1/manager.html?username=" + URLEncoder.encode(username, "UTF-8"));
                } else {
                   response.sendRedirect("/rest1/invaliduser.html");
                }
            } else {
            	 response.sendRedirect("/rest1/invaliduser.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/rest1/invaliduser.html");
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
    public String login() throws Exception {
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userType = null;
        
        try {
            // Connect to the database
            c = database.connect();

            // Ensure password and username are set
            if (loginPassword == null || username == null) {
                throw new IllegalArgumentException("Username or password cannot be null");
            }
            
            // Encrypt the provided password to compare with the stored encrypted password
            String encryptedPassword = encrypt(loginPassword, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");

            // Query to get the username for the given encrypted password
            String sql = "SELECT username FROM userdetails WHERE passwd = ?";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, encryptedPassword);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String retrievedUsername = rs.getString("username");
                
                // Validate the username
                if (username.equals(retrievedUsername)) {
                    
                    
                    // Find user type
                    userType = sqlOP.findUserType(username, c);
                } else {
                    userType = "invalid";
                }
            } else {
                userType = "invalid";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            userType = "error";
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return userType;
    }

    
    
   /* public void register(@Context HttpServletResponse response) throws Exception {
    	
    	if (!password.equals(confirmPassword)) {
    		 response.sendRedirect("/rest1/invaliduser.html");
        }
        
   	
        Connection c = null;
        PreparedStatement pstmt = null;
       
        
        try {
            c = database.connect();
            String userType = "member"; // Default value
            if (sqlOP.isFirstUser(c)) {
                userType = "admin";
            }
            
            String username=firstname.toLowerCase()+"."+lastname.toLowerCase();
            
           //String checkedusername=checkForUsername(username);
            
            // SQL Insert Statement
            String sql = "INSERT INTO userdetails (firstname, lastname, username, email, passwd,userType,dateOfJoining) VALUES (?, ?, ?, ?, ?,?,?)";
            
            String password1=encrypt(password,"C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");
            
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, password1);
            pstmt.setString(6, userType); 
            pstmt.setDate(7, Date.valueOf(LocalDate.now()));
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
            	response.sendRedirect("/rest1/registerLogin.html?username=" + URLEncoder.encode(username, "UTF-8"));

            } else {
            	 response.sendRedirect("/rest1/invaliduser.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/rest1/invaliduser.html");
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    
    }*/
    
    
    public String register() throws Exception {
    	
    	if (!password.equals(confirmPassword)) {
            return "passwordnotmatch"; // Passwords do not match
        }
    	
    	if(!checkpasswordconstraints(password)) {
    		return "checkpasscontraints";
    	}
    	//System.out.println("HI");	
        
        //System.out.println("H0");
        Connection c = null;
        PreparedStatement pstmt = null;
        
        try {
            c = database.connect();
            String userType = "member"; // Default value
            if (sqlOP.isFirstUser(c)) {
                userType = "admin";
            }

            String username = genUser(firstname,lastname);
            //System.out.println(username);
            this.username=username;
            // SQL Insert Statement
            String sql = "INSERT INTO userdetails (firstname, lastname, username, email, passwd, userType, dateOfJoining) VALUES (?, ?, ?, ?, ?, ?, ?)";

            String encryptedPassword = encrypt(password, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");

            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, encryptedPassword);
            pstmt.setString(6, userType);
            pstmt.setDate(7, Date.valueOf(LocalDate.now()));
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return "success"; // Registration successful
            } else {
            	System.out.println("Hm");
                return "invalid"; // Registration failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "invalid"; // Error during registration
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean checkpasswordconstraints(String password2) {
    	 // Check if the password is at least 8 characters long
       if (password2.length() < 8) {
            return false;
        }

       String specialChars = "!@#$%^&*(),.?\":{}|<>_";
       String digits = "1234567890";
       String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       
       // Flags to track the presence of required characters
       boolean hasDigit = false;
       boolean hasSpecialChar = false;
       boolean hasCapitalLetter = false;

       // Iterate through each character in the password
       for (int i = 0; i < password2.length(); i++) {
           char ch = password2.charAt(i);

           // Check if the character is a digit
           if (digits.indexOf(ch) != -1) {
               hasDigit = true;
           }
           // Check if the character is a special character
           else if (specialChars.indexOf(ch) != -1) {
               hasSpecialChar = true;
           }
           // Check if the character is a capital letter
           else if (capitalLetters.indexOf(ch) != -1) {
               hasCapitalLetter = true;
           }

           // If all criteria are met, return true
           if (hasDigit && hasSpecialChar && hasCapitalLetter) {
               return true;
           }
       }

       // If any of the criteria are not met, return false
       return false;

        // Password meets all constraints
        
}

	public static String encrypt(String password1, String fileName) throws FileNotFoundException {
       
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	ArrayList<String> list=new ArrayList<>();
        String line;
        try {
			while ((line = br.readLine()) != null)     
			{
					list.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index;
		String result="";
		for(int i=0;i<password1.length();i++) {
			char c=password1.charAt(i);
			int j=1;
			for(String s:list)
			{
				if(s.indexOf(c)>=0)
				{
					index=s.indexOf(c);
					result=result+j+(index+1);
					j++;
				}
				else
				{    
					j++;
				}
			}
		}
		return result;
	}

	public String forgotPassword(String us, String newP, String confirmP,String email1) throws Exception {
	    // Check if the new password and confirm password match
	    if (!newP.equals(confirmP)) {
	    	return "passwordnotmatch"; // Passwords do not match
	    }
	    
	    // Check if the new password meets the required constraints
	    if (!checkpasswordconstraints(newP)) {
	    	return "checkpasscontraints"; // Password does not meet constraints
	    }

	    PreparedStatement pstmt = null;
	    Connection c = null;
	    ResultSet rs = null;

	    try {
	        // Connect to the database
	        c = database.connect();

	        // SQL query to check if the username exists
	        String checkUserSql = "SELECT COUNT(*) FROM userdetails WHERE username = ? and email = ?";
	        pstmt = c.prepareStatement(checkUserSql);
	        pstmt.setString(1, us);
	        pstmt.setString(2, email1);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int userCount = rs.getInt(1);
	            if (userCount > 0) {
	                // Username exists, proceed with updating the password
	                // Define the SQL update statement
	                String updateSql = "UPDATE userdetails SET passwd = ? WHERE username = ?";
	                
	                // Encrypt the new password
	                String encryptedPassword = encrypt(newP, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");
	                
	                // Prepare and execute the update statement
	                pstmt = c.prepareStatement(updateSql);
	                pstmt.setString(1, encryptedPassword);
	                pstmt.setString(2, us);
	                int rowsAffected = pstmt.executeUpdate();

	                if (rowsAffected > 0) {
	                    return "success"; // Update successful
	                } else {
	                    return "invalid"; // Unexpected issue with updating
	                }
	            } else {
	                return "usernotfound"; // No user found with the given username
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "invalid"; // Error during database operations
	    } finally {
	        // Clean up resources
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            // Connection closing is handled by the caller
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return "error"; // Fallback return value in case of unexpected issues
	}
	
	
	public String updatePassword(String us,String newP,String confirmP) throws Exception {
		 if (!newP.equals(confirmP)) {
			 return "passwordnotmatch"; // Passwords do not match
	        }
		 
		 if(!checkpasswordconstraints(confirmP)) {
			 return "checkpasscontraints";
	    	}
	        PreparedStatement pstmt = null;
	        Connection c = null;
	        try {
	            // SQL Update Statement
	        	c = database.connect();
	        	
	        	
	        	
	        	
	        	
	        	
	            String sql = "UPDATE userdetails SET passwd = ? WHERE username = ?";

	            // Encrypt the new password
	            String encryptedPassword = encrypt(newP, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");

	            pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, encryptedPassword);
	            pstmt.setString(2, us);

	            // Execute the update operation
	            int rowsAffected = pstmt.executeUpdate();

	            if (rowsAffected > 0) {
	                return "success"; // Update successful
	            } else {
	                return "invalid"; // No user found with the given username
	            }
	            
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "invalid"; // Error during update
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

	 
	 
	 public static String genUser(String firstname, String lastname) throws Exception {
		 String basename=firstname.toLowerCase() + "." + lastname.toLowerCase();
	        String username = basename;
	 
	        try (Connection conn = database.connect();) {
	            String query = "SELECT COUNT(*) FROM userdetails WHERE username LIKE ?";
	            try (PreparedStatement checkStmt = conn.prepareStatement(query)) {
	                checkStmt.setString(1, username + "%");
	 
	                try (ResultSet rs = checkStmt.executeQuery()) {
	                    if (rs.next()) {
	                        int count = rs.getInt(1);
	                        if (count > 0) {
	                            username = basename + (count);
	                        }
	                    }
	                }
	            }
	        }
	        return username;
	    }
	 
	 
	 public static void saveUserToDatabase(String username, String email, String usertype) throws Exception {
         // Database connection
         try (Connection conn = database.connect()) {
             // SQL query to insert or update user
             String sql = "INSERT INTO userdetails (username, email, usertype) VALUES (?, ?, ?) " +
                          "ON DUPLICATE KEY UPDATE email = VALUES(email), usertype = VALUES(usertype)";
             try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                 pstmt.setString(1, username);
                 pstmt.setString(2, email);
                 pstmt.setString(3, usertype);
                 pstmt.executeUpdate();
             }
         }
     }

	 public static String addusers1(String firstname, String lastname, String email) throws Exception {
		    Connection c = null;
		    PreparedStatement pstmt = null;

		    try {
		        // Connect to the database
		        c = database.connect();

		        // Determine user type
		        String userType = "member"; // Default value for all users

		        // Generate username based on the provided names
		        String username = genUser(firstname, lastname);

		        // Generate default password
		        String defaultPassword = firstname + lastname + "@1";
		        // Encrypt the password
		        String encryptedPassword = encrypt(defaultPassword, "C:\\Users\\user\\Documents\\UAM_Project\\rest1\\src\\main\\resources\\passwordEncrypt.txt");

		        // SQL Insert Statement
		        String sql = "INSERT INTO userdetails (firstname, lastname, username, email, passwd, userType, dateOfJoining) VALUES (?, ?, ?, ?, ?, ?, ?)";

		        pstmt = c.prepareStatement(sql);
		        pstmt.setString(1, firstname);
		        pstmt.setString(2, lastname);
		        pstmt.setString(3, username);
		        pstmt.setString(4, email);
		        pstmt.setString(5, encryptedPassword);
		        pstmt.setString(6, userType);
		        pstmt.setDate(7, Date.valueOf(LocalDate.now())); // Automatic date of joining
		         // managername set to NULL

		        // Execute the insert
		        int rowsAffected = pstmt.executeUpdate();

		        if (rowsAffected > 0) {
		            return "success"; // Registration successful
		        } else {
		            return "invalid"; // Registration failed
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return "invalid"; // Error during registration
		    } finally {
		        // Close resources
		        try {
		            if (pstmt != null) pstmt.close();
		            if (c != null) c.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		}

}
