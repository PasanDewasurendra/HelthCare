package com;

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

import model.Pharmacy;

@Path("/Medicine")
public class PharmacyService {
	
	Pharmacy phObj = new Pharmacy();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getMedicine() {
		
		return phObj.getAllMedicine();	
		
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addMedicine(@FormParam("med_name") String medName, @FormParam("med_type") String medType, @FormParam("manufac") String manuFac, @FormParam("desc") String desc) {
		
		return phObj.addMedicine(medName, medType, manuFac, desc);	
		
	} 
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateMedicine(String medicine) {
		
		JsonObject medObj = new JsonParser().parse(medicine).getAsJsonObject();
		
		String id = medObj.get("id").getAsString();
		String name = medObj.get("name").getAsString();
		String type = medObj.get("type").getAsString();
		String manufac = medObj.get("manufacture").getAsString();
		String desc = medObj.get("description").getAsString();
			
		return phObj.updateMedicine(id, name, type, manufac, desc);
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMedicine(String medicine) {
		
		Document doc = Jsoup.parse(medicine,"",Parser.xmlParser());
		String id = doc.select("id").text();

		return phObj.removeMedicine(id);
	}

}
