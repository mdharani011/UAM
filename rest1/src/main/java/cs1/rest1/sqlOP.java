package cs1.rest1;
import java.util.*;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;

public class sqlOP {
	
	public static boolean isFirstUser(Connection c) throws SQLException {
        // Check if there are any users in the database
        String countSql = "SELECT COUNT(*) FROM userdetails";
        try (PreparedStatement pstmt = c.prepareStatement(countSql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Return true if no users exist
            }
            return false;
        }
    }
	
	public static String findUserType(String usrname,Connection c) throws SQLException
	{
		String sql = "SELECT userType FROM userdetails where username= ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
        	pstmt.setString(1,usrname);
            ResultSet rs = pstmt.executeQuery();
            
            rs.next();
                return rs.getString(1);  // Return true if no users exist
            }
            
        }
	
	
}



	
	
	
