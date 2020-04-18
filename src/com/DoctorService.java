package com;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;

import model.Doctor;

//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Doctors")
public class DoctorService {
	Doctor doctorObj = new Doctor();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors() {
		return doctorObj.readDoctors();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDoctor(@FormParam("doctorName") String doctorName, @FormParam("doctorAddress") String doctorAddress,
			@FormParam("doctorEmail") String doctorEmail, @FormParam("doctorPhone") String doctorPhone, @FormParam("doctorSpec") String doctorSpec){
		String output = doctorObj.insertDoctor(doctorName, doctorAddress, doctorEmail, doctorPhone, doctorSpec);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String doctorData) {
		// Convert the input string to a JSON object
		JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();
		// Read the values from the JSON object
		String doctorID = doctorObject.get("doctorID").getAsString();
		String doctorName = doctorObject.get("doctorName").getAsString();
		String doctorAddress = doctorObject.get("doctorAddress").getAsString();
		String doctorEmail = doctorObject.get("doctorEmail").getAsString();
		String doctorPhone = doctorObject.get("doctorPhone").getAsString();
		String doctorSpec = doctorObject.get("doctorSpec").getAsString();
		String output = doctorObj.updateDoctor(doctorID, doctorName, doctorAddress, doctorEmail, doctorPhone, doctorSpec);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String doctorData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(doctorData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String doctorID = doc.select("doctorID").text();
		String output = doctorObj.deleteDoctor(doctorID);
		return output;
	}

}
