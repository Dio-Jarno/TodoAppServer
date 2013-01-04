package de.fhb.webapp.access.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fhb.webapp.data.DeviceVO;
import de.fhb.webapp.data.TodoVO;

/**
 * This class creates the connection to the database.
 * 
 * @author Arvid Grunenberg, Thomas Habiger
 * @version 0.3
 * @date 04.11.2012
 *
 */
public class DatabaseAccess {

	/** local db */
	protected final static String DB = "todoapp";
	protected final String URL = "jdbc:mysql://localhost:3306/" + DB;
	protected final String USER = "root";
	protected final String PASSWORD = "fhb_forensik";
	
	/** extern server
	protected final static String DB = "diojarno_todoapp";
	protected final String URL = "jdbc:mysql://johnny.heliohost.org:3306/" + DB;
	protected final String USER = "diojarno_root";
	protected final String PASSWORD = "rr;D$y]nz@R)";
	*/
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
	
	public List<TodoVO> loadTodos(Date timestamp) {
		List<TodoVO> todos = new ArrayList<TodoVO>();
		TodoVO todo;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String select = "SELECT * FROM todo";
			if (timestamp != null) {
				select += " WHERE modifiedAt>'" + timestamp + "'";
			}
			System.out.println("SQL-Query ausgeführt: " + select);
			ResultSet resultTodos = statement.executeQuery(select);
			while (resultTodos.next()) {
				if (!resultTodos.getBoolean("isDeleted")) {
					todo = new TodoVO();
					todo.setId(resultTodos.getInt("id"));
					todo.setName(resultTodos.getString("name"));
					todo.setPlace(resultTodos.getString("place"));
					todo.setPlacemark_latitude(resultTodos.getFloat("placemark_latitude"));
					todo.setPlacemark_longitude(resultTodos.getFloat("placemark_longitude"));
					todo.setRadius(resultTodos.getInt("radius"));
					todo.setDetails(resultTodos.getString("details"));
					todo.setDueAt(resultTodos.getString("dueAt"));
					todo.setModifiedAt(resultTodos.getTimestamp("modifiedAt"));
					todo.setDone(resultTodos.getBoolean("done"));
					todos.add(todo);
				}
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
	 * @param todo - The given todo, which should inserted.
	 * @return The todo's id from the database.
	 */
	public int insertTodo(TodoVO todo) {
		int id = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String insert = "INSERT INTO todo VALUES (" + todo.toString() + ");";
			System.out.println("SQL-Query ausgeführt: " + insert);
			int updateCount = statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
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

	/**
	 * This method is updating a given todo.
	 * 
	 * @param todo - The given todo.
	 * @result The count of updated todos. It has to be 1, because the todo's id is unique.
	 * A count of 0 means the given todo was not found in the database.
	 */
	public int updateTodo(TodoVO todo) {
		int updateCount = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String update = "UPDATE todo SET name='" + todo.getName() +
					"',place='" + todo.getPlace() +
					"',placemark_latitude=" + todo.getPlacemark_latitude() +
					",placemark_longitude=" + todo.getPlacemark_longitude() +
					",radius=" + todo.getRadius() +
					",details='" + todo.getDetails() +
					"',dueAt='" + todo.getDueAt() +
					"',modifiedAt='" + todo.getModifiedAtAsSQLString() +
					"',done='" + todo.isDone() +
					"' WHERE id = " + todo.getId();
			System.out.println("SQL-Query ausgeführt: " + update);
			updateCount = statement.executeUpdate(update);
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
		return updateCount;
	}
	
	/**
	 * This method deletes a todo (sets isDeleted=1) by the given todo.
	 * 
	 * @param todo - The given todo.
	 * @result The count of deleted todos. It has to be 1, because the todo's id is unique.
	 * A count of 0 means the given todo was not found in the database.
	 */
	public int deleteTodo(TodoVO todo) {
		return deleteTodo(todo.getId());
	}
	
	/**
	 * This method deletes a todo (sets isDeleted=1) by the given id.
	 * 
	 * @param id - The given todo ID.
	 * @result The count of deleted todos. It has to be 1, because the todo's id is unique.
	 * A count of 0 means the given todo was not found in the database.
	 */
	public int deleteTodo(int id) {
		int updateCount = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String delete = "UPDATE todo SET isDeleted=1 WHERE id=" + id;
			System.out.println("SQL-Query ausgeführt: " + delete);
			updateCount = statement.executeUpdate(delete);
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
		return updateCount;
	}
	
	/**
	 * Loads all devices from database.
	 * 
	 * @return List<DeviceVO>
	 */
	public List<DeviceVO> loadDevices() {
		List<DeviceVO> devices = new ArrayList<DeviceVO>();
		DeviceVO device;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String select = "SELECT * FROM devices";
			System.out.println("SQL-Query ausgeführt: " + select);
			ResultSet resultDevices = statement.executeQuery(select);
			while (resultDevices.next()) {
				device = new DeviceVO();
				device.setId(resultDevices.getInt("id"));
				device.setDeviceID(resultDevices.getString("deviceId"));
				device.setDeviceInfo(resultDevices.getString("deviceInfo"));
				devices.add(device);
			}
			resultDevices.close();
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
		return devices;
	}
	
	public int insertDevice(DeviceVO device) {
		int updateCount = 0;
		
		return updateCount;
	}
	
}
