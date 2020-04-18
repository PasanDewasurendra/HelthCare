package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, Username, Password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcare","root","yasiya");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointment(String no, String type, String date, String did, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error Connecting to the Database for Inserting.";
			}
			// create a prepared statement
			String query = " insert into appointments(`appID`,`appNo`,`appType`,`appDate`,`appDocID`,`appDesc`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, no);
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, did);
			preparedStmt.setString(6, desc);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Successfully Inserted!";
		} catch (Exception e) {
			output = "Error While Registering the Appointment!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readAppointments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for Reading.";
			}

			// Prepare the HTML table to be displayed

			output = "<table border=\"1\"><tr><th>Appointment No</th><th>AppType</th><th>AppDate</th><th>AppDocID</th><th>AppDesc</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from appointments";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);

			// iterate through the rows in the result set

			while (rs.next()) {
				String appID = Integer.toString(rs.getInt("appID"));
				String appNo = rs.getString("appNo");
				String appType = rs.getString("appType");
				String appDate = rs.getString("appDate");
				String appDocID = rs.getString("appDocID");
				String appDesc = rs.getString("appDesc");
				

				// Add into the HTML table

				output += "<tr><td>" + appNo + "</td>";
				output += "<td>" + appType + "</td>";
				output += "<td>" + appDate + "</td>";
				output += "<td>" + appDocID + "</td>";
				output += "<td>" + appDesc + "</td>";
				

				// buttons

				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Appointments.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"appID\" type=\"hidden\" value=\"" + appID + "\">" + "</form></td></tr>";
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

	public String updateAppointment(String id, String no, String type, String date, String did, String desc) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement

			String query = "UPDATE appointments SET appNo=?,appType=?,appDate=?,appDocID=?,appDesc=? "
					+ "WHERE appID=?";
			PreparedStatement preparedSt = con.prepareStatement(query);

			// binding values

	
			preparedSt.setString(1, no);
			preparedSt.setString(2, type);
			preparedSt.setString(3, date);
			preparedSt.setString(4, did);
			preparedSt.setString(5, desc);
			preparedSt.setInt(6, Integer.parseInt(id));
			

			// execute the statement

			preparedSt.execute();
			con.close();
			output = "Successfully Updated! ";
		} catch (Exception e) {
			output = "Error while updating the Appointment Details!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointment(String appID) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement

			String query = "delete from appointments where appID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(appID));

			// execute the statement

			preparedStmt.execute();
			con.close();
			output = "Appointment Successfully Deleted! ";
		} catch (Exception e) {
			output = "Error while deleting the Appointment!";
			System.err.println(e.getMessage());
		}
		return output;
	}

}

