package org.eemtn.tnt.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eemtn.tnt.dao.trackingobject.TrackingObjectDao;
import org.eemtn.tnt.dao.user.UserDao;
import org.eemtn.tnt.entity.TrackingObject;
import org.eemtn.tnt.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Init the database
 */
public class DataBaseInitializer
{

	private TrackingObjectDao trackingObjectDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}


	public DataBaseInitializer(UserDao userDao,
			                   TrackingObjectDao trackingObjectDao,
			                   PasswordEncoder passwordEncoder)
	{
		this.userDao = userDao;
		this.trackingObjectDao = trackingObjectDao;
		this.passwordEncoder = passwordEncoder;
	}


	public void initDataBase()
	{
		List<HashMap<String,Float>> markerCoordinates = initMarkerCoordinates();
		
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		this.userDao.save(adminUser);

		
		for (int i=0; i<markerCoordinates.size(); i++)
		{
			TrackingObject trackObj = new TrackingObject();
			trackObj.setArticleId("Beispiel Art-Nr. "+i);
			trackObj.setName("Beispielname "+i);
			trackObj.setNameSpecific("Spez. Beispielname "+i);
			trackObj.setObjectId("HB00000"+i);
			trackObj.setLatitude(markerCoordinates.get(i).get("lat"));
			trackObj.setLongitude(markerCoordinates.get(i).get("lng"));
			this.trackingObjectDao.save(trackObj);
		}
	}
	
	private List<HashMap<String,Float>> initMarkerCoordinates()
	{
		List<HashMap<String,Float>> markerCoordinates = new ArrayList<HashMap<String,Float>>();
		
		HashMap<String,Float> coord1 = new HashMap<String,Float>();
		coord1.put("lat", 47.79816f);
		coord1.put("lng", 10.33239f);
		markerCoordinates.add(coord1);

		coord1 = new HashMap<String,Float>();
		coord1.put("lat", 47.89816f);
		coord1.put("lng", 10.33239f);
		markerCoordinates.add(coord1);

		coord1 = new HashMap<String,Float>();
		coord1.put("lat", 49.4186f);
		coord1.put("lng", 11.1167f);
		markerCoordinates.add(coord1);
		
		coord1 = new HashMap<String,Float>();
		coord1.put("lat", 50.715243f);
		coord1.put("lng", 12.494535f);
		markerCoordinates.add(coord1);

		coord1 = new HashMap<String,Float>();
		coord1.put("lat", 48.69331f);
		coord1.put("lng", 9.18557f);
		markerCoordinates.add(coord1);

		return markerCoordinates;
	}
	

}