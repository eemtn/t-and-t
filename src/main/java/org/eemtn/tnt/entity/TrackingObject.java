package org.eemtn.tnt.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.codehaus.jackson.map.annotate.JsonView;
import org.eemtn.tnt.JsonViews;

@javax.persistence.Entity
public class TrackingObject implements Entity
{

	@Id
	@GeneratedValue
	private Long id;

	// TODO bound to system client
	@Column
	private String clientId;
	
	@Column
	private String objectId;
	
	@Column
	private String name;
	
	@Column
	private String nameSpecific;
	
	@Column
	private String articleId;
	
	// TODO bound location object to location
	@Column
	private String location;
	
	@Column
	private String customer;
	
	@Column
	private float longitude;
	
	@Column
	private float latitude;

	@JsonView(JsonViews.Admin.class)
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JsonView(JsonViews.User.class)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@JsonView(JsonViews.User.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(JsonViews.User.class)
	public String getNameSpecific() {
		return nameSpecific;
	}

	public void setNameSpecific(String nameSpecific) {
		this.nameSpecific = nameSpecific;
	}

	@JsonView(JsonViews.User.class)
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@JsonView(JsonViews.User.class)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@JsonView(JsonViews.User.class)
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@JsonView(JsonViews.User.class)
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@JsonView(JsonViews.User.class)
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public String toString()
	{
		return String.format("TrackingObject[%d, %s]", this.id, this.objectId);
	}
	
}


