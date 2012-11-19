package de.fhb.webapp.access.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
	protected final String PASSWORD = "";//"fhb_forensik";
	
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
	
	public List<TodoVO> loadTodos() {
		List<TodoVO> todos = new ArrayList<TodoVO>();
		TodoVO todo;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String select = "SELECT * FROM todo";
			ResultSet resultTodos = statement.executeQuery(select);
			System.out.println("SQL-Query ausgef체hrt: " + select);
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
	 * @param todo - The given todo, which should inserted.
	 * @return The todo's id from the database.
	 */
	public int insertTodo(TodoVO todo) {
		int id = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String insert = "INSERT INTO todo VALUES (" + todo.toString() + ");";
			int updateCount = statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
			System.out.println("SQL-Query ausgef체hrt: " + insert);
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
	 * This method is for inserting a new todo. It creates a SQL statement and sends it to the database.
	 * 
	 * @param todo - The given todo, which should insert.
	 * @param deviceId - The device ID from which the todo is inserted.
	 * @return The todo's id from the database.
	 */
	public int insertTodo(TodoVO todo, String deviceId) {
		int id = 0;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String insert = "INSERT INTO todo VALUES (" + todo.toString() + ");";
			int updateCount = statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
			System.out.println("SQL-Query ausgef체hrt: " + insert);
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
	 */
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
			System.out.println("SQL-Query ausgef체hrt: " + update);
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

	/**
	 * This method is updating a given todo by a specific device.
	 * 
	 * @param todo - The given todo.
	 * @param deviceId - The device ID from which the todo is updated.
	 */
	public void updateTodo(TodoVO todo, String deviceId) {
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
			System.out.println("SQL-Query ausgef체hrt: " + update);
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
	
	/**
	 * This method deletes a todo by the given id.
	 * 
	 * @param id - The given todo ID.
	 */
	public void deleteTodo(int id) {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String delete = "DELETE FROM todo WHERE id=" + id;
			int updateCount = statement.executeUpdate(delete);
			System.out.println("SQL-Query ausgef체hrt: " + delete);
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
	
	/**
	 * This method deletes a todo by the given id and device ID.
	 * 
	 * @param id - The given todo ID.
	 * @param deviceId - The given device ID.
	 */
	public void deleteTodo(int id, String deviceId) {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String delete = "DELETE FROM todo WHERE id=" + id + " AND deviceId=" + deviceId;
			int updateCount = statement.executeUpdate(delete);
			System.out.println("SQL-Query ausgef체hrt: " + delete);
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
	
	/**
	 * Loads all devices from database.
	 * 
	 * @return List<DeviceVO>
	 */
	public List<DeviceVO> loadDevices(){
		List<DeviceVO> devices = new ArrayList<DeviceVO>();
		DeviceVO device;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String select = "SELECT * FROM devices";
			ResultSet resultDevices = statement.executeQuery(select);
			System.out.println("SQL-Query ausgef웘rt: " + select);
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
}
