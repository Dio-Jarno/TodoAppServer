package de.fhb.webapp.access.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.fhb.webapp.data.TodoVO;

/**
 * This class creates the connection to the database.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.2
 *
 */
public class DatabaseAccess {

	protected final static String DB = "todoapp";
	protected final String URL = "jdbc:mysql://localhost:3306/" + DB;
	protected final String USER = "root";
	protected final String PASSWORD = "fhb_forensik";
	
	protected final static String DRIVER = "com.mysql.jdbc.Driver";
	
	protected Connection connection;
	
	/**
	 * Default-Constructor
	 */
	public DatabaseAccess() {
		connection = null;
		loadDriver();
	}
	
	/**
	 * Loads the current driver.
	 * @return True if the driver is loaded.
	 */
	protected boolean loadDriver() {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (Exception e) {
			System.out.println("Failed to load MySQL JDBC driver.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<TodoVO> loadTodos() {
		List<TodoVO> todos = new ArrayList<TodoVO>();
		TodoVO todo;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String select = "SELECT * FROM todo";
			ResultSet resultTodos = statement.executeQuery(select);
			System.out.println("SQL-Query ausgeführt: " + select);
			while (resultTodos.next()) {
				todo = new TodoVO();
				todo.setId(resultTodos.getInt("id"));
				todo.setName(resultTodos.getString("name"));
				todo.setPlace(resultTodos.getString("place"));
				todo.setPlacemark_latitude(resultTodos.getFloat("placemark_latitude"));
				todo.setPlacemark_longitude(resultTodos.getFloat("placemark_longitude"));
				todo.setDetails(resultTodos.getString("details"));
				todo.setDueAt(resultTodos.getString("dueAt"));
				todo.setDone(resultTodos.getBoolean("done"));
				todos.add(todo);
			}
			resultTodos.close();
			statement.close();
		} catch (Exception e) {
			System.out.println("Es konnte keine Verbindung zur Datenbank aufgebaut werden.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try { 
					connection.close(); 
				} catch(Exception e) {
					System.out.println("Die Verbindung zur Datenbank konnte nicht geschlossen werden.");
					e.printStackTrace(); 
				}
			}
		}
		return todos;
	}
	
	/**
	 * This method is for inserting a new todo. It creates a SQL statement and sends it to the database.
	 * 
	 * @param todo - The given todo, which should insert.
	 * @return The todo's id from the database.
	 */
	public int insertTodo(TodoVO todo) {
		int id = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String insert = "INSERT INTO todo VALUES (" + todo.toString() + ");";
			int updateCount = statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
			System.out.println("SQL-Query ausgeführt: " + insert);
			if (updateCount > 0) {
				ResultSet resultKey = statement.getGeneratedKeys();
				if (resultKey.next()) {
					id = resultKey.getInt(1);
				}
				resultKey.close();
			}
			statement.close();
		} catch (Exception e) {
			System.out.println("Es konnte keine Verbindung zur Datenbank aufgebaut werden.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try { 
					connection.close(); 
				} catch(Exception e) {
					System.out.println("Die Verbindung zur Datenbank konnte nicht geschlossen werden.");
					e.printStackTrace(); 
				}
			}
		}
		return id;
	}

	public void updateTodo(TodoVO todo) {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String update = "UPDATE todo SET name='" + todo.getName() +
					"',place='" + todo.getPlace() +
					"',placemark_latitude=" + todo.getPlacemark_latitude() +
					",placemark_longitude=" + todo.getPlacemark_longitude() +
					",details='" + todo.getDetails() +
					"',dueAt='" + todo.getDueAt() +
					"',done='" + todo.isDone() +
					"' WHERE id = " + todo.getId();
			int updateCount = statement.executeUpdate(update);
			System.out.println("SQL-Query ausgeführt: " + update);
			statement.close();
		} catch (Exception e) {
			System.out.println("Es konnte keine Verbindung zur Datenbank aufgebaut werden.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try { 
					connection.close(); 
				} catch(Exception e) {
					System.out.println("Die Verbindung zur Datenbank konnte nicht geschlossen werden.");
					e.printStackTrace(); 
				}
			}
		}
	}

	public void deleteTodo(int id) {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String delete = "DELETE FROM todo WHERE id=" + id;
			int updateCount = statement.executeUpdate(delete);
			System.out.println("SQL-Query ausgeführt: " + delete);
			statement.close();
		} catch (Exception e) {
			System.out.println("Es konnte keine Verbindung zur Datenbank aufgebaut werden.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try { 
					connection.close(); 
				} catch(Exception e) {
					System.out.println("Die Verbindung zur Datenbank konnte nicht geschlossen werden.");
					e.printStackTrace(); 
				}
			}
		}
	}
	
}
