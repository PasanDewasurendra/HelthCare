package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, Username, Password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPatient(String pName, String pAddress, String pDOB, String pAge, String pGender, String pEmail,
			String pTelephone) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error Connecting to the Database for Inserting.";
			}
			// create a prepared statement
			String query = " insert into healthcare(`pID`,`pName`,`pAddress`,`pDOB`,`pAge`,`pGender`,`pEmail`,`pTelephone`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pName);
			preparedStmt.setString(3, pAddress);
			preparedStmt.setString(4, pDOB);
			preparedStmt.setString(5, pAge);
			preparedStmt.setString(6, pGender);
			preparedStmt.setString(7, pEmail);
			preparedStmt.setString(8, pTelephone);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted Successfully !";
		} catch (Exception e) {
			output = "Error While Registering the Patient!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPatients() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for Reading.";
			}

			// Prepare the HTML table to be displayed

			output = "<table border=\"1\"><tr><th>Patient Name</th><th>Address</th><th>Date of Birth</th><th>Age</th><th>Gender</th><th>Email</th><th>Telephone</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from patients";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);

			// iterate through the rows in the result set

			while (rs.next()) {
				
				String pID = Integer.toString(rs.getInt("pID"));
				String pName = rs.getString("pName");
				String pAddress = rs.getString("pAddress");
				String pDOB = rs.getString("pDOB");
				String pAge = rs.getString("pAge");
				String pGender = rs.getString("pGender");
				String pEmail = rs.getString("pEmail");
				String pTelephone = rs.getString("pTelephone");

				// Add into the HTML table

				output += "<tr><td>" + pName + "</td>";
				output += "<td>" + pAddress + "</td>";
				output += "<td>" + pDOB + "</td>";
				output += "<td>" + pAge + "</td>";
				output += "<td>" + pGender + "</td>";
				output += "<td>" + pEmail + "</td>";
				output += "<td>" + pTelephone + "</td>";

				// buttons

				output +=  "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						 + "<td><form method=\"post\" action=\"Patients.jsp\">"
						 + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						 + "<input name=\"pID\" type=\"hidden\" value=\"" + pID
						 + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the HTML table
			output += "</table>";
		} catch (Exception e) {
			output = "Error  Reading!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePatient(String pID, String pName, String pAddress, String pDOB, String pAge, String pGender,
			String pEmail, String pTelephone) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement

			String query = "UPDATE patients SET pName=?,pAddress=?,pDOB=?,pAge=?,pGender=? ,pEmail=? ,pTelephone=?" + "WHERE pID=?";
			PreparedStatement preparedSt = con.prepareStatement(query);

			// binding values

	
			preparedSt.setString(1, pName);
			preparedSt.setString(2, pAddress);
			preparedSt.setString(3, pDOB);
			preparedSt.setString(4, pAge);
			preparedSt.setString(5, pGender);
			preparedSt.setString(6, pEmail);
			preparedSt.setString(7, pTelephone);
			preparedSt.setInt(8, Integer.parseInt(pID));
			
			// execute the statement

			preparedSt.execute();
			con.close();
			output = "Successfully Updated! ";
		} catch (Exception e) {
			output = "Error while updating the Patient Details!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String pID) {

		 String output = ""; 
		 
		  try   
		  {    
			  Connection con = connect(); 
		 
		   if (con == null)    
		   {
			   return "Error while connecting to the database for deleting."; } 
		 
		   // create a prepared statement    
		   
		   String query = "delete from patients where pID=?"; 
		 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		   
		   preparedStmt.setInt(1, Integer.parseInt(pID)); 
		 
		   // execute the statement    
		   
		   preparedStmt.execute();    
		   
		   con.close(); 
		 
		   output = "Deleted successfully";   
		   }   
		  	catch (Exception e)   
		  {    output = "Error while deleting the patient.";    
		  System.err.println(e.getMessage());   } 
		 
		  return output;  }
}
