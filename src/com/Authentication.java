package com;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.tomcat.util.codec.binary.Base64;


@Provider
public class Authentication implements ContainerRequestFilter  {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PRIFIX = "Basic";
	private static final String SECURED_URL_PREFIX = "Hospital";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			
			if(authHeader != null && authHeader.size()>0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PRIFIX, "");
				String decodedByte = new String(Base64.decodeBase64(authToken));
				StringTokenizer tokenizer = new StringTokenizer(decodedByte,":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if("admin".equals(username) && "admin".equals(password)) {
					return;
				}
			}
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User cannot access the resouce.")
					.build();
			
			requestContext.abortWith(unauthorizedStatus);
			
		}
		
	}


	
}
