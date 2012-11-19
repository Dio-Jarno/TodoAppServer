package de.fhb.webapp.manager;

import java.util.ArrayList;
import java.util.List;

import de.fhb.webapp.access.database.DatabaseAccess; 
import de.fhb.webapp.data.DeviceVO;

/**
 * This class manages all devices an notifies them in case of changes.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.1
 * @date 16.11.2012
 *
 */
public class DeviceManager {

	protected DatabaseAccess databaseAccess;
	
	/**
	 * @param id - Id of the todo which should be notified to the other devices.
	 * @param deviceId - Id of the the device where the todo changes comes from.
	 */
	public void sendPushNotificationToOtherDevices(int id, String deviceId){
		databaseAccess = new DatabaseAccess();
		List<DeviceVO> devices = databaseAccess.loadDevices();
		devices.remove(deviceId);
		
		for(int i = 0; i<devices.size(); i++){
			devices.get(i);
		}
		
	}
	
}
