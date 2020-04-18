package com;

import model.Patient;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Patient")
public class PatientService {

	Patient PatientObj = new Patient();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPatients() {
		return PatientObj.readPatients();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String insertPatient(
			@FormParam("pName") String pName, 
			@FormParam("pAddress") String pAddress,
			@FormParam("pDOB") String pDOB, 
			@FormParam("pAge") String pAge,
			@FormParam("pGender") String pGender, 
			@FormParam("pEmail") String pEmail,
			@FormParam("pTelephone") String pTelephone) {
		
		String output = PatientObj.insertPatient(pName, pAddress, pDOB, pAge, pGender, pEmail, pTelephone);
		return output;
		
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updatePatient(String PatientData) {
		
		// Convert the input string to a JSON object
		
		JsonObject PatientObject = new JsonParser().parse(PatientData).getAsJsonObject();
		
		// Read the values from the JSON object
		
		String pID = PatientObject.get("pID").getAsString();
		String pName = PatientObject.get("pName").getAsString();
		String pAddress = PatientObject.get("pAddress").getAsString();
		String pDOB = PatientObject.get("pDOB").getAsString();
		String pAge = PatientObject.get("pAge").getAsString();
		String pGender = PatientObject.get("pGender").getAsString();
		String pEmail = PatientObject.get("pEmail").getAsString();
		String pTelephone = PatientObject.get("pTelephone").getAsString();
		String output = PatientObj.updatePatient(pID, pName, pAddress, pDOB, pAge, pGender,pEmail,pTelephone);
		
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String PatientData) {
		
		// Convert the input string to an XML document
		
		Document doc = Jsoup.parse(PatientData, "", Parser.xmlParser());

		// Read the value from the element <PatientID>
		
		String pID = doc.select("pID").text();
		String output = PatientObj.deletePatient(pID);
		return output;
	}

}