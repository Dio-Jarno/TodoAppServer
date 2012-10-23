package de.fhb.webapp.controller.web.actions;

import java.io.IOException;

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
public class InsertTodoAction extends HttpRequestActionBase {

	protected DatabaseAccess databaseAccess;
	
	public InsertTodoAction() {
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
		System.out.println("Todo mit Namen zum Einfügen erhalten: " + request.getParameter("todo"));
		TodoVO todo = new TodoVO(request.getParameter("todo"));
		int id = databaseAccess.insertTodo(todo);
		try {
			response.getWriter().write(id + "");
			System.out.println("ID des Todos an Client geschickt: " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
