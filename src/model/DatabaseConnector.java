package model;
import java.sql.*;
import java.util.ArrayList;

import model.AddressObtainer;
import view.WeatherSystemView;

public class DatabaseConnector {
	public Connection con = null;
	
	//connects to the database
	public Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:src/resources/InfoDb.db");
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//checks if database connection is active
	public boolean isDbConnected() {
		try {
			return !con.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//testing purposes
	public void connectionStatus() {
		if (this.isDbConnected()) {
			System.out.println("Database is connected.\n");
		} else {
			System.out.println("Database is not connected.\n");
		}
	}
	
	//checks if user is registered
	public boolean isRegistered(String macAddress) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from table_user_info where MACAddress = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, macAddress);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("error");
			return false;
			
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	//if user is not recognized within database
	public void registerUser(String macAddress) throws SQLException {
		PreparedStatement preparedStatement = null;
		String insert = "INSERT INTO table_user_info(MACAddress) VALUES(?)";
		try {
			preparedStatement = con.prepareStatement(insert);
			
			preparedStatement.setString(1, macAddress);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			preparedStatement.close();
		}
	}
	
	public void setDbLocationsData(ArrayList<String> locationsList) { 
		PreparedStatement ps = null;
		ResultSet resultSet;
		String query = "select * from table_user_info";
			
		try {
			
			ps = con.prepareStatement(query);
			resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				for (int i = 2; i <= 11; i++) {
					if (resultSet.getString(i) != null) {
						locationsList.add(resultSet.getString(i));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public boolean checkFavouriteExists(WeatherSystemView view) {
		PreparedStatement ps = null;
		ResultSet resultSet;
		String query = "select FavouriteLocation from table_user_info";
			
			try {
				ps = con.prepareStatement(query);
				resultSet = ps.executeQuery();
				
				//checks if a favouriteLocation is set within database
				if (resultSet.getString(1) != null) {
					view.favouriteLocation = resultSet.getString(1);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public void setFavouriteLocation(String location) {
		PreparedStatement preparedStatement = null;
		
		//update location value in database if space is available
		String sql = "UPDATE table_user_info SET FavouriteLocation = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, location);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void registerLocation(String location) throws SQLException {
		PreparedStatement preparedStatement = null;
		PreparedStatement test = null;
		ResultSet resultSet;
		String test1 = "select * from table_user_info";
		int locationCounter = 2;
		boolean duplicate = false;
			
		try {
			
			test = con.prepareStatement(test1);
			resultSet = test.executeQuery();
			
			//checking for empty spot in database to add a location
			int i = 0;
			
			if (resultSet.next()) {
				for (i = locationCounter; i <= 11; i++) {
					if (resultSet.getString(i) == null) {
						locationCounter = i-1;
						break;
					}
					
					// makes sure duplicates do not exist within database
					if (resultSet.getString(i).toLowerCase().equals(location.toLowerCase())) {
						duplicate = true;
						break;
					}
					
					// checks if all location entries are occupied
					if (resultSet.getString(i) != null && i == 11) {
					       locationCounter = 12; //12 indicates no empty location found
					}
				}
			}
			
			//update location value in database if space is available
			if (locationCounter != 12 && !duplicate) {
				String sql = "UPDATE table_user_info SET Location" + locationCounter + " = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, location);
				preparedStatement.executeUpdate();
			} else {
				System.out.println("Max number of locations. Please remove one if you'd like to add another.\n");
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (locationCounter != 12) {
				preparedStatement.close();
			}
		}
	}
	
	public void removeLocation(ArrayList<String> locationsList, String locationToDelete) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		PreparedStatement ps2 = null;
		ResultSet resultSet;
		String query = "select * from table_user_info";
		int locationCounter = 1;
		boolean found = false;
		
		int i;
		try {
			ps2 = con.prepareStatement(query);
			resultSet = ps2.executeQuery();
			
			
			if (resultSet.next()) {
				for (i = locationCounter; i <= 11; i++) {
					
					// only if location exists
					if (resultSet.getString(i) != null) {
					// looks for location to be deleted
						if (resultSet.getString(i).toLowerCase().equals(locationToDelete.toLowerCase())) {
							found = true;
							locationCounter = i-1;
							break;
						}
					}
				}
			}

			if (found) {
				String sql = "UPDATE table_user_info SET Location" + locationCounter + " = NULL";
				try {
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					preparedStatement.close();
				}
			} else {
				//testing purposes
				System.out.println("Error - Location does not exist within database.");
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			preparedStatement.close();
		}
	}
	
	//logs in if the user exists
	//testing purposes
	public void login() {
		AddressObtainer aoObtainer = new AddressObtainer();
		try {
			if (isRegistered(aoObtainer.getMACAddress())) {
				System.out.println(aoObtainer.getMACAddress() + " is a registered user.");
			} else {
				System.out.println(aoObtainer.getMACAddress() + " is not a registered user.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
