package de.fhb.webapp.controller.web.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class DeleteTodoAction extends HttpRequestActionBase {

	protected DatabaseAccess databaseAccess;
	
	public DeleteTodoAction() {
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
		System.out.println("Todo mit ID zum Löschen erhalten: " + request.getParameter("todo"));
		int id = Integer.valueOf(request.getParameter("todo"));
		databaseAccess.deleteTodo(id);
	}

}
