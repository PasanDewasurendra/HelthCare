package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.DBConnection;

public class Hospital {
	
	String out = "";
	DBConnection db = new DBConnection();
	
	public String getHospitalDetails() {
		
		out = "<table border='1'><tr><th>ID</th><th>Name</th><th>Address</th><th>Phone</th></tr>";
		
		try {
			Connection con = db.connect();

			String query = "SELECT * FROM hospital";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				
				String row = "<tr><td>"+id+"</td><td>"+name+"</td><td>"+address+"</td><td>"+phone+"</td></tr>";
				out = out.concat(row);
			}	
			out = out.concat("</table>");

			
		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(out);
		
		return out;	
	}
	
	public String UpdateHospitalDetails(String id, String name, String address, String phone) {
		
		try {
			Connection con = db.connect();
			
			String query = "UPDATE hospital SET name=?, address=?, phone=? WHERE id=?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, address);
			ps.setString(3, phone);
			ps.setString(4, id);
			ps.executeUpdate();
			con.close();
			
			out = "Hospital Data Updated.";
			
		}catch (Exception e) {
			// TODO: handle exception
			out = "Error While Hospital Data Updating.";
			e.printStackTrace();
		}
		
		return out;
		
	}
	
	public String getAllPatients() {
		
		out = "<table border='1'><tr><th>Patient ID</th><th>Patient Name</th><th>Patient Address</th><th>Patient Phone</th><th>Patient Email</th></tr>";
		
		try {
			Connection con = db.connect();

			String query = "SELECT * FROM patient";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				
				String row = "<tr><td>"+id+"</td><td>"+name+"</td><td>"+address+"</td><td>"+phone+"</td><td>"+email+"</td></tr>";
				out = out.concat(row);

			}	
			out = out.concat("</table>");

			
		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(out);
		
		return out;	
	}
	
	public String getAllDoctors() {
		
		out = "<table border='1'><tr><th>Doctor ID</th><th>Doctor Name</th><th>Doctor Address</th><th>Doctor Phone</th><th>Speciality</th<th>Qualification</th></tr>";
		
		try {
			Connection con = db.connect();

			String query = "SELECT * FROM doctor";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String specs = rs.getString("speciality");
				String qualify = rs.getString("qualification");
				
				String row = "<tr><td>"+id+"</td><td>"+name+"</td><td>"+address+"</td><td>"+phone+"</td><td>"+specs+"</td><td>"+qualify+"</td></tr>";
				out = out.concat(row);

			}	
			out = out.concat("</table>");

		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(out);
		
		return out;	
	}
	
	
	public String getDoctorSchedule() {
		
		out = "<table border='1'><tr><th>Date</th><th>Doctor Name</th><th>Speciality</th><th>Location</th><th>Time From</th><th>Time To</th></tr>";
		
	
		try {
			Connection con = db.connect();

			String query = "SELECT * FROM doctor_schedule";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String date = rs.getString("date");
				String name = rs.getString("doctor_name");
				String loc = rs.getString("location");
				String specs = rs.getString("speciality");
				String timef = rs.getString("time_from");
				String timet = rs.getString("time_to");
				
				String row = "<tr><td>"+date+"</td><td>"+name+"</td><td>"+specs+"</td><td>"+loc+"</td><td>"+timef+"</td><td>"+timet+"</td></tr>";
				out = out.concat(row);

			}	
			out = out.concat("</table>");

			
		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(out);
		
		return out;	
	}
	
	
	public String getDoctorSchedule(String doctor, String date, String location, String speciality) {
		
		out = "<table border='1'><tr><th>Doctor Name</th><th>Date</th><th>Time: from</th><th>Time: to</th><th>Speciality</th<th>Location</th></tr>";
		
		try {
			Connection con = db.connect();

			String query = "SELECT * FROM doctor_schedule WHERE doctor_name = '"+doctor+"' AND date = '"+date+"' AND location = '"+location+"' AND speciality = '"+speciality+"'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String dName = rs.getString("doctor_name");
				String dDate = rs.getString("date");
				String tFrom = rs.getString("time_from");
				String tTo = rs.getString("time_to");
				String specs = rs.getString("speciality");
				String loc = rs.getString("location");
				
				String row = "<tr><td>"+dName+"</td><td>"+dDate+"</td><td>"+tFrom+"</td><td>"+tTo+"</td><td>"+specs+"</td><td>"+loc+"</td></tr>";
				out = out.concat(row);

			}	
			out = out.concat("</table>");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return out;
		
	}
	
	public String addDoctorSchedule(String doctor_name, String time_from, String time_to, String date, String speciality, String location) {
		
		try {	
			Connection con = db.connect();

			String query = "INSERT INTO doctor_schedule(`doctor_name`, `time_from`, `time_to`, `date`, `speciality`, `location`) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, doctor_name);
			ps.setString(2, time_from);
			ps.setString(3, time_to);
			ps.setString(4, date);
			ps.setString(5, speciality);
			ps.setString(6, location);
			ps.execute();
			con.close();
			out = " New Shedule Inserted.";
				
		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(out);
		return out;
	}
	
	public String updateDoctorSchedule(String id,String doctor_name, String time_from, String time_to, String date, String speciality, String location ) {
		
		try {	
			Connection con = db.connect();

			String query = "UPDATE doctor_schedule SET doctor_name=?, time_from = ?, time_to = ?, date = ?, speciality = ?, location = ? WHERE id=?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, doctor_name);
			ps.setString(2, time_from);
			ps.setString(3, time_to);
			ps.setString(4, date);
			ps.setString(5,speciality);
			ps.setString(6, location);
			ps.setInt(7, Integer.parseInt(id));
			ps.executeUpdate();
			con.close();
			out = "Schedule Data Updated.";
			
		}catch (Exception e) {
			// TODO: handle exception
			out = "Error While Schedule Data Updating.";
			e.printStackTrace();
		}
		
		return out;
	}
	
	public String deleteDoctorSchedule(String id) {
		
		try {	
			Connection con = db.connect();
			
			String query = "DELETE FROM doctor_schedule WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,Integer.parseInt(id));
			ps.execute();
			con.close();
			out = "Schedule Data Deleted.";
			
		}catch (Exception e) {
			// TODO: handle exception
			out = "Error While Schedule Data Deleting.";
			e.printStackTrace();
		}
		
		return out;
		
	}
	
	    
//	public String confirmAppointment(String apData) {
//		
//		return "";
//	}
	
	

}
