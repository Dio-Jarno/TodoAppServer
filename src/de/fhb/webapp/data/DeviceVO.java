package de.fhb.webapp.data;

public class DeviceVO {

	protected int id;
	protected String deviceId; // string da typ noch unbekannt
	protected String deviceInfo;
	
	public DeviceVO(){
		this.id = 0;
		this.deviceId = "";
		this.deviceInfo = "";
	}
	
	public DeviceVO(int id, String deviceId, String deviceInfo){
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.deviceInfo = deviceInfo;
	}
	
	public String toString(){
		StringBuffer values = new StringBuffer();
		values.append(this.getId());
		values.append(",'");
		values.append(this.getDeviceId());
		values.append(",'");
		values.append(this.getDeviceInfo());
		values.append(",'");
		return values.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceID(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
}
