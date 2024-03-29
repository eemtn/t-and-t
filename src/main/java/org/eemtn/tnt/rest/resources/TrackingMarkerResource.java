package org.eemtn.tnt.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;





import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.eemtn.tnt.JsonViews;
import org.eemtn.tnt.dao.trackingmarker.TrackingMarkerDao;
import org.eemtn.tnt.entity.TrackingMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Path("/trackMark")
public class TrackingMarkerResource
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TrackingMarkerDao trackMarkDao;
	
	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<TrackingMarker> allEntries = this.trackMarkDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public TrackingMarker read(@PathParam("id") Long id)
	{
		this.logger.info("read(id)");

		TrackingMarker trackingMarker = this.trackMarkDao.find(id);
		if (trackingMarker == null) {
			throw new WebApplicationException(404);
		}
		return trackingMarker;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TrackingMarker create(TrackingMarker trackingMarker)
	{
		this.logger.info("create(): " + trackingMarker);

		return this.trackMarkDao.save(trackingMarker);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public TrackingMarker update(@PathParam("id") Long id, TrackingMarker trackingMarker)
	{
		this.logger.info("update(): " + trackingMarker);

		return this.trackMarkDao.save(trackingMarker);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id)
	{
		this.logger.info("delete(id)");

		this.trackMarkDao.delete(id);
	}
	
	private boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.toString().equals("admin")) {
				return true;
			}
		}

		return false;
	}




	
}
