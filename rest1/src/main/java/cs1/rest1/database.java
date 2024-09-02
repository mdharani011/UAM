package cs1.rest1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class database {
	public static Connection connect() throws Exception {
		String driver="com.mysql.cj.jdbc.Driver",url="jdbc:mysql://localhost:3306/UAM",userName="root",password="cybersolve";
		Class.forName(driver);
		Connection c=DriverManager.getConnection(url,userName,password);
		return c;
	}
}