package org.eemtn.tnt.dao.trackingmarker;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eemtn.tnt.dao.JpaDao;
import org.eemtn.tnt.entity.TrackingMarker;
import org.eemtn.tnt.entity.TrackingObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA Implementation of a {@link TrackingMarkerDao}.
 * 
 * @author M. Neugebauer
 */
public class JpaTrackingMarkerDao extends JpaDao<TrackingMarker,Long> implements TrackingMarkerDao
{

	public JpaTrackingMarkerDao()
	{
		super(TrackingMarker.class);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TrackingMarker> findAll()
	{
		List<TrackingMarker> resultList = new ArrayList<TrackingMarker>();
		
		
		// for the tracking markers the data is taken from the tracking objects
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<TrackingObject> criteriaQuery = builder.createQuery(TrackingObject.class);

		Root<TrackingObject> root = criteriaQuery.from(TrackingObject.class);
		criteriaQuery.orderBy(builder.desc(root.get("objectId")));
		
		TypedQuery<TrackingObject> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		
		List<TrackingObject> trackObjResultList = typedQuery.getResultList();
		
		for (TrackingObject trackingObject : trackObjResultList)
		{
			TrackingMarker trackMark = new TrackingMarker();
			
			trackMark.setObjectId(trackingObject.getObjectId());
			trackMark.setLat(trackingObject.getLatitude());
			trackMark.setLng(trackingObject.getLongitude());
			trackMark.setMessage(trackingObject.getObjectId());
			trackMark.setFocus(false);
			trackMark.setDraggable(false);
			resultList.add(trackMark);
		}
		
		return resultList;
	}


}
