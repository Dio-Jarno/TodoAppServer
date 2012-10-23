package de.fhb.webapp.controller.web.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.fhb.webapp.access.database.DatabaseAccess;
import de.fhb.webapp.commons.web.HttpRequestActionBase;
import de.fhb.webapp.data.TodoVO;

/**
 * Action to manage a HttpServletRequest.
 * 
 * @author Arvid Grunenberg
 * @date 30.12.2011
 * @version 0.1
 */
public class LoadTodosAction extends HttpRequestActionBase {

	protected DatabaseAccess databaseAccess;
	
	public LoadTodosAction() {
		super();
		databaseAccess = new DatabaseAccess();
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
		List<TodoVO> todos = databaseAccess.loadTodos();
		JSONArray jsonArray = new JSONArray();
		JSONObject json;
		if (todos != null) {
			int length = todos.size();
			for (int i=0; i<length; i++) {
				TodoVO todo = (TodoVO)todos.get(i);
				if (todo.getId() != 0) {
					json = new JSONObject();
					json.put("id", todo.getId());
					json.put("name", todo.getName());
					json.put("place", todo.getPlace());
					json.put("placemark_latitude", todo.getPlacemark_latitude());
					json.put("placemark_longitude", todo.getPlacemark_longitude());
					json.put("details", todo.getDetails());
					json.put("dueAt", todo.getDueAt());
					json.put("done", todo.isDone());
					jsonArray.add(json);
				}
			}
		}
		try {
			response.getWriter().write(jsonArray.toJSONString());
			System.out.println("Todos aus der Datenbank als JSON-String an Client geschickt: " + jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
