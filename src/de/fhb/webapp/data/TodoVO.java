package de.fhb.webapp.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TodoVO {
	
	protected int id;
	protected String name;
	protected String details;
	protected float placemark_latitude;
	protected float placemark_longitude;
	protected String place;
	protected int radius;
	protected String dueAt;
	protected Date modifiedAt;
	protected boolean done;
	
	public TodoVO() {
		this.id = 0;
		this.name = "";
		this.details = "";
		this.placemark_latitude = 0.0f;
		this.placemark_longitude = 0.0f;
		this.place = "";
		this.radius = 0;
		this.dueAt = "";
		this.modifiedAt = null;
		done = false;
	}
	
	public TodoVO(String name) {
		this();
		this.name = name;
	}
	
	public TodoVO(int id, String name, String details, float placemark_latitude, float placemark_longitude, String place, int radius, String dueAt, Date modifiedAt, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.details = details;
		this.placemark_latitude = placemark_latitude;
		this.placemark_longitude = placemark_longitude;
		this.place = place;
		this.radius = radius;
		this.dueAt = dueAt;
		this.modifiedAt = modifiedAt;
		this.done = done;
	}
	
	/**
	 * Represents the todo as a string for sql insert query. The order must be exactly the same as in the database!
	 */
	public String toString() {
		StringBuffer values = new StringBuffer();
		values.append(this.getId());
		values.append(",'");
		values.append(this.getName());
		values.append("','");
		values.append(this.getPlace());
		values.append("',");
		values.append(this.getPlacemark_latitude());
		values.append(",");
		values.append(this.getPlacemark_longitude());
		values.append(",'");
		values.append(this.getDetails());
		values.append("','");
		values.append(this.getDueAt());
		values.append("','");
		values.append(this.isDone());
		values.append("',");
		values.append(this.getRadius());
		values.append(",");
		values.append(0);
		values.append(",");
		values.append(this.getModifiedAtAsSQLString());
		return values.toString();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public float getPlacemark_latitude() {
		return placemark_latitude;
	}
	
	public void setPlacemark_latitude(float placemark_latitude) {
		this.placemark_latitude = placemark_latitude;
	}
	
	public float getPlacemark_longitude() {
		return placemark_longitude;
	}
	
	public void setPlacemark_longitude(float placemark_longitude) {
		this.placemark_longitude = placemark_longitude;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getDueAt() {
		return dueAt;
	}
	
	public void setDueAt(String dueAt) {
		this.dueAt = dueAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedAtAsSQLString() {
		if (modifiedAt != null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(modifiedAt);
		}
		return "null";
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


}
