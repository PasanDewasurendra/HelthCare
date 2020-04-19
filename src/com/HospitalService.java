package com;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Hospital;


@Path("/Hospital")
public class HospitalService {
	
	Hospital obj = new Hospital();
	
	@RolesAllowed({"admin","patient","doctor"})
	@GET  
	@Path("/")  
	@Produces(MediaType.TEXT_HTML)  
	public String GetHospitalDetails()  {   
		
		return  obj.getHospitalDetails();
		 
	}
	
	@RolesAllowed("admin")
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospitalDetails(String hospitalData) {
		
		JsonObject hpObj = new JsonParser().parse(hospitalData).getAsJsonObject();
		
		String id = hpObj.get("id").getAsString();
		String name = hpObj.get("name").getAsString();
		String address = hpObj.get("address").getAsString();
		String phone = hpObj.get("phone").getAsString();
			
		return obj.UpdateHospitalDetails(id, name, address, phone);
		
	}
	
	@RolesAllowed({"admin","doctor"})
	@GET
	@Path("/Patient")
	@Produces(MediaType.TEXT_HTML)
	public String getPatientInfo() {
		
		return obj.getAllPatients();
	}
	
	@RolesAllowed({"admin","doctor"})
	@GET
	@Path("/Doctor")
	@Produces(MediaType.TEXT_HTML)
	public String getDoctorInfo() {
		
		return obj.getAllDoctors();
	}
	
	
	@RolesAllowed({"admin","patient"})
	@POST
	@Path("/Schedule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String getDoctorSchedule(String data) {
		
		JsonObject scheduleObj = new JsonParser().parse(data).getAsJsonObject();
		String doctor = scheduleObj.get("doctor_name").getAsString();
		String date =  scheduleObj.get("date").getAsString();
		String location = scheduleObj.get("location").getAsString();
		String speciality = scheduleObj.get("speciality").getAsString();
		
		return obj.getDoctorSchedule(doctor, date, location, speciality);	

	}
	
	@RolesAllowed({"patient","admin","doctor"})
	@GET
	@Path("/Schedule")
	@Produces(MediaType.TEXT_HTML)
	public String getDoctorSchedule() {
		
		return obj.getDoctorSchedule();	
		
	}
	
	
	@RolesAllowed("admin")
	@POST
	@Path("/Schedule/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String addDoctorSchedule(@FormParam("doctor_name") String doctor_name, @FormParam("time_from") String time_from, @FormParam("time_to") String time_to, @FormParam("date") String date, @FormParam("speciality") String speciality, @FormParam("location") String location) {
		
		return obj.addDoctorSchedule(doctor_name, time_from, time_to, date, speciality, location);	
		
	}
	

	@RolesAllowed("admin")
	@PUT
	@Path("/Schedule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctorSchedule(String hospitalData) {
		
		JsonObject hpObj = new JsonParser().parse(hospitalData).getAsJsonObject();
		
		String id = null, date = null, doctor_name = null, time_from = null, time_to = null, speciality = null, location = null;
		
		try {
		 id = hpObj.get("id").getAsString();
		 doctor_name = hpObj.get("doctor_name").getAsString();
		 time_from = hpObj.get("time_from").getAsString();
		 time_to = hpObj.get("time_to").getAsString();
		 date = hpObj.get("date").getAsString();
		 speciality = hpObj.get("speciality").getAsString();
		 location = hpObj.get("location").getAsString();
		
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
			
		return obj.updateDoctorSchedule(id, doctor_name, time_from, time_to, date, speciality, location);
	}
	
	@RolesAllowed("admin")
	@DELETE
	@Path("/Schedule")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctorSchedule(String schedule) {
		
		Document doc = Jsoup.parse(schedule,"",Parser.xmlParser());
		String id = doc.select("id").text();

		return obj.deleteDoctorSchedule(id);
	}
	
	
//	@Path("/Appointment")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String confirmAppintment(String apData){
//		
//		return obj.confirmAppointment(apData);
//	}
	
}
