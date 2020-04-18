package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public Connection connect() {
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/helthcare");
			
			System.out.println("Database Connected.");
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB Connecting Faild.");
			e.printStackTrace();
		}
		
		return con;
	}
	
}
