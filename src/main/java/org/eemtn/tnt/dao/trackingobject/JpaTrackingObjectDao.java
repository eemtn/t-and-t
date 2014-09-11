package org.eemtn.tnt.dao.trackingobject;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eemtn.tnt.dao.JpaDao;
import org.eemtn.tnt.entity.TrackingObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA Implementation of a {@link TrackingObjectDao}.
 * 
 * @author M. Neugebauer
 */
public class JpaTrackingObjectDao extends JpaDao<TrackingObject,Long> implements TrackingObjectDao
{

	public JpaTrackingObjectDao()
	{
		super(TrackingObject.class);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TrackingObject> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<TrackingObject> criteriaQuery = builder.createQuery(TrackingObject.class);

		Root<TrackingObject> root = criteriaQuery.from(TrackingObject.class);
		criteriaQuery.orderBy(builder.desc(root.get("objectId")));
		
		TypedQuery<TrackingObject> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}


}
