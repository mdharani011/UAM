package cs1.rest1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {
	
	public String getUsers() throws Exception {
    	Connection c=database.connect();
    	PreparedStatement ps=c.prepareStatement("select username from userdetails");
    	ResultSet rs=ps.executeQuery();
    	String dropDown="<select name='users'>";
    	while(rs.next()) {
    		String value=rs.getString(1);
    		dropDown+="<option value='"+value+"'>"+value+"</option>";
    	}
    	dropDown+="</select>";
           return dropDown;
}
	
	
	

}
