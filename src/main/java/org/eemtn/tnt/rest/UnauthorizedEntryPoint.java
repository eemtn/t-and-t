package org.eemtn.tnt.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


/**
 * {@link AuthenticationEntryPoint} rejects requests with appropriate error message 
 * 
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException
	{
		response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				"Unauthorized Access: Authentication token was either missing or invalid.");
	}

}