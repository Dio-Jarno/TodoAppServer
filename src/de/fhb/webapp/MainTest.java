package de.fhb.webapp;

import de.fhb.webapp.access.database.DatabaseAccess;
import de.fhb.webapp.data.TodoVO;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseAccess databaseAccess = new DatabaseAccess();
		TodoVO todo = new TodoVO("Test");
		int id = databaseAccess.insertTodo(todo);
		System.out.println("ID des Todos ist: " + id);
	}

}
