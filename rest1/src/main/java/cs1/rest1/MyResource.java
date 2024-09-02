package cs1.rest1;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.mysql.cj.xdevapi.Statement;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("UAM")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws Exception 
     */
    
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void LoginForm(@FormParam("username") String username, @FormParam("password") String password,@Context HttpServletResponse response,@Context HttpServletRequest request) throws Exception {
	    
		

	    
		User user=new User(username,password);
		 String userType=user.login();
		 System.out.println(userType);
		 if ( "admin".equals(userType) || "member".equals(userType) || "manager".equals(userType))
		 {
			 HttpSession session = request.getSession();
			    session.setAttribute("username", username);
		 }
		 
		 if ("admin".equals(userType)) {
		        response.sendRedirect("/rest1/admin.html?username=" + URLEncoder.encode(username, "UTF-8"));
		    } else if ("member".equals(userType)) {
		        response.sendRedirect("/rest1/member.html?username=" + URLEncoder.encode(username, "UTF-8"));
		    } else if ("manager".equals(userType)) {
		        response.sendRedirect("/rest1/manager.html?username=" + URLEncoder.encode(username, "UTF-8"));
		    } else {
		        response.sendRedirect("/rest1/invaliduser.html");
		    }
		
	}

	
	/*@POST
	@Path("redirect")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void redirect(@Context HttpServletResponse response,@Context HttpServletRequest request) throws Exception {
	    
		
		 HttpSession session = request.getSession();
		     String usrname= session.getAttribute("username").toString();
		
		        response.sendRedirect("/rest1/admin.html?username=" + URLEncoder.encode(usrname, "UTF-8"));
		    
		
	}*/

    
    
   
        @POST
        @Path("register")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public void RegistrationForm(@FormParam("firstname") String firstname,
                                @FormParam("lastname") String lastname,
                                @FormParam("email") String email,
                                @FormParam("password") String password,
                                @FormParam("confirmPassword") String confirmPassword,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
        	 
        	
    		User user=new User(firstname,lastname,email,password,confirmPassword);
    		String status= user.register();
    		 if ("success".equals(status)) {
    			 HttpSession session = request.getSession();
 			      session.setAttribute("username", user.username);
    		        response.sendRedirect("/rest1/registerLogin.html?username=" + URLEncoder.encode(user.username, "UTF-8"));
    		        
    		    } else if ("invalid".equals(status)) {
    		        response.sendRedirect("/rest1/invaliduser.html");
    		    } 
    		    else if ("checkpasscontraints".equals(status)) {
    		        response.sendRedirect("/rest1/checkpasscontraints.html");
    		    } 
    		    else if ("passwordnotmatch".equals(status)) {
    		        response.sendRedirect("/rest1/passwordnotmatch.html");
    		    } 
    		
     }
        
        
        @POST
    	@Path("forgotpassword")
    	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    	public void LoginForm(@FormParam("username") String username, @FormParam("newPassword") String newPassword,@FormParam("confirmPassword") String confirmPassword,@FormParam("email") String email1,@Context HttpServletResponse response,@Context HttpServletRequest request) throws Exception {
    	    
    		

    	    
    		User user=new User(username,newPassword,confirmPassword);
    		 String result=user.forgotPassword(username,newPassword,confirmPassword,email1);
    		 
    		 if ( "success".equals(result) )
    		 {
    			 HttpSession session = request.getSession();
    			    session.setAttribute("username", username);
    		 }
    		 
    		 if ("success".equals(result)) {
    		        response.sendRedirect("/rest1/login.html");
    		    
    		    } else if ("invalid".equals(result)) {
    		        response.sendRedirect("/rest1/invaliduser.html");
    		    }
    		    else if ("checkpasscontraints".equals(result)) {
    		        response.sendRedirect("/rest1/checkpasscontraints.html");
    		    } 
    		    else if ("passwordnotmatch".equals(result)) {
    		        response.sendRedirect("/rest1/passwordnotmatch.html");
    		    } 
    		    else
    		    {
    		    	response.sendRedirect("/rest1/invaliduser1.html");
    		    }
    		
    	}
        
        
        @POST
    	@Path("updatepassword")
    	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    	public String updatepassword(@FormParam("newPassword") String newPassword,@FormParam("confirmPassword") String confirmPassword,@Context HttpServletResponse response,@Context HttpServletRequest request) throws Exception {
    	    
    		

    	    
    		User user=new User(newPassword,confirmPassword);
    		 HttpSession session = request.getSession();
			 String username= session.getAttribute("username").toString();
    		 String result=user.updatePassword(username,newPassword,confirmPassword);
    		 
    		 
    		 
    		 if ("success".equals(result)) {
    		        return "password changed succesfully";
    		    
    		    } else if ("checkpasscontraints".equals(result)) {
    		        return "check password contraints";
    		    } 
    		    else if ("passwordnotmatch".equals(result)) {
    		        return "password do not match";
    		    } 
    		    else
    		    {
    		    	return "error";
    		    }
    		
    	}
        
        @POST
        @Path("requestRole")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String chnageuser(@FormParam("approvalRequest") String approvalRequest,
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
        	 
        	
        	
        	 HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
        	
        	 String result= memberOP.requestadded(s,approvalRequest);
        	 if(result.equals("Y")) 
        	 return s+" has requested for "+approvalRequest;
        	 else
        		 return "request already made";
        	
        }
        
        /*@POST
        @Path("checkRequests")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public void checkrequest(@FormParam("approvalRequest")
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
        	 
        	
        	
        	 
			 
			 
        	  try {
        	  ArrayList<Request> requests = adminOP.checkrequests(); // Pass the appropriate user name or ID if needed

              // Set the requests as an attribute to the request object
              request.setAttribute("requests", requests);

              // Forward the request to the JSP page
              request.getRequestDispatcher("rest1/request.jsp").forward(request, response);
          } catch (Exception e) {
              e.printStackTrace();
              response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
          }
        	
        }*/
        
        
        @POST
        @Path("checkmyresources")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkmyresource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
        	 
        	
        	
        	 HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
        	
        	 String list=memberOP.checkResources(s);
        	 
        	 return list;
        	
        }
        
        @POST
        @Path("checknotavailableresources")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checknotavailableresource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
        	 
        	
        	
        	 HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
        	
        	 String list=memberOP.getnotavaibleResources(s);
        	 
        	 
        	 
        	 return list;
        	
        }

        
        
       

        @POST
        @Path("resourcerequestsent")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checknotavailablerewsource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response, @FormParam("resources") String res) throws Exception {
        	 
        	
        	
        	 HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
			// String res=request.getParameter("resources");
        	
			 String result= memberOP.requestadded(s,res);
        	 if(result.equals("Y")) 
        	 return s+" has requested for "+res;
        	 else
        		 return "request already made";
        	
        }
        
        
        
        @POST
        @Path("checkapprovals")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkapprovals(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    String list=memberOP.getapprovals(s);
       	 
       	 
       	 
       	    return list;
     }
        
        

        @POST
        @Path("getManager")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String getManager(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    String list=memberOP.getManager(s);
       	 
       	 
       	 
       	    return list;
     }
        
        @POST
        @Path("checkavailableresources")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkavailableresources(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    String list=memberOP.checkavaibaleresources(s);
       	 
       	    	
       	 
       	    return list;
     }

        
        
        @POST
        @Path("resourceremoverequestsent")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresources(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resources") String res) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    memberOP.removeResources(s,res);
       	 
       	    	
       	    String string=s+" has removed "+res;
       	    return string;
     }
        
        
        // ADMIN OPERATIONS:

        @POST
        @Path("checkrequests")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkrequests(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resources") String res) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    String list=adminOP.checkrequests();
       	 
       	    	
       	    
       	    return list;
     }

        
        

        @POST
        @Path("aprroverequest")
        //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String approverequest(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("requestfrom") String requestfrom,@FormParam("requestedresource") String requestedresource) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    adminOP.approveRequests(requestfrom,requestedresource,request);
       	 
       	 return "Approved request for " + requestedresource + " from " + requestfrom ;

       	    
       	  
     }
        
        
        @POST
        @Path("rejectrequest")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String rejectrequest(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("requestfrom") String requestfrom,@FormParam("requestedresource") String requestedresource) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String s= session.getAttribute("username").toString();
       	
       	    String res=adminOP.rejectRequests(requestfrom,requestedresource);
       	 
       	    	
       	    
       	 return "Rejected request for " + requestedresource + " from " + requestfrom;
     }
        
        
        
        @POST
        @Path("addresource")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String addresource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resourceName") String resourceName ) throws Exception {
       
        	
        	
        	
        	
       	
       	    String res=adminOP.addResource(resourceName);
       	 
       	    	
       	    
       	    return res;
     }
        
        
        @POST
        @Path("checkresourcedetails")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkresourcedetails(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	   String list= adminOP.checkresourcedetails();
       	 
       	    	return list;
       	    
       	    
     }
        
        @POST
        @Path("displayresources")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	   String list= adminOP.displayResources();
       	   return list;
       	    	
       	    
       	    
     }
        

        @POST
        @Path("removeresource")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresource1(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resources") String removeResource ) throws Exception {
       
        	
        	
        	
        	
       	
       	    adminOP.removeResource(removeResource);
       	 
       	    	return "removed resource : "+removeResource;
       	    
       	    
     }
        
        
        @POST
        @Path("checkusersforaresource")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkusersforresource(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response ) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.displaytheresources();
       	    return list;
       	 
       	    	
       	    
       	    
     }
        

        @POST
        @Path("displaytheusers")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkusersforresource1(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resources") String resource) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.checkusersforaresource(resource);
       	    return list;
       	 
       	    	
       	    
       	    
     }
        
        
        
        @POST
        @Path("checkresourcesofauser")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkresourcesofuser(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("resources") String resource) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.displaytheusers();
       	    return list;
       	 
       	    	
       	    
       	    
     }
        
        
        @POST
        @Path("displaytheresources")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String checkresourcesofuser1(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("users") String user) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.checkresourcesofuser(user);
       	    return list;
       	 
       	    	
       	    
       	    
     }
        
        
        @POST
        @Path("removeuser")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeuser(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.displaylistofusers();
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        

        @POST
        @Path("removeuser1")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeuser1(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response,@FormParam("users") String user) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.removeUser(user);
       	    adminOP.removeUser1(user);
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        @POST
        @Path("removeresourcefromuser1")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresourcefromuser(
                              
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.removeresourcefromuser1();
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        @POST
        @Path("removeresourcefromuser2")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresourcefromuserr(@FormParam("selecteduser") String suser,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.removeresourcefromuser2(suser);
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        
        
        @POST
        @Path("removeresourcefromuser3")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeresourcefromuserrr(@FormParam("sresource") String sresource,@FormParam("suser") String suser,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.removeresourcefromuser3(sresource,suser);
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        /*@POST
        @Path("removemanager")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removemanager( 
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.displaymanager();
       	    return list;
       	 
       	    	
       	    
       	    
     } */
        
        

        @POST
        @Path("edituser")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String edituser( 
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=adminOP.edituser();
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        @POST
        @Path("updateuser")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String updateuser(@FormParam("firstname_") String firstname,@FormParam("lastname_") String lastname,@FormParam("email_") String email,
        		@FormParam("username_") String username,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	System.out.println(firstname+lastname+email+username);
       	
       	    String list=adminOP.updateuser(firstname,lastname,email,username);
       	    return list;
       	 
       	    	
       	    
       	    
     } 
        
        
       /* @POST
        @Path("upload")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        public String handleFileUpload(@FormParam("file") InputStream uploadedInputStream
                                        ) { 
            try {
                adminOP.processCsvFile(uploadedInputStream);
                return "File uploaded and processed successfully.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to upload and process the file.";
            }
        }*/
        
        // manager section
        
        
        
        

        @POST
        @Path("showteammembers")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String showteammembers(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String usrn= session.getAttribute("username").toString();
       	
       	    String list=managerOP.showTeamMembers(usrn);
       	    return list;
       	} 
        
        
        @POST
        @Path("addteammembers")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String addteammembers(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	
       	
       	    String list=managerOP.getUserDropdownWithNoManager();
       	    return list;
       	} 
        
        
        @POST
        @Path("addtheusertoteam")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String addtheusertoteam(@FormParam("users") String user,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	HttpSession session = request.getSession();
			 String managerusername= session.getAttribute("username").toString();
        	
       	
       	    String list=managerOP.addTheUserToTeam(user,managerusername);
       	    return list;
       	} 
        
        

        @POST
        @Path("removeteammembers")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removeteammembers(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String managerusername= session.getAttribute("username").toString();
       	
       	    String list=managerOP.getUserDropdownOfTeamMembers(managerusername);
       	    return list;
       	}
        
        
        @POST
        @Path("removetheuserfromteam")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String removetheuserfromteam(@FormParam("users") String user,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String managerusername= session.getAttribute("username").toString();
       	
       	    String list=managerOP.removeTheUserFromTeam( user,managerusername);
       	    return list;
       	}
        
        
        @POST
        @Path("editteammemberdetails")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String editteammemberdetails(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String managerusername= session.getAttribute("username").toString();
       	
       	    String list=managerOP.editteammemberdetails(managerusername);
       	    return list;
       	}
        
        
        @POST
        @Path("requestadmin")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String requestadmin(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	
        	
        	HttpSession session = request.getSession();
			 String managerusername= session.getAttribute("username").toString();
       	  String admin="ManagerToAdmin";
       	    String list=memberOP.requestadded(managerusername,admin);
       	   
       	 String list1=managerOP.getUserDropdownOfTeamMembers1(managerusername);
       	 return list1;
       	    
       	}
        
        
        @POST
        @Path("assignmanager")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public void assignmanager(@FormParam("teammember") String teamMember,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	 //System.out.println("Received teamMember: " + teamMember);
        	
        	HttpSession session = request.getSession();
		    session.setAttribute("teammember",teamMember);
		    //System.out.println(session.getAttribute("teammember").toString());
       	        	    
       	}
        
        
        
        @POST
        @Path("addusersbyadmin")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String addusersbyadmin(
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	 
        	//HttpSession session = request.getSession();
        	String list1=adminOP.addusers();
          	 return list1;
		    
       	        	    
       	}
        
        
        @POST
        @Path("adduserbyadmin1")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public String addusersbyadmin1(
        						
        		             @FormParam("firstname") String firstname,
        		             @FormParam("lastname") String lastname,
        		             @FormParam("email") String email,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response) throws Exception {
       
        	
        	 
        	//HttpSession session = request.getSession();
        	String list1=User.addusers1(firstname,lastname,email);
          	 return list1;
		    
       	        	    
       	}
        
        



       


}



        


    


