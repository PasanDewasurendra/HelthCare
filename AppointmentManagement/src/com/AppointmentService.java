package com;

import model.Appointment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Appointments")
public class AppointmentService {

	Appointment AppointmentObj = new Appointment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointments() {
		return AppointmentObj.readAppointments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String insertAppointment(@FormParam("appNo") String appNo, 
			@FormParam("appType") String appType,
			@FormParam("appDate") String appDate, 
			@FormParam("appDocID") String appDocID, 
			@FormParam("appDesc") String appDesc) 
	{
		
		String output = AppointmentObj.insertAppointment(appNo, appType, appDate, appDocID, appDesc);
		return output;
		
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateAppointment(String AppointmentData) {
		
		// Convert the input string to a JSON object
		
		JsonObject AppointmentObject = new JsonParser().parse(AppointmentData).getAsJsonObject();
		
		// Read the values from the JSON object
		
		String appID = AppointmentObject.get("appID").getAsString();
		String appNo = AppointmentObject.get("appNo").getAsString();
		String appType = AppointmentObject.get("appType").getAsString();
		String appDate = AppointmentObject.get("appDate").getAsString();
		String appDocID = AppointmentObject.get("appDocID").getAsString();
		String appDesc = AppointmentObject.get("appDesc").getAsString();

		String output = AppointmentObj.updateAppointment(appID, appNo, appType, appDate, appDocID, appDesc);
		
		return output;
	}

	@DELETE
	@Path("/")  
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppointment(String AppointmentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(AppointmentData, "", Parser.xmlParser());

		// Read the value from the element <AppointmentID>
		String appID = doc.select("appID").text();
		String output = AppointmentObj.deleteAppointment(appID);
		return output;
	}

}
