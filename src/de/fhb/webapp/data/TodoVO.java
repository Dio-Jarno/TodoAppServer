package de.fhb.webapp.data;

public class TodoVO {
	
	protected int id;
	protected String name;
	protected String details;
	protected float placemark_latitude;
	protected float placemark_longitude;
	protected String place;
	protected String dueAt;
	protected boolean done;
	
	public TodoVO() {
		this.id = 0;
		this.name = "";
		this.details = "";
		this.placemark_latitude = 0.0f;
		this.placemark_longitude = 0.0f;
		this.place = "";
		this.dueAt = "";
		done = false;
	}
	
	public TodoVO(String name) {
		this();
		this.name = name;
	}
	
	public TodoVO(int id, String name, String details, float placemark_latitude, float placemark_longitude, String place, String dueAt, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.details = details;
		this.placemark_latitude = placemark_latitude;
		this.placemark_longitude = placemark_longitude;
		this.place = place;
		this.dueAt = dueAt;
		this.done = done;
	}
	
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
		values.append("'");
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
	
	public String getDueAt() {
		return dueAt;
	}
	
	public void setDueAt(String dueAt) {
		this.dueAt = dueAt;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
