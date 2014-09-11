package org.eemtn.tnt.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.codehaus.jackson.map.annotate.JsonView;
import org.eemtn.tnt.JsonViews;

@javax.persistence.Entity
public class TrackingMarker implements Entity
{

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String objectId;
	
	@Column
	private Float lat;
	
	@Column
	private Float lng;
	
	@Column
	private String message;
	
	@Column
	private boolean focus;
	
	@Column
	private boolean draggable;
	
	@JsonView(JsonViews.User.class)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@JsonView(JsonViews.User.class)
	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	@JsonView(JsonViews.User.class)
	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	@JsonView(JsonViews.User.class)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonView(JsonViews.User.class)
	public boolean getFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	@JsonView(JsonViews.User.class)
	public boolean getDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}


	
	public String toString()
	{
		return String.format("TrackingMarker[%d, %s]", this.id, this.message);
	}
	
}


