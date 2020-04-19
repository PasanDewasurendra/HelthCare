package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Pharmacy {
	
	public Connection connect() {
		
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/helthcare");
			System.out.println("Database Connected.");
			
		}catch (Exception e) {
			System.out.println("DB Connecting Faild.");
			e.printStackTrace();
		}
		return con;
	}
	
	public String getAllMedicine() {
		
		String output = "<table><tr><th>Medicine ID</th><th>Name</th><th>Type</th><th>Manufacture</th><th>Description</th></tr>";
		
		try {
			
			Connection con = connect();

			String query = "SELECT * FROM medicine";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				String manufac = rs.getString("manufacture");
				String desc = rs.getString("description");
				
				String row = "<tr><td>"+id+"</td><td>"+id+"</td><td>"+name+"</td><td>"+type+"</td><td>"+manufac+"</td><td>"+desc+"</td></tr>";
				output = output.concat(row);

			}	
			output = output.concat("</table>");
			
		}catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println(output);
		
		return output;	
		
	}
	
	public String addMedicine(String medName, String medType, String manuFac, String desc) {
		
		String output = "";
		
		try {	
			Connection con = connect();

			String query = "INSERT INTO medicine(`name`, `type`, `manufacture`, `description`) VALUES(?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, medName);
			ps.setString(2, medType);
			ps.setString(3, manuFac);
			ps.setString(4, desc);
			ps.execute();
			con.close();
			output = " New Medicine Added.";
				
		}catch (Exception e) {
			// TODO: handle exception	
			e.getStackTrace();
		}
		System.out.println(output);
		return output;
	}
	
	public String updateMedicine(String id,String name, String type, String manuFac, String desc) {
		
		String output = "";
		
		try {	
			Connection con = connect();
			
			String query = "UPDATE medicine SET name=?, type = ?, manufacture = ?, description = ? WHERE id=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, manuFac);
			ps.setString(4, desc);
			ps.setInt(5,Integer.parseInt(id));
			ps.executeUpdate();
			con.close();
			output = "Medicine Updated.";
			
		}catch (Exception e) {
			// TODO: handle exception
			output = "Error While Medicine Updating.";
			e.printStackTrace();
		}
		
		return output;
	}
	
	public String removeMedicine(String id) {
		
		String output = "";
		
		try {	
			Connection con = connect();
			
			String query = "DELETE FROM medicine WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,Integer.parseInt(id));
			ps.execute();
			con.close();
			output = "Medicine item Deleted.";
			
		}catch (Exception e) {
			// TODO: handle exception
			output = "Error While Medicine Deleting.";
			e.printStackTrace();
		}
		
		return output;
		
	}

}
