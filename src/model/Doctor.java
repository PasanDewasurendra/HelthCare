package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class Doctor {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/doctorser?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
		
	}
	
	public String insertDoctor(String name, String address, String email, String phone, String spec)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 // create a prepared statement
		String query = " insert into doctors(`doctorID`,`doctorName`,`doctorAddress`,`doctorEmail`,`doctorPhone`,`doctorSpec`)"
	 + " values (?, ?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, name);
	 preparedStmt.setString(3, address);
	 preparedStmt.setString(4, email);
	 preparedStmt.setString(5, phone);
	 preparedStmt.setString(6, spec);
	// execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the doctor.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String readDoctors()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border=\"1\"><tr><th>Doctor Name</th><th>Doctor Address</th><th>Doctor Email</th><th>Doctor PhoneNo</th><th>Doctor Specification</th><th>Update</th><th>Remove</th></tr>";
	 String query = "select * from doctors";
	 Statement stmt = (Statement) con.createStatement();
	 ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String doctorID = Integer.toString(rs.getInt("doctorID"));
	 String doctorName = rs.getString("doctorName");
	 String doctorAddress = rs.getString("doctorAddress");
	 String doctorEmail = rs.getString("doctorEmail");
	 String doctorPhone = rs.getString("doctorPhone");
	 String doctorSpec = rs.getString("doctorSpec");
	 
	 // Add into the html table
	 output += "<tr><td>" + doctorName + "</td>";
	 output += "<td>" + doctorAddress + "</td>";
	 output += "<td>" + doctorEmail + "</td>";
	 output += "<td>" + doctorPhone + "</td>";
	 output += "<td>" + doctorSpec + "</td>";
	 
	 // buttons
	 output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"+ "<td><form method=\"post\" action=\"doctors.jsp\">"+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
	 + "<input name=\"doctorID\" type=\"hidden\" value=\"" + doctorID
	 + "\">" + "</form></td></tr>";
	 }
	 con.close();
	 
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the doctors.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String updateDoctor(String ID,String name, String address, String email, String phone, String spec)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE doctors SET doctorName=?,doctorAddress=?,doctorEmail=?,doctorPhone=?,doctorSpec=? WHERE doctorID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, name);    
			preparedStmt.setString(2, address);    
			preparedStmt.setString(3, email);   
			preparedStmt.setString(4, phone); 
			preparedStmt.setString(5, spec); 
			preparedStmt.setInt(6, Integer.parseInt(ID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			output = "Updated successfully";   
		}   
		catch (Exception e)   
		{    
			output = "Error while updating the doctor.";    
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	} 
	
	public String deleteDoctor(String doctorID)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for deleting."; } 
	 
			// create a prepared statement    
			String query = "delete from doctors where doctorID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(doctorID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			output = "Deleted successfully";   
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the doctor.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
	
	public String appointmentSchedule(String doctorID) {
	
		String out = "<table border='1'><tr><th>Number</th><th>Type</th><th>Date</th><th>Description</th><th>Patient</th></tr>";
		
		try {
			
			Connection con = connect(); 
			 
			if (con == null)    
			{return "Error while connecting to the database for deleting."; 
			}
			
			String query = "SELECT * FROM appointment WHERE doctor = '"+doctorID+"'";
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				String no = rs.getString("number");
				String type = rs.getString("type");
				String date = rs.getString("date");
				String description = rs.getString("description");
				String patient = rs.getString("patient");
				
				out += "<tr><td>"+no+"</td><td>"+type+"</td><td>"+date+"</td><td>"+description+"</td><td>"+patient+"</td></tr>";
				
			}
			
			out += "</table>";
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return out;
		
	}
	 

	
	


}