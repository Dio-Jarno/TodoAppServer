package de.fhb.webapp.controller.web.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.fhb.webapp.access.database.DatabaseAccess;
import de.fhb.webapp.commons.web.HttpRequestActionBase;
import de.fhb.webapp.data.TodoVO;
import de.fhb.webapp.manager.DeviceManager;

/**
 * Action to manage a HttpServletRequest.
 * 
 * @author Arvid Grunenberg
 * @date 30.12.2011
 * @version 0.1
 */
public class UpdateTodoAction extends HttpRequestActionBase {

	protected DatabaseAccess databaseAccess;
	protected DeviceManager deviceManager;
	
	public UpdateTodoAction() {
		super();
		databaseAccess = new DatabaseAccess();
		deviceManager = new DeviceManager();
	}
	
	/**
     * This function forwards 
     * 
     * @param request - HttpServletRequest, which came from the browser.
     * @param response - HttpServletResponse, which is sent back to the browser as answer.
	 * @throws ServletException When the action cannot forward to the page, i.e. the site is not availably.
     */
	@Override
	public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String jsonString = request.getParameter("todo");
		System.out.println("JSON vom Client erhalten: " + jsonString);
		try {
			JSONParser parser = new JSONParser();
			JSONObject jObject = (JSONObject) parser.parse(jsonString);
			TodoVO todo = new TodoVO(Integer.valueOf((String)jObject.get("id")), (String)jObject.get("name"), (String)jObject.get("details"), 
					Float.valueOf((String)jObject.get("placemark_latitude")), Float.valueOf((String)jObject.get("placemark_longitude")),
					(String)jObject.get("place"), (String)jObject.get("dueAt"), Boolean.valueOf((String)jObject.get("done")));
			databaseAccess.updateTodo(todo);
			deviceManager.sendPushNotificationToOtherDevices(todo.getId(), (String)jObject.get("deviceId"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
